package view

import json.models.JsonArray
import json.models.JsonElement
import json.models.JsonKeyValuePair
import json.models.JsonObject
import json.models.command.AddElementCommand
import json.models.command.UpdateElementCommand
import java.awt.BorderLayout
import java.awt.Color
import java.awt.Component
import java.awt.Dimension
import java.awt.event.*
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

    private fun addComponent(e: MouseEvent, panel:JPanel,jsonElement: JsonElement=rootJsonObject){
        if (SwingUtilities.isRightMouseButton(e)) {
            val menu = JPopupMenu("Message")
            val addSimpleAttribute = JButton("Add simple JSON attribute")
            val addListAttribute = JButton("Add list JSON attribute")

            addSimpleAttribute.addActionListener {
                val text = JOptionPane.showInputDialog("Property Name")

                // adds null element
                val command = AddElementCommand(jsonElement, Pair(text, "N/A"))
                command.run()
                panel.add(structureJsonDataWidget(jsonElement,text, "N/A"))
                panel.add(Box.createRigidArea(Dimension(0, 1)))
                menu.isVisible = false
                panel.revalidate()
                panel.repaint()
            }

            addListAttribute.addActionListener {
                val text = JOptionPane.showInputDialog("List Attribute Name")

                // adds null element
                AddElementCommand(jsonElement, Pair(text, listOf<JsonElement>())).run()
                panel.add(structureJsonListDataWidget(text,jsonElement))
                panel.add(Box.createRigidArea(Dimension(0, 1)))

                menu.isVisible = false
                panel.revalidate()
                panel.repaint()
            }

            menu.add(addSimpleAttribute);
            menu.add(addListAttribute);
            menu.show(this, 100, 100);
        }

    }

    private fun structureJsonDataWidget(jsonElement: JsonElement, key:String,value: String): JPanel =
        JPanel().apply {

            layout = BoxLayout(this, BoxLayout.X_AXIS)
            border = BorderFactory.createLineBorder(Color.BLACK, 2)

            alignmentX = Component.LEFT_ALIGNMENT
            alignmentY = Component.TOP_ALIGNMENT
            maximumSize = Dimension(Int.MAX_VALUE,20)

            add(JLabel(key))
            add(Box.createHorizontalStrut(10))

            val text = JLabel(value)
            text.addMouseListener(object : MouseAdapter() {
                override fun mouseClicked(e: MouseEvent) {
                    if (e.clickCount == 2) {
                        convertLabelToTextField(jsonElement,key, text)
                    }
                }
            })
            add(text)

            /*
if (isList) {
addMouseListener(object : MouseAdapter() {
    override fun mouseClicked(e: MouseEvent) {
            addComponent(e,this@apply,rootJsonObject.attributes.find { it.name == key }!!.value)
            val text = JOptionPane.showInputDialog("Property Name")
            AddElementCommand(rootJsonObject.attributes.find { it.name == key }!!.value, text).run()
    }
})
        */
        }

    private fun structureJsonListDataWidget(key: String, jsonParent:JsonElement): JPanel =
        JPanel().apply {

            layout = BoxLayout(this, BoxLayout.Y_AXIS)
            border = BorderFactory.createLineBorder(Color.BLACK, 2)

            alignmentX = Component.LEFT_ALIGNMENT
            alignmentY = Component.TOP_ALIGNMENT


            //maximumSize = Dimension(Int.MAX_VALUE,20)

            add(JLabel(key))
            add(Box.createHorizontalStrut(10))

            val list = JPanel()
            addMouseListener(object : MouseAdapter() {
                override fun mouseClicked(e: MouseEvent) {
                    addComponent(e, this@apply, rootJsonObject.attributes.find { it.name == key }!!.value)

                }
            })

            add(list)
        }







    private fun convertLabelToTextField(jsonElement: JsonElement, key: String, label: JLabel) {
        val parentNode = label.parent as JPanel
        val textField = JTextField(label.text)

        textField.maximumSize = Dimension(100 ,20)
        textField.addKeyListener(object : KeyAdapter() {

            override fun keyPressed(e: KeyEvent?) {
                if (e != null && e.keyCode == KeyEvent.VK_ENTER) {

                    val newValue = textField.text
                    val element = if (jsonElement is JsonArray) {
                        jsonElement.elements.find { it is JsonKeyValuePair && it.name == key }!!
                    } else {
                        jsonElement
                    }
                    UpdateElementCommand(element, Pair(key, newValue)).run()

                    val newJLabel = JLabel(newValue)
                    newJLabel.addMouseListener(object : MouseAdapter() {
                        override fun mouseClicked(e: MouseEvent) {
                            if (e.clickCount == 2) {
                                convertLabelToTextField(jsonElement,key, newJLabel)
                            }
                        }
                    })
                    parentNode.remove(textField)
                    parentNode.add(newJLabel, BorderLayout.CENTER)

                    parentNode.revalidate()
                    parentNode.repaint()
                    //println(rootJsonObject.toPrettyJsonString(0))
                }
            }
        })

        parentNode.remove(label)
        parentNode.add(textField, BorderLayout.CENTER)

        parentNode.revalidate()
        parentNode.repaint()
    }
}