package json.visitors

import json.models.JsonObject

/**
 * Checks and filters all objects based on whether they contain the properties given or not.
 */
class ObjectFinderVisitor(private val properties: List<String>) : Visitor {

    private val objects = mutableListOf<JsonObject>()

    /**
     * Visits a [JsonObject] and performs the Visitor's job.
     */
    override fun visit(obj: JsonObject) {
        if (hasProperties(obj)) {
            objects.add(obj)
        }
    }

    /**
     * @return the objects of the JSON tree that contain the properties given.
     * Should only be called when visitation is over.
     */
    fun getObjects() = listOf(objects).flatten()

    private fun hasProperties(obj: JsonObject): Boolean {
        return properties.all { property -> obj.attributes.any { it.name == property } }
    }
}