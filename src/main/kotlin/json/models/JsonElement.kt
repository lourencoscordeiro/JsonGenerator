package json.models

import json.visitors.interfaces.Visitor

/**
 * Abstraction that represents any type of JSON Element.
 */
interface JsonElement {

    /**
     * Represents the depth of the element inside the JSON
     * tree. Depth values starts at 0 (root JSON node).
     */
    val depth: Int

    /**
     * Converts element to a formatted JSON string.
     */
    fun toPrettyJsonString(): String

    fun accept(visitor: Visitor) {}
}