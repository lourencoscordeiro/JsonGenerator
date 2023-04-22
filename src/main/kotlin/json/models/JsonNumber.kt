package json.models

/**
 * Representation of a String in JSON.
 */
data class JsonNumber(private val value: Number, override val depth: Int = 0) : JsonElement {

    override fun toPrettyJsonString(): String = value.toString()

    override fun toString(): String = toPrettyJsonString()

}