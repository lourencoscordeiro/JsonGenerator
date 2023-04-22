package json.models

/**
 * Represents a pair of values in JSON. Integrated into JsonObject and JsonList.
 */
data class JsonKeyValuePair(private val name: String, private val value: JsonElement) : JsonElement {

    override val depth: Int = value.depth

    override fun toPrettyJsonString(): String = "\"$name\": $value"

    override fun toString(): String = toPrettyJsonString()

}