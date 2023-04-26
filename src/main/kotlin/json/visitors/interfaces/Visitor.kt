package json.visitors.interfaces

import json.models.*

/**
 *Interface that defines the methods required for implementing a visitor pattern for JSON elements.
 */
interface Visitor {
    /**
    Visits a JSON list element.
    */
    fun visit(list: JsonList){}

    /**
    Visits a JSON number element.
    */
    fun visit(number: JsonNumber){}

    /**
    Visits a JSON string element.
    */
    fun visit(string: JsonString){}

    /**
    Visits a JSON boolean element.
    */
    fun visit(boolean: JsonBoolean){}

    /**
    Visits a JSON object element.
    */
    fun visit(obj: JsonObject){}

    /**
    Visits a JSON key-value pair element.
    */
    fun visit(keyValuePair: JsonKeyValuePair){}
}