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
    fun convertNumber(number: Number): JsonNumber

    /**
     * Converts a Kotlin [String] into a [JsonString].
     *
     * @param string original Kotlin String.
     */
    fun convertString(string: String): JsonString

    /**
     * Converts a Kotlin [Boolean] into a [JsonBoolean].
     *
     * @param boolean original Kotlin Boolean.
     */
    fun convertBoolean(boolean: Boolean): JsonBoolean

    /**
     * Converts a Kotlin [List] into a [JsonArray].
     *
     * @param list original Kotlin List.
     */
    fun convertList(list: MutableList<JsonElement>): JsonArray

    /**
     * Converts a Data Class data into a [JsonObject]
     *
     * @param objectDataMap object attributes (names and respective values)
     */
    fun convertObject(objectDataMap: Map<String, JsonElement>): JsonObject

    /**
     * Creates a JSON null node
     */
    fun createNullNode(): JsonNullNode

    /**
     *  Creates default implementation for [TypeMapping] ([DefaultTypeMapping]).
     */
    companion object Factory {
        fun default(): TypeMapping = DefaultTypeMapping()
    }

}