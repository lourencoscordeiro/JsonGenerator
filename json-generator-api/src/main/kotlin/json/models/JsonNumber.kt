package json.models

import json.models.observability.JsonElementObserver
import json.visitors.Visitor

/**
 * Representation of a Number in JSON.
 */
data class JsonNumber(val value: Number) : JsonElement {

    override val observers: MutableList<JsonElementObserver> = mutableListOf()

    override fun accept(visitor: Visitor) {
        visitor.visit(this)
    }

    override fun toPrettyJsonString(depth: Int): String = value.toString()

}