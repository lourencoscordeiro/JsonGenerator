package json.printer

import json.models.JsonElement

/**
 * Abstraction responsible for printing a JSON Element in any shape or form.
 */
interface JsonElementPrinter {

    /**
     * Prints the received JSON Element.
     */
    fun print(jsonElement: JsonElement)

    /**
     *  Creates implementation for [JsonElementPrinter] that prints element to console ([ToConsoleJsonElementPrinter]).
     */
    companion object Factory {
        fun toConsole(): JsonElementPrinter = ToConsoleJsonElementPrinter()
    }

}