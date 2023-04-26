package json.visitors

import json.models.JsonElement
import json.models.JsonObject
import json.visitors.interfaces.Visitor

class ObjectVisitor(private val properties:List<String>):Visitor {

    private val objects = mutableListOf<JsonObject>()

    override fun visit(obj: JsonObject) {
        if (hasProperties(obj)) {
            objects.add(obj)
        }
    }
/*
    override fun visit(element: JsonElement) {
        if(element is JsonObject)
            visit(element);
    }
*/
    private fun hasProperties(obj: JsonObject): Boolean {
        return properties.all { property -> obj.getAttributes().any { it.getName() == property } }
    }

    fun getObjects() = objects
}