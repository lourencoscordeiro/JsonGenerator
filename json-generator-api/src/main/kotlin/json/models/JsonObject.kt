package json.models

import json.models.observability.JsonElementObserver
import json.visitors.Visitor

/**
 * Representation of an Object in JSON. Consists on a map of its attributes (name and JSON Element).
 */
data class JsonObject(var attributes: MutableList<JsonKeyValuePair> = mutableListOf<JsonKeyValuePair>()) :
    JsonElement {

    override val observers: MutableList<JsonElementObserver> = mutableListOf()
    init {
        val duplicateAttributes = attributes.groupBy { it.name }.filter { it.value.size > 1 }.flatMap { it.value }
        if (duplicateAttributes.isNotEmpty()) {
            throw IllegalArgumentException("No duplicate keys on a JSON Object allowed!")
        }
    }


    override fun addElement(newValue: JsonElement) {
        if (attributes.any { it.name == (newValue as JsonKeyValuePair).name }) {
            throw IllegalArgumentException("No duplicate keys on a JSON Object allowed!")
        }
        if(newValue is JsonObject){
            newValue.attributes.forEach{
                addElement(it)
            }
        }else{
            val attribute = newValue as JsonKeyValuePair
            attributes.add(attribute)
            observers.forEach {
            attribute.addObserver(it)
            attribute.value.addObserver(it)
            }
            observers.forEach { it.addedElement(newValue) }
        }
    }

    override fun updateElement(newValue: JsonElement) {
        if (!attributes.any { it.name == (newValue as JsonKeyValuePair).name }) {
            throw IllegalArgumentException("No attribute '${(newValue as JsonKeyValuePair).name}' found on the JSON Object to be updated!")
        }
        val attribute: JsonKeyValuePair = attributes.find { it.name == (newValue as JsonKeyValuePair).name }!!
        attribute.updateElement((newValue as JsonKeyValuePair).value)
        observers.forEach { it.updatedElement(newValue) }
    }

    override fun eraseAll() {
        attributes = mutableListOf()
        notifyObservers()
    }
    fun notifyObservers(){
        observers.forEach { it.erasedAll() }
    }

    override fun eraseElement(valueToErase: JsonElement) {
        val index = attributes.indexOf(valueToErase)
        if (-1 != index) {
            attributes.removeAt(index)
            observers.forEach { it.erasedElement(this) }
        }
    }

    override fun accept(visitor: Visitor) {
        visitor.visit(this)
        attributes.forEach{
            it.accept(visitor)
        }
    }

    override fun toPrettyJsonString(depth: Int): String =
        attributes.joinToString(", ", "{", "\n${createIndentation(depth)}}") { "\n${it.toPrettyJsonString(depth + 1)}" }

    private fun createIndentation(indentationRatio: Int): String = "\t".repeat(indentationRatio)


    fun copy(): JsonObject {
        val jsonObject =  JsonObject(attributes = attributes.toMutableList())
        observers.forEach{
            jsonObject.addObserver(it)
        }
        return jsonObject
    }
}