package json.models

import json.visitors.Visitor

/**
 * Representation of a List in JSON.
 */
data class JsonArray(val elements: List<JsonElement>, override val depth: Int = 0) : JsonElement {

    override fun accept(visitor: Visitor) {
        visitor.visit(this)
        elements.forEach{ it.accept(visitor)}
    }

    override fun toPrettyJsonString(): String = elements.joinToString(
        ",\n ",
        "[\n",
        "\n${createIndentation()}]"
    ) { createIndentation(it.depth) + it.toPrettyJsonString() }

    override fun toString(): String = toPrettyJsonString()

    private fun createIndentation(indentationRatio: Int = depth): String = "\t".repeat(indentationRatio)

}