package json.visitors

import json.models.JsonElement
import json.models.JsonKeyValuePair

/**
 * Given a property it finds all the values in the JSON tree stored with that property name.
 */
class ValueFinderVisitor(private val propertyName: String) : Visitor {

    private val values = mutableListOf<JsonElement>()

    /**
     * Visits a [JsonKeyValuePair] and performs the Visitor's job.
     */
    override fun visit(keyValuePair: JsonKeyValuePair) {
        if (keyValuePair.name == propertyName)
            values.add(keyValuePair.value)
    }

    /**
     * @return the values of the JSON tree in which the property name matches [ValueFinderVisitor.propertyName].
     * Should only be called when visitation is over.
     */
    fun getValues() = listOf(values).flatten()
}