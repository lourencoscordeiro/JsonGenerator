package json.models

import json.models.observability.JsonElementObserver
import json.visitors.Visitor

/**
 * Represents a pair of values in JSON. Integrated into JsonObject and JsonList.
 */
data class JsonKeyValuePair(val name: String, var value: JsonElement) : JsonElement {

    override val observers: MutableList<JsonElementObserver> = mutableListOf()

    override fun accept(visitor: Visitor) {
        visitor.visit(this)
        value.accept(visitor)
    }

    override fun updateElement(newValue: JsonElement) {
        value = newValue
        observers.forEach { it.updatedElement(newValue) }
    }
    override fun toPrettyJsonString(depth: Int): String = "${createIndentation(depth)}\"$name\": ${value.toPrettyJsonString(depth)}"

    private fun createIndentation(indentationRatio: Int): String = "\t".repeat(indentationRatio)

}