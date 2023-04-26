package json.models

import json.visitors.interfaces.Visitor

/**
 * Representation of a Boolean in JSON.
 */
data class JsonBoolean(val value: Boolean, override val depth: Int = 0) : JsonElement {

    override fun toPrettyJsonString(): String = value.toString()
    override fun accept(visitor: Visitor) {
        visitor.visit(this)
    }

    override fun toString(): String = toPrettyJsonString()

}