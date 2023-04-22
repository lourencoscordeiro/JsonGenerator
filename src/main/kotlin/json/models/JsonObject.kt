package json.models

/**
 * Representation of an Object in JSON. Consists on a map of its attributes (name and JSON Element).
 */
data class JsonObject(private val attributes: List<JsonKeyValuePair> = listOf(), override val depth: Int = 0) :
    JsonElement {

    override fun toPrettyJsonString(): String =
        attributes.joinToString(", ", "{", "\n${createIndentation()}}") { "\n${createIndentation(it.depth)}$it" }

    override fun toString(): String = toPrettyJsonString()

    private fun createIndentation(indentationRatio: Int = depth): String = "\t".repeat(indentationRatio)

}