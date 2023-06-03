package json.models

import json.models.observability.JsonElementObserver
import json.visitors.Visitor

/**
 * Representation of a List in JSON.
 */
data class JsonArray(val elements: List<JsonElement>) : JsonElement {

    init {
        if (elements.any { it is JsonObject || it is JsonArray }) {
            throw IllegalArgumentException("Array cannot contain an object or an array.")
        }
    }

    override val observers: MutableList<JsonElementObserver> = mutableListOf()

    override fun accept(visitor: Visitor) {
        visitor.visit(this)
        elements.forEach{ it.accept(visitor)}
    }

    override fun toPrettyJsonString(depth: Int): String = elements.joinToString(
        ",\n ",
        "[\n",
        "\n${createIndentation(depth)}]"
    ) { "${createIndentation(depth)}${it.toPrettyJsonString(depth)}" }

    private fun createIndentation(indentationRatio: Int): String = "\t".repeat(indentationRatio)

}