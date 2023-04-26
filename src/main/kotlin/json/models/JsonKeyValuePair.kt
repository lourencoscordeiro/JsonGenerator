package json.models

import json.visitors.interfaces.Visitor

/**
 * Represents a pair of values in JSON. Integrated into JsonObject and JsonList.
 */
data class JsonKeyValuePair(val name: String, val value: JsonElement) : JsonElement {

    override val depth: Int = value.depth
    fun getName() = name
    fun getValue() = value
    override fun toPrettyJsonString(): String = "\"$name\": $value"
    override fun accept(visitor: Visitor) {
            visitor.visit(this)
            value.accept(visitor)
    }

    override fun toString(): String = toPrettyJsonString()

}