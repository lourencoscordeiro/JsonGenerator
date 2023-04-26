package json.models

/**
 * Representation of a null node in JSON.
 */
class JsonNullNode(override val depth: Int = 0) : JsonElement {

    override fun toPrettyJsonString(): String = "null"

    override fun equals(other: Any?): Boolean = if (other is JsonNullNode) depth == other.depth else false

    override fun hashCode(): Int = depth
}