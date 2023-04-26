package json.visitors

import json.models.JsonElement
import json.models.JsonKeyValuePair
import json.visitors.interfaces.Visitor

/**
 * Given a property it finds all the values stored in that
 */
class ValueFinderVisitor(private val property:String): Visitor {
    private val values = mutableListOf<JsonElement>()

    /**
     * Get all the values from a certain property
     */
    override fun visit(keyValuePair: JsonKeyValuePair) {
        if(keyValuePair.getName() == property)
            values.add(keyValuePair.getValue())
    }

    fun getValues() = values
}