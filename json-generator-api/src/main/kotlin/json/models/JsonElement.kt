package json.models

import json.models.observability.JsonElementObservable
import json.printer.JsonElementPrinter
import json.visitors.Visitor

/**
 * Abstraction that represents any type of JSON Element.
 */
interface JsonElement : JsonElementObservable {

    /**
     * Represents the depth of the element inside the JSON
     * tree. Depth values starts at 0 (root JSON node).
     */
    val depth: Int

    /**
     * Entry point for an update on a Json Element.
     */
    fun addElement(newValue: JsonElement): JsonElement {
        throw NotImplementedError("Value update is not implemented for this Json Element!")
    }

    /**
     * Entry point for a Visitor. Follows Visitor design pattern to allow
     * performing additional logic onto JSON Elements.
     */
    fun accept(visitor: Visitor)

    /**
     * Converts element to a formatted JSON string.
     */
    fun toPrettyJsonString(): String

    /**
     * Prints the element.
     */
    fun print(printer: JsonElementPrinter = JsonElementPrinter.toConsole()) {
        printer.print(this)
    }
}