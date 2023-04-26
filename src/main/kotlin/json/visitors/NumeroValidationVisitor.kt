package json.visitors

import json.models.JsonKeyValuePair
import json.models.JsonNumber
import json.visitors.interfaces.Visitor

/**
 * Validate "numero" property for a certain type
 */
class NumeroValidationVisitor: Visitor {
    var isValid = true
        private set

    /**
     * Verifies that the "numero" property is allways of type Int
     */
    override fun visit(jsonKeyValuePair: JsonKeyValuePair) {
        if (jsonKeyValuePair.getName() == "numero") {
            if (jsonKeyValuePair.getValue() !is JsonNumber || (jsonKeyValuePair.getValue() as JsonNumber).getValue() !is Int) {
                isValid = false
            }
        }
    }
}