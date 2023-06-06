package json.models.command

import json.generator.JsonGenerator
import json.models.JsonElement

/** Abstraction that represents any update action on a Json Element **/
interface JsonUpdateCommand {

    val generator: JsonGenerator
        get() = JsonGenerator()

    fun run()
    fun undo()
}

class AddElementCommand(private val jsonElement: JsonElement, private var newElement: Any?) : JsonUpdateCommand {

    var wasRan: Boolean = false

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

    var wasRan: Boolean = false

    override fun run() {
        newValue = generator.toJsonElement(newValue)
        jsonElement.updateElement(newValue as JsonElement)
        wasRan = true
    }

    override fun undo() {
        TODO("Not yet implemented")
    }

}

class EraseAllElementsCommand(private val jsonElement: JsonElement) : JsonUpdateCommand {

    override fun run() {
        TODO("Not yet implemented")
    }

    override fun undo() {
        TODO("Not yet implemented")
    }

}

// todo here, erasedValue is the json element to be deleted. For arrays, it is a JsonElement, for objects it is a Json Key Value Pair
class EraseElementCommand(private val jsonElement: JsonElement, private val erasedValue: JsonElement) : JsonUpdateCommand {

    var wasRan: Boolean = false

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