package json.visitors

import json.models.JsonElement
import json.models.JsonKeyValuePair
import json.models.JsonNumber
import json.visitors.interfaces.Visitor

class NumberValidationVisitor: Visitor {
    var isValid = true
        private set

    override fun visit(element: JsonElement) {
        if (element is JsonKeyValuePair && element.getName() == "numero") {
            if (element.getValue() !is JsonNumber || (element.getValue() as JsonNumber).getValue() !is Int) {
                isValid = false
            }
        }
    }
}