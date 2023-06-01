package json.printer

import json.models.JsonElement

/**
 * Default implementation for [JsonElementPrinter]. Prints a [JsonElement] to the console.
 */
class ToConsoleJsonElementPrinter : JsonElementPrinter {

    override fun print(jsonElement: JsonElement) {
        println(jsonElement.toPrettyJsonString())
    }
}