package view

import json.models.*
import json.models.command.AddElementCommand
import json.models.command.UpdateElementCommand
import java.awt.*
import java.awt.event.*
import javax.swing.*

class StructuredJsonView(private var rootJsonObject: JsonObject, private val gbc:GridBagConstraints = GridBagConstraints()) : JPanel() {


    init {

        layout = BorderLayout()

        val scrollPane = JScrollPane(structuredJsonViewPanel()).apply {
            horizontalScrollBarPolicy = JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS
            verticalScrollBarPolicy = JScrollPane.VERTICAL_SCROLLBAR_ALWAYS
        }
        add(scrollPane, BorderLayout.CENTER)
        maximumSize = Dimension(300,Int.MAX_VALUE)
    }

    private fun structuredJsonViewPanel(): JPanel=
        JPanel().apply {
            border = BorderFactory.createLineBorder(Color.GRAY, 1)

            layout = GridBagLayout()
            name = "mainPanel"
            // Add padding to the top and left
            border = BorderFactory.createEmptyBorder(10, 20, 0, 0)
            // menu
            addMouseListener(object : MouseAdapter() {
                override fun mouseClicked(e: MouseEvent) {
                    if (SwingUtilities.isRightMouseButton(e)) {
                        addComponent(this@apply)
                    }
                }
            })
        }



    private fun addComponent(panel:JPanel,jsonElement: JsonElement=rootJsonObject){
        val menu = JPopupMenu("Message")
        val addSimpleAttribute = JButton("Add simple JSON attribute")
        val addListAttribute = JButton("Add array JSON attribute")
        val addObjectAttribute = JButton("Add object JSON attribute")
        val eraseAll = JButton("Erase entire JSON object")

        addSimpleAttribute.addActionListener {
            val text = JOptionPane.showInputDialog("Property Name")
            // adds null element
            val command = AddElementCommand(jsonElement, Pair(text, "N/A"))
            helper(command,panel,menu)
        }

        addListAttribute.addActionListener {
            val text = JOptionPane.showInputDialog("Array Attribute Name")
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

    private fun helper(command:AddElementCommand,panel:JPanel,menu:JPopupMenu){
        command.run()

        val newKeyValuePairPanel = createKeyValuePairJPanel(command.getNewElement() as JsonKeyValuePair)
        if(panel.name == "mainPanel"){
            gbc.gridx=0
            gbc.fill = GridBagConstraints.HORIZONTAL
            gbc.weightx = 1.0
        }
        panel.add(newKeyValuePairPanel,gbc)

        menu.isVisible = false
        repaintWindow(panel)
    }




    private fun repaintWindow(panel: JPanel) {
        panel.revalidate()
        panel.repaint()
    }



    private fun createKeyValuePairJPanel(keyValuePair: JsonKeyValuePair):JPanel =
        JPanel().apply {
            name = keyValuePair.name
            layout = BoxLayout(this, BoxLayout.X_AXIS)
            border = BorderFactory.createLineBorder(Color.GRAY, 1)
            alignmentX = Component.LEFT_ALIGNMENT
            alignmentY = Component.TOP_ALIGNMENT

            add(JLabel(keyValuePair.name))
            add(Box.createHorizontalStrut(10))
            val value = keyValuePair.value
            if (value is JsonArray) {
                val panel = createPanel()
                panel.name = keyValuePair.name
                this@apply.addMouseListener(object : MouseAdapter() {
                    override fun mouseClicked(e: MouseEvent) {
                        if (SwingUtilities.isRightMouseButton(e)) {
                            addValuesToList(value, panel)
                        }
                    }
                })
                add(panel)
            } else if (value is JsonObject) {
                val panel =createPanel()
                panel.name = keyValuePair.name
                this@apply.addMouseListener(object : MouseAdapter() {
                    override fun mouseClicked(e: MouseEvent) {
                        if (SwingUtilities.isRightMouseButton(e)) {
                            addComponent(panel, value)
                        }
                    }
                })
                add(panel)
            } else {
                add(
                    createInteractiveLabel(
                        keyValuePair.name,
                        keyValuePair.value.toPrettyJsonString(0).replace("\"", ""),
                        this@apply,
                        keyValuePair
                    )
                )
            }
        }

    private fun createPanel():JPanel =
        JPanel().apply {
            layout = GridLayout(0,1)
        }
    private fun addValuesToList(jsonArray:JsonArray,parent:JPanel) {
        val menu = JPopupMenu("Message")
        val addSimpleAttribute = JButton("Add")

        val addListAttribute = JButton("Add list JSON attribute")
        val addObjectAttribute = JButton("Add object JSON attribute")

        addListAttribute.addActionListener {
            val text = JOptionPane.showInputDialog("List Attribute Name")
            // adds null element
            val command =AddElementCommand(jsonArray, Pair(text, (mutableListOf<JsonElement>())))
            helper(command,parent,menu)
        }

        addObjectAttribute.addActionListener {
            val text = JOptionPane.showInputDialog("Object Attribute Name")
            // adds null element
            val command =  AddElementCommand(jsonArray, Pair(text,emptyMap<String,JsonElement>()))
            helper(command,parent,menu)
        }


        addSimpleAttribute.addActionListener {
            val text = JOptionPane.showInputDialog("Property Name")

            // adds null element
            val command = AddElementCommand(jsonArray, text)
            command.run()
            val element = command.getNewElement()
            if (element is JsonBoolean){
                val checkbox = JCheckBox()
                checkbox.isSelected = element.value
                checkbox.addActionListener {
                    val newValue = checkbox.isSelected
                    val updatedElement = UpdateElementCommand(jsonArray,newValue).run()

                }
                parent.add(checkbox)
                repaintWindow(parent)
            }else {
                val label = createInteractiveLabel((parent.components.size).toString(), text, parent, jsonArray)
                label.maximumSize = Dimension(Int.MAX_VALUE, 30)
            }
            menu.isVisible = false
        }

        menu.add(addSimpleAttribute)
        menu.add(addListAttribute)
        menu.add(addObjectAttribute)
        menu.show(this, 100, 100)
    }

    private fun createInteractiveLabel(key:String,text:String, parent:JPanel,jsonElement: JsonElement):JLabel {
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
        repaintWindow(parent)
        return label
    }


    private fun convertLabelToTextField(jsonElement: JsonElement,  label: JLabel) {
        val parentNode = label.parent as JPanel
        val textField = JTextField(label.text)
        textField.name = label.name
        textField.minimumSize = Dimension(100 ,20)
        textField.maximumSize = Dimension(100 ,20)

        textField.addKeyListener(object : KeyAdapter() {
            override fun keyPressed(e: KeyEvent?) {
                if (e != null && e.keyCode == KeyEvent.VK_ENTER) {
                    submitChanges(textField,jsonElement)
                }
            }
        })
        val index = parentNode.components.indexOf(label)
        if(index != -1){
            parentNode.remove(label)
            parentNode.add(textField,BorderLayout.CENTER, index )

            repaintWindow(parentNode)
        }
    }

    private fun submitChanges(textField:JTextField, jsonElement: JsonElement){
        val newValue = textField.text
        val parentNode = textField.parent as JPanel;
        val elementModifier = UpdateElementCommand(jsonElement, Pair(textField.name, newValue))
        elementModifier.run()
        val value = elementModifier.getNewElement() as JsonKeyValuePair
        var element:Component
        if (value.value is JsonBoolean){
            element = JCheckBox()
            element.isSelected = (value.value as JsonBoolean).value
            element.addActionListener {
                val newValue = (element as JCheckBox).isSelected
                val updatedElement = UpdateElementCommand(jsonElement,newValue).run()
            }

        }else{
            element = createInteractiveLabel(textField.name,newValue,parentNode,jsonElement)
        }
        val index = parentNode.components.indexOf(textField)
        if(index != -1){
            parentNode.remove(textField)
            parentNode.add(element,BorderLayout.CENTER, index )

            repaintWindow(parentNode)
        }
        println(rootJsonObject.toPrettyJsonString(0))
    }


}