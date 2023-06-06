package json.models

import json.models.observability.JsonElementObserver
import json.visitors.Visitor

/**
 * Representation of a List in JSON.
 */
data class JsonArray(var elements: MutableList<JsonElement>) : JsonElement {

    override val observers: MutableList<JsonElementObserver> = mutableListOf()


    override fun updateElement(newValue: JsonElement) {
        val element = newValue as JsonKeyValuePair
        elements[element.name.toInt()] = element.value
        observers.forEach { it.updatedElement(newValue.value) }
    }
    override fun addElement(newValue: JsonElement) {
        elements.add(newValue)
        observers.forEach{
            newValue.addObserver(it)
            if(newValue is JsonKeyValuePair)
                newValue.value.addObserver(it)

        }
        observers.forEach {
            it.addedElement(newValue) }
    }

    override fun eraseAll() {
        elements.clear()
        observers.forEach { it.erasedAll() }
    }


    override fun eraseElement(valueToErase: JsonElement) {
            elements.remove(valueToErase)
            observers.forEach { it.erasedElement(this) }
    }

    override fun accept(visitor: Visitor) {
        visitor.visit(this)
        elements.forEach{ it.accept(visitor)}
    }

    override fun toPrettyJsonString(depth: Int): String = elements.joinToString(
        ",\n ",
        "[\n",
        "\n${createIndentation(depth)}]"
    ) { "${createIndentation(depth + 1)}${it.toPrettyJsonString(depth)}" }

    private fun createIndentation(indentationRatio: Int): String = "\t".repeat(indentationRatio)

}