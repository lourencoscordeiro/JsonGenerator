package json.visitors

import json.models.JsonKeyValuePair
import json.models.JsonNumber

/**
 * Validate whether all properties with a given name in a JSON structure have the expected type [Int] or not.
 *
 * Only implements visitation for [JsonKeyValuePair] because it is the only JSON Element that represents
 * a property with a name and value.
 */
data class IntegerPropertyValidationVisitor(val propertyName: String) : Visitor {

    private var isValid = true

    /**
     * Visits a [JsonKeyValuePair] and performs the Visitor's job.
     */
    override fun visit(keyValuePair: JsonKeyValuePair) {
        if (keyValuePair.name == propertyName) {
            isValid = keyValuePair.value is JsonNumber && (keyValuePair.value as JsonNumber).value is Int
        }
    }

    /**
     * @return the validity of the JSON Object. Should only be called when visitation is over.
     */
    fun isValid(): Boolean = isValid
}