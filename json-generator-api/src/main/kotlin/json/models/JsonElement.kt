package json.models

import json.models.observability.JsonElementObservable
import json.visitors.Visitor

/**
 * Abstraction that represents any type of JSON Element.
 */
interface JsonElement : JsonElementObservable {

    fun addElement(newValue: JsonElement) {
        throw NotImplementedError("Value addition is not implemented for this Json Element!")
    }

    fun updateElement(newValue: JsonElement) {
        throw NotImplementedError("Value update is not implemented for this Json Element!")
    }

    fun eraseAll() {
        throw NotImplementedError("Element erasure is not implemented for this Json Element!")
    }

    fun eraseElement(valueToErase: JsonElement) {
        throw NotImplementedError("Value erasure is not implemented for this Json Element!")
    }

    /**
     * Entry point for a Visitor. Follows Visitor design pattern to allow
     * performing additional logic onto JSON Elements.
     */
    fun accept(visitor: Visitor)

    /**
     * Converts element to a formatted JSON string.
     */
    fun toPrettyJsonString(depth: Int): String
}