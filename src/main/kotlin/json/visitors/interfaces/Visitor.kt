package json.visitors.interfaces

import json.models.*

interface Visitor {
    /*fun visit(list:JsonList){ }
    fun visit(number:JsonNumber) { }
    fun visit(string: JsonString){ }
    fun visit(boolean: JsonBoolean){ }
    fun visit(_object:JsonObject){ }*/
    fun visit(element: JsonElement){ }
}