package json.models

import json.models.observability.JsonElementObserver
import json.visitors.Visitor

/**
 * Representation of a Boolean in JSON.
 */
data class JsonBoolean(val value: Boolean) : JsonElement {

    override val observers: MutableList<JsonElementObserver> = mutableListOf()

    override fun accept(visitor: Visitor) {
        visitor.visit(this)
    }

    override fun toPrettyJsonString(depth: Int): String = value.toString()

}