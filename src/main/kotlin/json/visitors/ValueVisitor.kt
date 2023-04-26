package json.visitors

import json.models.JsonElement
import json.models.JsonKeyValuePair
import json.visitors.interfaces.Visitor

class ValueVisitor(private val property:String): Visitor {
    private val values = mutableListOf<JsonElement>()


    override fun visit(keyValuePair: JsonKeyValuePair) {
        if(keyValuePair.getName() == property)
            values.add(keyValuePair.getValue())
    }

    fun getValues() = values
}