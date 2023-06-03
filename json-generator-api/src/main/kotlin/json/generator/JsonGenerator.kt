package json.generator

import json.generator.annotations.AsJsonString
import json.generator.annotations.JsonExclude
import json.generator.annotations.RenamedJsonProperty
import json.generator.mapping.TypeMapping
import json.models.JsonElement
import json.models.JsonArray
import json.models.JsonKeyValuePair
import json.models.JsonObject
import util.dataClassFields
import kotlin.reflect.full.findAnnotations

/**
 * Generator of Json Model based on [JsonElement] data models.
 */
data class JsonGenerator(val typeMapping: TypeMapping = TypeMapping.default()) {

    fun toJsonElement(value: Any?, depth: Int = 0): JsonElement {

        return when (value) {

            is String -> typeMapping.convertString(value, depth)
            is Enum<*> -> typeMapping.convertString(value.toString(), depth)
            is Boolean -> typeMapping.convertBoolean(value, depth)
            is Char -> typeMapping.convertString(value.toString(), depth)

            is Byte, is Short, is Int, is Long, is Float, is Double ->
                typeMapping.convertNumber(value as Number, depth)

            is List<*>, is Set<*> -> mapList(value as Collection<*>, depth)
            is Array<*> -> mapList((value.toCollection(ArrayList())), depth)

            is Pair<*, *> -> JsonKeyValuePair(value.first as String, toJsonElement(value.second, depth))
            is Map<*, *> -> typeMapping.convertObject(value.map { (key, value) ->
                key.toString() to toJsonElement(value, depth + 1) }.toMap(), depth)

            else -> {
                if (value == null) return typeMapping.createNullNode(depth)
                return mapUnknownObject(value, depth)
            }
        }
    }

    @Suppress("EXPERIMENTAL_IS_NOT_ENABLED")
    @OptIn(ExperimentalStdlibApi::class)
    private fun mapUnknownObject(value: Any, depth: Int): JsonObject {
        return typeMapping.convertObject(
            value::class.dataClassFields
                .filter { it.findAnnotations<JsonExclude>().isEmpty() }
                .associate {
                    val propertyName = if (it.findAnnotations<RenamedJsonProperty>()
                            .isNotEmpty()
                    ) it.findAnnotations<RenamedJsonProperty>().first().name else it.name
                    val propertyValue =
                        if (it.findAnnotations<AsJsonString>().isEmpty()) it.call(value) else it.call(value)
                            .toString()
                    propertyName to toJsonElement(propertyValue, depth + 1)
                }, depth
        )
    }

    private fun mapList(list: Collection<*>, depth: Int): JsonArray {
        return typeMapping.convertList(list.map { toJsonElement(it, depth + 1) }, depth)
    }

}