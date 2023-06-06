package json.models.observability

import json.models.JsonElement

/**
 * Represents something that wants to be notified once some actions
 * are applied to a Simple Json Element.
 */
interface JsonElementObserver {

    fun addedElement(newValue: JsonElement) {}

    fun updatedElement(newValue: JsonElement) {}

    fun erasedElement(erasedValue: JsonElement) {}

    fun erasedAll() {}

}

interface JsonElementObservable {

    /**
     * Keeps all the [JsonElementObserver] of a Json Element.
     */
    val observers: MutableList<JsonElementObserver>

    fun addObserver(jsonElementObserver: JsonElementObserver) {
        observers.add(jsonElementObserver)
    }

    fun removeObserver(jsonElementObserver: JsonElementObserver) {
        observers.remove(jsonElementObserver)
    }


}



