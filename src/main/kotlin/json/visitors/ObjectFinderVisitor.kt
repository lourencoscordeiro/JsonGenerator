package json.visitors

import json.models.JsonObject
import json.visitors.interfaces.Visitor
/**
 * Checks all objects to find the ones that have the same properties as the ones given in a list in the constructor
 */
class ObjectFinderVisitor(private val properties:List<String>):Visitor {

    private val objects = mutableListOf<JsonObject>()

    /**
     * Checks and filters all objects based on whether they contain the properties given or not
     */
    override fun visit(obj: JsonObject) {
        if (hasProperties(obj)) {
            objects.add(obj)
        }
    }

    private fun hasProperties(obj: JsonObject): Boolean {
        return properties.all { property -> obj.getAttributes().any { it.getName() == property } }
    }

    fun getObjects() = objects
}