package view

import json.models.JsonElement
import json.models.command.AddElementCommand
import json.models.observability.JsonElementObserver
import java.awt.BorderLayout
import java.awt.Component
import java.awt.Dimension
import java.awt.GridLayout
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import javax.swing.*

class StructuredJsonView(private val jsonElement: JsonElement) : JLabel() {


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
                val command = AddElementCommand(jsonElement, Pair(text, "N/A"))
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
                        convertLabelToTextField(text)
                    }
                }
            })
            add(text)
        }

    private fun convertLabelToTextField(label:JLabel) {
        val parentNode = label.parent as JPanel
        val textField = JTextField(label.text)
        textField.maximumSize = Dimension(100 ,20)
        parentNode.remove(label)
        parentNode.add(textField, BorderLayout.CENTER)
        parentNode.revalidate()
        parentNode.repaint()

    }


}