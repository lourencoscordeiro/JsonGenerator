package json.generator.mapping

import json.models.*

/**
 * Abstraction responsible for converting Kotlin Types to the corresponding [JsonElement] type.
 */
interface TypeMapping {

    /**
     * Converts a Kotlin [Number] into a [JsonNumber].
     *
     * @param number original Kotlin Number.
     */
    fun convertNumber(number: Number, depth: Int): JsonNumber

    /**
     * Converts a Kotlin [String] into a [JsonString].
     *
     * @param string original Kotlin String.
     */
    fun convertString(string: String, depth: Int): JsonString

    /**
     * Converts a Kotlin [Boolean] into a [JsonBoolean].
     *
     * @param boolean original Kotlin Boolean.
     */
    fun convertBoolean(boolean: Boolean, depth: Int): JsonBoolean

    /**
     * Converts a Kotlin [List] into a [JsonArray].
     *
     * @param list original Kotlin List.
     */
    fun convertList(list: List<JsonElement>, depth: Int): JsonArray

    /**
     * Converts a Data Class data into a [JsonObject]
     *
     * @param objectDataMap object attributes (names and respective values)
     */
    fun convertObject(objectDataMap: Map<String, JsonElement>, depth: Int): JsonObject

    /**
     * Creates a JSON null node
     */
    fun createNullNode(depth: Int): JsonNullNode

    /**
     *  Creates default implementation for [TypeMapping] ([DefaultTypeMapping]).
     */
    companion object Factory {
        fun default(): TypeMapping = DefaultTypeMapping()
    }

}