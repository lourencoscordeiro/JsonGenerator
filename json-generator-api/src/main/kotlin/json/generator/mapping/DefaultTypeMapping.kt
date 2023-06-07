package json.generator.mapping

import json.models.*

/**
 * Default implementation for [TypeMapping].
 */
class DefaultTypeMapping : TypeMapping {

    override fun convertNumber(number: Number): JsonNumber = JsonNumber(number)

    override fun convertString(string: String): JsonString = JsonString(string)

    override fun convertBoolean(boolean: Boolean): JsonBoolean = JsonBoolean(boolean)

    override fun convertList(list: MutableList<JsonElement>): JsonArray = JsonArray(list)

    override fun convertObject(objectDataMap: Map<String, JsonElement>): JsonObject =
        JsonObject(objectDataMap.map { JsonKeyValuePair(it.key, it.value) }.toMutableList())

    override fun createNullNode(): JsonNullNode = JsonNullNode()
}