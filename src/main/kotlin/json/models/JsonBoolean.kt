package json.models

/**
 * Representation of a Boolean in JSON.
 */
data class JsonBoolean(val value: Boolean, override val depth: Int = 0) : JsonElement {

    override fun toPrettyJsonString(): String = value.toString()

    override fun toString(): String = toPrettyJsonString()

}