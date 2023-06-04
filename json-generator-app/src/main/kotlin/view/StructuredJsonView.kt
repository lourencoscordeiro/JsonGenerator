package view

import json.models.JsonObject
import json.models.command.AddElementCommand
import json.models.command.UpdateElementCommand
import java.awt.BorderLayout
import java.awt.Component
import java.awt.Dimension
import java.awt.event.KeyEvent
import java.awt.event.KeyListener
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import javax.swing.*

class StructuredJsonView(private val rootJsonObject: JsonObject) : JLabel() {


    init {

        layout = BorderLayout()

        val scrollPane = JScrollPane(structuredJsonViewPanel()).apply {
            horizontalScrollBarPolicy = JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS
            verticalScrollBarPolicy = JScrollPane.VERTICAL_SCROLLBAR_ALWAYS
        }
        add(scrollPane, BorderLayout.CENTER)

    }

    private fun structuredJsonViewPanel(): JPanel=
        JPanel().apply {
            layout = BoxLayout(this, BoxLayout.Y_AXIS)
            alignmentX = Component.LEFT_ALIGNMENT
            alignmentY = Component.TOP_ALIGNMENT

            // Add padding to the top and left
            border = BorderFactory.createEmptyBorder(10, 20, 0, 0)

            // menu
            addMouseListener(object : MouseAdapter() {
                override fun mouseClicked(e: MouseEvent) {
                    addComponent(e,this@apply)
                }
            })
        }


    private fun addComponent(e: MouseEvent, panel:JPanel){
        if (SwingUtilities.isRightMouseButton(e)) {
            val menu = JPopupMenu("Message")
            val add = JButton("add")

            add.addActionListener {
                val text = JOptionPane.showInputDialog("text")

                // adds null element
                val command = AddElementCommand(rootJsonObject, Pair(text, "N/A"))
                command.run()
                panel.add(structureJsonDataWidget(text, "N/A"))
                panel.add(Box.createRigidArea(Dimension(0, 1)))
                menu.isVisible = false
                panel.revalidate()
                panel.repaint()
            }

            menu.add(add);
            menu.show(this, 100, 100);
        }

    }

    private fun structureJsonDataWidget(key: String, value: String): JPanel =
        JPanel().apply {
            layout = BoxLayout(this, BoxLayout.X_AXIS)
            alignmentX = Component.LEFT_ALIGNMENT
            alignmentY = Component.TOP_ALIGNMENT
            maximumSize = Dimension(Int.MAX_VALUE,20)
            add(JLabel(key))
            add(Box.createHorizontalStrut(10))
            val text = JLabel(value)
            text.addMouseListener(object : MouseAdapter() {
                override fun mouseClicked(e: MouseEvent) {
                    if (e.clickCount == 2) {
                        convertLabelToTextField(key, text)
                    }
                }
            })
            add(text)
        }

    private fun convertLabelToTextField(key: String, label: JLabel) {
        val parentNode = label.parent as JPanel
        val textField = JTextField(label.text)

        textField.maximumSize = Dimension(100 ,20)
        textField.addKeyListener(object : KeyListener {
            override fun keyTyped(e: KeyEvent?) {
                // no implementation needed
            }

            override fun keyPressed(e: KeyEvent?) {
                if (e != null && e.keyCode == KeyEvent.VK_ENTER) {

                    val newValue = textField.text
                    UpdateElementCommand(rootJsonObject, Pair(key, newValue)).run()

                    val newJLabel = JLabel(newValue)
                    parentNode.remove(textField)
                    parentNode.add(newJLabel, BorderLayout.CENTER)

                    parentNode.revalidate()
                    parentNode.repaint()
                }


            }

            override fun keyReleased(e: KeyEvent?) {
                // no implementation needed
            }

        })

        parentNode.remove(label)
        parentNode.add(textField, BorderLayout.CENTER)

        parentNode.revalidate()
        parentNode.repaint()



    }


}