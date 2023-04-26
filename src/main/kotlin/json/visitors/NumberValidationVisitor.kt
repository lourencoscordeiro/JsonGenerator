package json.visitors

import json.models.JsonElement
import json.models.JsonKeyValuePair
import json.models.JsonNumber
import json.visitors.interfaces.Visitor

class NumberValidationVisitor: Visitor {
    var isValid = true
        private set

    override fun visit(jsonKeyValuePair: JsonKeyValuePair) {
        if (jsonKeyValuePair.getName() == "numero") {
            if (jsonKeyValuePair.getValue() !is JsonNumber || (jsonKeyValuePair.getValue() as JsonNumber).getValue() !is Int) {
                isValid = false
            }
        }
    }
}