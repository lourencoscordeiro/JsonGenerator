package json.models.command

import json.generator.JsonGenerator
import json.models.JsonElement

/** Abstraction that represents any update action on a Json Element **/
interface JsonUpdateCommand {

    fun run()
    fun undo()
}

class AddElementCommand(private val jsonElement: JsonElement, private val newElement: Any?) : JsonUpdateCommand {

    override fun run() {
        jsonElement.addElement(JsonGenerator().toJsonElement(newElement))
        println(jsonElement)
    }

    override fun undo() {
        // todo
    }

}