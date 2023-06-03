package json.models

import json.models.observability.JsonElementObserver
import json.visitors.Visitor

/**
 * Represents a pair of values in JSON. Integrated into JsonObject and JsonList.
 */
data class JsonKeyValuePair(val name: String, val value: JsonElement) : JsonElement {

    override val observers: MutableList<JsonElementObserver> = mutableListOf()

    override fun accept(visitor: Visitor) {
        visitor.visit(this)
        value.accept(visitor)
    }

    override val depth: Int = value.depth

    override fun toPrettyJsonString(): String = "\"$name\": $value"

    override fun toString(): String = toPrettyJsonString()

}