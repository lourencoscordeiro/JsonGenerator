package json.models

import json.models.observability.JsonElementObserver
import json.visitors.Visitor

/**
 * Representation of a List in JSON.
 */
data class JsonArray(var elements: List<JsonElement>) : JsonElement {

    override val observers: MutableList<JsonElementObserver> = mutableListOf()

    override fun addElement(newValue: JsonElement) {
        elements = elements.plus(newValue)
        observers.forEach { it.addedElement(newValue) }
    }

    override fun eraseAll() {
        elements = listOf()
        observers.forEach { it.erasedAll() }
    }

    override fun eraseElement(valueToErase: JsonElement) {
        val index = elements.indexOf(valueToErase)
        if (-1 != index) {
            elements = elements.drop(index)
            observers.forEach { it.erasedElement(this) }
        }
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