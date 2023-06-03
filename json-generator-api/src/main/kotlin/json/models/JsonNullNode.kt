package json.models

import json.models.observability.JsonElementObserver
import json.visitors.Visitor

/**
 * Representation of a null node in JSON.
 */
class JsonNullNode : JsonElement {

    override val observers: MutableList<JsonElementObserver> = mutableListOf()

    override fun accept(visitor: Visitor) {
        visitor.visit(this)
    }

    override fun toPrettyJsonString(depth: Int): String = "null"

}