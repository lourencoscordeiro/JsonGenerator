package json.models

import json.models.observability.JsonElementObserver
import json.visitors.Visitor

/**
 * Representation of a null node in JSON.
 */
class JsonNullNode(override val depth: Int = 0) : JsonElement {

    override val observers: MutableList<JsonElementObserver> = mutableListOf()

    override fun accept(visitor: Visitor) {
        visitor.visit(this)
    }

    override fun toPrettyJsonString(): String = "null"

    override fun equals(other: Any?): Boolean = if (other is JsonNullNode) depth == other.depth else false

    override fun hashCode(): Int = depth
}