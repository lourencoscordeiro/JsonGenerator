package view

import json.models.*
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
            val addObjectAttribute = JButton("Add object JSON attribute")

            addSimpleAttribute.addActionListener {
                val text = JOptionPane.showInputDialog("Property Name")
                // adds null element
                val command = AddElementCommand(jsonElement, Pair(text, "N/A"))
                helper(command,panel,menu)
            }

            addListAttribute.addActionListener {
                val text = JOptionPane.showInputDialog("List Attribute Name")
                // adds null element
                val command =AddElementCommand(jsonElement, Pair(text, listOf<JsonElement>()))
                helper(command,panel,menu)
            }

            addObjectAttribute.addActionListener {
                val text = JOptionPane.showInputDialog("Object Attribute Name")
                // adds null element
                val command =  AddElementCommand(jsonElement, Pair(text,emptyMap<String,JsonElement>()))
                helper(command,panel,menu)
            }

            menu.add(addSimpleAttribute)
            menu.add(addListAttribute)
            menu.add(addObjectAttribute)
            menu.show(this, 100, 100)
        }

    }

private fun helper(command:AddElementCommand,panel:JPanel,menu:JPopupMenu){
    command.run()

    val newKeyValuePairPanel = createKeyValuePairJPanel(command.getNewElement() as JsonKeyValuePair)
    panel.add(newKeyValuePairPanel)
    //panel.add(Box.createRigidArea(Dimension(0, 1)))

    menu.isVisible = false
    panel.revalidate()
    panel.repaint()
}

    private fun createKeyValuePairJPanel(keyValuePair: JsonKeyValuePair):JPanel =
        JPanel().apply {
            layout = BoxLayout(this, BoxLayout.X_AXIS)
            border = BorderFactory.createLineBorder(Color.BLACK, 2)
            alignmentX = Component.LEFT_ALIGNMENT
            alignmentY = Component.TOP_ALIGNMENT

            add(JLabel(keyValuePair.name))
            add(Box.createHorizontalStrut(10))
            val value = keyValuePair.value
            if(value is JsonArray)
                add(createListPanel(value))
            else if(value is JsonObject)
                add(createObjectPanel(value))
            else
                add(createInteractiveLabel(keyValuePair.value.toPrettyJsonString(0).replace("\"", "")   ,this@apply,keyValuePair.name,rootJsonObject))
        }

    private fun createListPanel(jsonList: JsonArray):JPanel =
        JPanel().apply {
            layout = BoxLayout(this,BoxLayout.Y_AXIS)
            alignmentX = Component.LEFT_ALIGNMENT
            alignmentY = Component.TOP_ALIGNMENT
            border = BorderFactory.createLineBorder(Color.BLACK, 2)
            addMouseListener(object : MouseAdapter() {
                override fun mouseClicked(e: MouseEvent) {
                    if (e.clickCount == 2) {
                        addValuesToList(jsonList, this@apply)
                    }
                }
            })
        }
    private fun addValuesToList(jsonArray:JsonArray,parent:JPanel) {
        val menu = JPopupMenu("Message")
        val addSimpleAttribute = JButton("Add")

        addSimpleAttribute.addActionListener {
            val text = JOptionPane.showInputDialog("Property Name")

            // adds null element
            val command = AddElementCommand(jsonArray, text)
            command.run()
            parent.add(JLabel(text))
            menu.isVisible = false
            parent.revalidate()
            parent.repaint()
        }
        menu.add(addSimpleAttribute)
        menu.show(this, 100, 100)
    }

    private fun createObjectPanel(jsonObject: JsonObject):JPanel=
        JPanel().apply {
            layout = BoxLayout(this,BoxLayout.Y_AXIS)
            alignmentX = Component.LEFT_ALIGNMENT
            alignmentY = Component.TOP_ALIGNMENT
            border = BorderFactory.createLineBorder(Color.BLACK, 2)

            addMouseListener(object : MouseAdapter() {
                override fun mouseClicked(e: MouseEvent) {
                    if (e.clickCount == 2) {
                        addComponent(e, this@apply, jsonObject)
                    }
                }
            })

        }


    private fun convertLabelToTextField(jsonElement: JsonElement,  label: JLabel) {
        val parentNode = label.parent as JPanel
        val textField = JTextField(label.text)
        textField.name = label.name
        textField.maximumSize = Dimension(100 ,20)

        textField.addKeyListener(object : KeyAdapter() {
            override fun keyPressed(e: KeyEvent?) {
                if (e != null && e.keyCode == KeyEvent.VK_ENTER) {
                    submitChanges(textField,jsonElement)
                }
            }
        })

        parentNode.remove(label)
        parentNode.add(textField, BorderLayout.CENTER)

        parentNode.revalidate()
        parentNode.repaint()
    }


    private fun createInteractiveLabel(text:String, parent:JPanel,key:String,jsonElement: JsonElement):JLabel {
        val label = JLabel(text)
        label.name = key
        label.addMouseListener(object : MouseAdapter() {
            override fun mouseClicked(e: MouseEvent) {
                if (e.clickCount == 2) {
                    convertLabelToTextField(jsonElement,label)
                }
            }
        })
        parent.add(label)
        parent.revalidate()
        parent.repaint()
        return label
    }

    private fun submitChanges(textField:JTextField, jsonElement: JsonElement){
        val newValue = textField.text
        val parent = textField.parent as JPanel
        val elementModifier = UpdateElementCommand(jsonElement, Pair(textField.name, newValue))
        elementModifier.run()

        val label = createInteractiveLabel(newValue,parent,textField.name,jsonElement)
        parent.remove(textField)
        parent.add(label)
        parent.revalidate()
        parent.repaint()
        println(rootJsonObject.toPrettyJsonString(0))
    }


}