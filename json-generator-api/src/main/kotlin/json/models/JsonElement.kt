package json.models

import json.printer.JsonElementPrinter
import json.visitors.Visitor

/**
 * Abstraction that represents any type of JSON Element.
 */
interface JsonElement {

    /**
     * Entry point for a Visitor. Follows Visitor design pattern to allow
     * performing additional logic onto JSON Elements.
     */
    fun accept(visitor: Visitor)

    /**
     * Represents the depth of the element inside the JSON
     * tree. Depth values starts at 0 (root JSON node).
     */
    val depth: Int

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