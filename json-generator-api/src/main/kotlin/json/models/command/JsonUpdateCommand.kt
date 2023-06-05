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

    fun getNewElement():JsonElement{
        return newElement as JsonElement
    }
    override fun run() {
        newElement = generator.toJsonElement(newElement)
        jsonElement.addElement(newElement as JsonElement)
    }

    override fun undo() {
        TODO("Not yet implemented")
    }

}

class UpdateElementCommand(private val jsonElement: JsonElement, private var newValue: Any) : JsonUpdateCommand {

    fun getNewElement():JsonElement{

        return newValue as JsonElement
    }
    override fun run() {
        newValue = generator.toJsonElement(newValue)
        jsonElement.updateElement(newValue as JsonElement)
    }

    override fun undo() {
        TODO("Not yet implemented")
    }

}