package view

import json.models.*
import json.models.command.AddElementCommand
import json.models.command.EraseAllElementsCommand
import json.models.command.EraseElementCommand
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
            name = "scrollPane"
        }
        add(scrollPane, BorderLayout.CENTER)

        val command = EraseAllElementsCommand(rootJsonObject,scrollPane.viewport)

        val eraseButton = JButton("Erase entire JSON Object")
        val undoButton = JButton("Undo Erase")
        val buttonPanel = JPanel(FlowLayout())
        eraseButton.addActionListener{
            command.run()
            val panel = structuredJsonViewPanel()
            panel.addContainerListener(object: ContainerListener{
                override fun componentAdded(e: ContainerEvent) {
                    buttonPanel.remove(undoButton)
                    repaintWindow(buttonPanel)
                }

                override fun componentRemoved(e: ContainerEvent) {
                    buttonPanel.remove(undoButton)
                    repaintWindow(buttonPanel)
                }
            })

            scrollPane.viewport.add(panel)
            buttonPanel.add(undoButton,BorderLayout.SOUTH)
            repaintWindow(this)
        }
        undoButton.addActionListener{
            command.undo()
            rootJsonObject.notifyObservers()
            buttonPanel.remove(undoButton)
            repaintWindow(this)
        }
        buttonPanel.add(eraseButton)
        add(buttonPanel, BorderLayout.SOUTH)

        repaintWindow(this)


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
                        addComponent(this@apply).show(this@apply, 100, 100)
                    }
                }
            })
        }



    private fun addComponent(panel:JPanel,jsonElement: JsonElement=rootJsonObject):JPopupMenu{
        val menu = JPopupMenu("Message")
        val addSimpleAttribute = JButton("Add simple JSON attribute")
        val addListAttribute = JButton("Add array JSON attribute")
        val addObjectAttribute = JButton("Add object JSON attribute")

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
        return menu
    //menu.show(this, 100, 100)

    }

    private fun helper(command:AddElementCommand,panel:JPanel,menu:JPopupMenu){

        val newKeyValuePairPanel = createKeyValuePairJPanel(command,panel)
        if(panel.name == "mainPanel"){
            gbc.gridx=0
            gbc.fill = GridBagConstraints.HORIZONTAL
            gbc.weightx = 1.0
        }
        panel.add(newKeyValuePairPanel,gbc)

        menu.isVisible = false
        repaintWindow(panel)
    }

    private fun createKeyValuePairJPanel(command:AddElementCommand,parent:JPanel):JPanel =

        JPanel().apply {
            command.run()
            val element = command.getNewElement() as JsonKeyValuePair
            name = element.name
            layout = BoxLayout(this, BoxLayout.X_AXIS)
            border = BorderFactory.createLineBorder(Color.GRAY, 1)
            alignmentX = Component.LEFT_ALIGNMENT
            alignmentY = Component.TOP_ALIGNMENT

            add(JLabel(element.name))
            add(Box.createHorizontalStrut(10))
            val value = element.value
            when (value) {
                is JsonArray -> {
                    val panel = createPanel()
                    panel.name = element.name
                    this@apply.addMouseListener(object : MouseAdapter() {
                        override fun mouseClicked(e: MouseEvent) {
                            if (SwingUtilities.isRightMouseButton(e)) {
                                val menu = addValuesToList(value, panel)
                                if(menu.components.find { it.name == "removeButton" } == null)menu.add(remove(command,this@apply,parent))
                                menu.show(this@StructuredJsonView, 100, 100)
                            }
                        }
                    })
                    add(panel)
                }

                is JsonObject -> {
                    val panel =createPanel()
                    panel.name = element.name
                    this@apply.addMouseListener(object : MouseAdapter() {
                        override fun mouseClicked(e: MouseEvent) {
                            if (SwingUtilities.isRightMouseButton(e)) {
                                val menu =addComponent(panel, value)
                                if(menu.components.find { it.name == "removeButton" } == null)menu.add(remove(command,this@apply,parent))
                                menu.show(this@StructuredJsonView, 100, 100)
                            }
                        }
                    })
                    add(panel)
                }
                else -> {
                    val menu = JPopupMenu("Message")
                    val label =createInteractiveLabel(element.name, element.value.toPrettyJsonString(0).replace("\"", ""),element)
                    label.addMouseListener(object : MouseAdapter() {
                        override fun mouseClicked(e: MouseEvent) {
                            if (SwingUtilities.isRightMouseButton(e)) {
                                val removeButton = remove(command,this@apply,parent)
                                if(menu.components.find { it.name == "removeButton" } == null)menu.add(removeButton)
                                menu.show(this@StructuredJsonView, 100, 100)
                            }
                        }
                    })
                    add(label)
                }
            }
        }
    private fun remove(command:AddElementCommand,toRemove:Component,panel:JPanel):JButton{
        val removeButton = JButton("Remove")
        removeButton.name = "removeButton"
        removeButton.addActionListener{
            EraseElementCommand(command.getJsonElement(),command.getNewElement()).run()
            panel.remove(toRemove)
            val menu = removeButton.parent as JPopupMenu
            menu.isVisible = false
            repaintWindow(panel)
        }
        return removeButton
    }
    private fun repaintWindow(panel: JPanel) {
        panel.revalidate()
        panel.repaint()
    }
    private fun createPanel():JPanel =
        JPanel().apply {
            layout = GridLayout(0,1)
        }
    private fun addValuesToList(jsonArray:JsonArray,parent:JPanel):JPopupMenu {
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
                    UpdateElementCommand(jsonArray,newValue).run()
                }
                checkbox.addMouseListener(object : MouseAdapter() {
                    override fun mouseClicked(e: MouseEvent) {
                        if (SwingUtilities.isRightMouseButton(e)) {
                            val removeMenu = JPopupMenu("Message")
                            val removeButton = remove(command,checkbox,parent)
                            if(removeMenu.components.find { it.name == "removeButton" } == null)
                                removeMenu.add(removeButton)
                            removeMenu.show(parent, 100, 100)
                        }
                    }
                })
                parent.add(checkbox)
                repaintWindow(parent)
            }else {
                val label = createInteractiveLabel((parent.components.size).toString(), text, jsonArray)
                label.maximumSize = Dimension(Int.MAX_VALUE, 30)
                label.addMouseListener(object : MouseAdapter() {
                    override fun mouseClicked(e: MouseEvent) {
                        if (SwingUtilities.isRightMouseButton(e)) {
                            val removeMenu = JPopupMenu("Message")
                            val removeButton = remove(command,label,parent)
                            if(removeMenu.components.find { it.name == "removeButton" } == null)removeMenu.add(removeButton)
                            removeMenu.show(parent, 100, 100)
                        }
                    }
                })
                parent.add(label)
            }
            menu.isVisible = false
        }
        repaintWindow(parent)
        menu.add(addSimpleAttribute)
        menu.add(addListAttribute)
        menu.add(addObjectAttribute)
       return menu
    }

    private fun createInteractiveLabel(key:String,text:String,jsonElement: JsonElement):JLabel {
        val label = JLabel(text)
        label.name = key
        label.addMouseListener(object : MouseAdapter() {
            override fun mouseClicked(e: MouseEvent) {
                if (e.clickCount == 2) {
                    convertLabelToTextField(jsonElement,label)
                }
            }
        })
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
        val parentNode = textField.parent as JPanel
        val elementModifier = UpdateElementCommand(jsonElement, Pair(textField.name, newValue))
        elementModifier.run()
        val value = elementModifier.getNewElement() as JsonKeyValuePair
        val element:Component
        if (value.value is JsonBoolean){
            element = JCheckBox()
            element.isSelected = (value.value as JsonBoolean).value
            element.addActionListener {
                val checkboxValue = element.isSelected
                UpdateElementCommand(jsonElement,checkboxValue).run()
            }

        }else{
            element = createInteractiveLabel(textField.name,newValue,jsonElement)
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