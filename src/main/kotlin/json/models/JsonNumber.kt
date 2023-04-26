package json.models

import json.visitors.interfaces.Visitor

/**
 * Representation of a Number in JSON.
 */
data class JsonNumber(val value: Number, override val depth: Int = 0) : JsonElement {

    override fun toPrettyJsonString(): String = value.toString()

    override fun toString(): String = toPrettyJsonString()

    override fun accept(visitor: Visitor) {
        visitor.visit(this)
    }
}