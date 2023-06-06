package json.models.command

import json.generator.JsonGenerator
import json.models.JsonElement
import json.models.JsonObject
import java.awt.Component
import javax.swing.JPanel
import javax.swing.JScrollPane
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

class EraseAllElementsCommand(private var jsonElement: JsonElement, private var panel:JPanel) : JsonUpdateCommand {

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


    private fun getViewport():JViewport?{
        val scrollPane = panel.components.find { it.name == "scrollPane"  }
        if(scrollPane!=null)
            return (scrollPane as JScrollPane).viewport
        return null
    }

    private fun getBackupComponent(): Component? {
        return getViewport()?.components?.find { it.name == "mainPanel" }
    }

    private fun addComponent(component: Component){
        getViewport()?.add(component)
    }

    private fun removeComponent(component: Component){
        getViewport()?.remove(component)
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