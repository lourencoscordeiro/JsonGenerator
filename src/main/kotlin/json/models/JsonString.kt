package json.models

/**
 * Representation of a String in JSON.
 */
data class JsonString(val value: String, override val depth: Int = 0) : JsonElement {

    override fun toPrettyJsonString(): String = "\"$value\""

    override fun toString(): String = toPrettyJsonString()

}