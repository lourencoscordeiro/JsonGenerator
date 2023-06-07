package json.models.command

import json.generator.JsonGenerator
import json.models.JsonElement
import json.models.JsonObject
import java.awt.Component
import javax.swing.JViewport





/** Abstraction that represents any update action on a Json Element **/
interface JsonUpdateCommand {

    val generator: JsonGenerator
        get() = JsonGenerator()

    fun run()
    fun undo()
}

class AddElementCommand(private val jsonElement: JsonElement, private var newElement: Any?) : JsonUpdateCommand {

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
        }
        wasRan = false
    }

}

class UpdateElementCommand(private val jsonElement: JsonElement, private var newValue: Any) : JsonUpdateCommand {

    private var wasRan: Boolean = false

    fun getNewElement(): JsonElement = newValue as JsonElement
    override fun run() {
        newValue = generator.toJsonElement(newValue)
        jsonElement.updateElement(newValue as JsonElement)
        wasRan = true
    }

    override fun undo() {
        TODO("Not yet implemented")
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