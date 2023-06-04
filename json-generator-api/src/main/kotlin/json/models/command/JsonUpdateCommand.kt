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

class AddElementCommand(private val jsonElement: JsonElement, private val newElement: Any?) : JsonUpdateCommand {

    fun getNewElement():Any?{
        return newElement
    }
    override fun run() {
        jsonElement.addElement(generator.toJsonElement(newElement))
    }

    override fun undo() {
        TODO("Not yet implemented")
    }

}

class UpdateElementCommand(private val jsonElement: JsonElement, private val newValue: Any) : JsonUpdateCommand {

    override fun run() {
        jsonElement.updateElement(generator.toJsonElement(newValue))
    }

    override fun undo() {
        TODO("Not yet implemented")
    }

}