package json.models

import json.models.observability.JsonElementObserver
import json.visitors.Visitor

/**
 * Representation of a String in JSON.
 */
data class JsonString(val value: String) : JsonElement {

    override val observers: MutableList<JsonElementObserver> = mutableListOf()

    override fun accept(visitor: Visitor) {
        visitor.visit(this)
    }

    override fun toPrettyJsonString(depth: Int): String = "\"$value\""

}