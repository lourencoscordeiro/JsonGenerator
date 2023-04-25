package json.visitors

import json.models.JsonElement
import json.models.JsonKeyValuePair
import json.models.JsonList
import json.models.JsonObject
import json.visitors.interfaces.Visitor

class InscritosValidationVisitor:Visitor {
    var isValid = true
        private set
    private var reference: List<String>? = null

    override fun visit(element: JsonElement) {
        if (element is JsonKeyValuePair && element.getName() == "inscritos" && element.getValue() is JsonList) {
            val jsonList = element.getValue() as JsonList
            jsonList.getElements().forEach { jsonElement ->
                if (jsonElement is JsonObject) {
                    val objectStructure = jsonElement.getAttributes().map { it.getName() }
                    if (reference == null) {
                        reference = objectStructure
                    } else if (reference != objectStructure) {
                        isValid = false
                    }
                } else {
                    isValid = false
                }
            }
        }
    }
}