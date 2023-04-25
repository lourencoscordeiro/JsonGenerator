package json.visitors

import json.models.JsonElement
import json.models.JsonKeyValuePair
import json.visitors.interfaces.Visitor

class ValueVisitor(private val property:String): Visitor {
    private val values = mutableListOf<JsonElement>()
    override fun visit(element:JsonElement) {
        if(element is JsonKeyValuePair && element.getName() == property)
                    values.add(element.getValue())
    }

    fun getValues() = values
}