package json.generator.mapping

import json.models.*

/**
 * Default implementation for [TypeMapping].
 */
class DefaultTypeMapping : TypeMapping {

    override fun convertNumber(number: Number, depth: Int): JsonNumber = JsonNumber(number, depth)

    override fun convertString(string: String, depth: Int): JsonString = JsonString(string, depth)

    override fun convertBoolean(boolean: Boolean, depth: Int): JsonBoolean = JsonBoolean(boolean, depth)

    override fun convertList(list: List<JsonElement>, depth: Int): JsonArray = JsonArray(list, depth)

    override fun convertObject(objectDataMap: Map<String, JsonElement>, depth: Int): JsonObject =
        JsonObject(objectDataMap.map { JsonKeyValuePair(it.key, it.value) }, depth)

    override fun createNullNode(depth: Int): JsonNullNode = JsonNullNode(depth)
}