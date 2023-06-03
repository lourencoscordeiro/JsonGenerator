package json.models

import json.models.observability.JsonElementObserver
import json.visitors.Visitor

/**
 * Representation of an Object in JSON. Consists on a map of its attributes (name and JSON Element).
 */
data class JsonObject(var attributes: List<JsonKeyValuePair> = listOf()) :
    JsonElement {

    override val observers: MutableList<JsonElementObserver> = mutableListOf()

    override fun addElement(newValue: JsonElement): JsonElement {
        attributes = attributes.plus(newValue as JsonKeyValuePair)
        observers.forEach { it.addedElement(newValue) }
        return this
    }

    override fun accept(visitor: Visitor) {
        visitor.visit(this)
        attributes.forEach{
            it.accept(visitor)
        }
    }

    override fun toPrettyJsonString(depth: Int): String =
        attributes.joinToString(", ", "{", "\n${createIndentation(depth)}}") { "\n${it.toPrettyJsonString(depth + 1)}" }

    private fun createIndentation(indentationRatio: Int): String = "\t".repeat(indentationRatio)
}