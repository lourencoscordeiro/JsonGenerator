package json.models

import json.models.observability.JsonElementObserver
import json.visitors.Visitor

/**
 * Representation of a String in JSON.
 */
data class JsonString(val value: String, override val depth: Int = 0) : JsonElement {

    override val observers: MutableList<JsonElementObserver> = mutableListOf()

    override fun accept(visitor: Visitor) {
        visitor.visit(this)
    }

    override fun toPrettyJsonString(): String = "\"$value\""

    override fun toString(): String = toPrettyJsonString()
}