package json.models.command

import json.generator.JsonGenerator
import json.models.*
import java.awt.Component
import java.awt.Panel
import javax.swing.JCheckBox
import javax.swing.JPanel
import javax.swing.JViewport





/** Abstraction that represents any update action on a Json Element **/
interface JsonUpdateCommand {

    val generator: JsonGenerator
        get() = JsonGenerator()

    fun run()
    fun undo()
}

class AddElementCommand(private val jsonElement: JsonElement, private var newElement: Any?, private val parent:JPanel) : JsonUpdateCommand {

    private var wasRan: Boolean = false

    fun getNewElement(): JsonElement = newElement as JsonElement
    fun getJsonElement():JsonElement = jsonElement

    override fun run() {
        newElement = generator.toJsonElement(newElement)
        jsonElement.addElement(newElement as JsonElement)
        wasRan = true
    }

    override fun undo() {
        if (wasRan) {
            jsonElement.eraseElement(newElement as JsonElement)
            val toRemove = parent.components.find { it.name == (newElement as JsonKeyValuePair).name }
            parent.remove(toRemove)
            parent.revalidate()
            parent.repaint()
        }
        wasRan = false
    }

}

class UpdateElementCommand(private var jsonElement: JsonElement, private var newValue: Any,private var parent: JPanel,private var component:Component) : JsonUpdateCommand {

    private var wasRan: Boolean = false
    private lateinit var backupElement:JsonElement
    private lateinit var backupComponent:Component

    fun getNewElement(): JsonElement = newValue as JsonElement
    override fun run() {
        newValue = generator.toJsonElement(newValue)
        createBackup()
        jsonElement.updateElement(newValue as JsonElement)
        wasRan = true
    }

    override fun undo() {
        if(wasRan){
            jsonElement.observers.forEach{backupElement.addObserver(it)}
            jsonElement.updateElement(backupElement as JsonElement)
            jsonElement.observers.forEach{it.updatedElement(backupElement as JsonElement)}
            if(component is JCheckBox){
                (component as JCheckBox).isSelected = !(component as JCheckBox).isSelected
            }else{
                val element = parent.components.find{it.name == backupComponent.name}
                parent.remove(element)
                parent.add(backupComponent)
            }
        }
        wasRan = false
    }
    private fun createBackup(){
        createElementBackup()
        createComponentBackup()
    }
    private fun createElementBackup() {
        backupElement = when (jsonElement) {
            is JsonObject -> (jsonElement as JsonObject).attributes.find { it.name == (jsonElement as JsonKeyValuePair).name } as JsonElement
            is JsonArray -> JsonKeyValuePair((newValue as JsonKeyValuePair).name,(jsonElement as JsonArray).elements[(newValue as JsonKeyValuePair).name.toInt()] )
            is JsonKeyValuePair -> (jsonElement as JsonKeyValuePair).copy()
            else -> throw IllegalArgumentException("Unsupported JsonElement type: ${jsonElement.javaClass.simpleName}")
        }
    }
    private fun createComponentBackup(){
        backupComponent = component
    }
}


class EraseAllElementsCommand(private var jsonElement: JsonElement, private var viewport:JViewport) : JsonUpdateCommand {

    private var wasRan: Boolean = false
    private lateinit var backupElement:JsonObject
    private var backupComponent: Component? = null
    override fun run() {
        if(jsonElement is JsonObject) {
            val element = jsonElement as JsonObject
            backupElement = element.copy()
            backupComponent = getBackupComponent()
            backupComponent?.let { removeComponent(it) }
            jsonElement.eraseAll()
            wasRan = true
        }else{
            throw IllegalArgumentException("Can't erase")
        }
    }

    override fun undo() {
        if(wasRan) {
            if(jsonElement is JsonObject) {
                backupElement.attributes.forEach{jsonElement.addElement(it)}
                val componentRemoved = getBackupComponent()
                if (componentRemoved != null) {
                    removeComponent(componentRemoved)
                }
                backupComponent?.let { addComponent(it) }
            }
        }
        wasRan = false
    }

    private fun getBackupComponent(): Component? {
        return viewport.components.find { it.name == "mainPanel" }
    }
    private fun addComponent(component: Component){
        viewport.add(component)
    }
    private fun removeComponent(component: Component){
        viewport.remove(component)
    }
}

// todo here, erasedValue is the json element to be deleted. For arrays, it is a JsonElement, for objects it is a Json Key Value Pair
class EraseElementCommand(private val jsonElement: JsonElement, private val erasedValue: JsonElement) : JsonUpdateCommand {

    private var wasRan: Boolean = false

    override fun run() {
        jsonElement.eraseElement(erasedValue)
        wasRan = true
    }

    override fun undo() {
        if (wasRan) {
            jsonElement.addElement(erasedValue)
        }
        wasRan = false
    }

}