package json.visitors

import json.models.JsonKeyValuePair
import json.models.JsonList
import json.models.JsonObject

/**
 * Validate whether all elements in a JSON list/array have the same structure or not.
 *
 * Only implements visitation for [JsonList] because it is the only relevant
 * JSON element where list elements can be compared.
 */
class SameStructureArrayValidationVisitor : Visitor {

    private var isValid = true
    private var reference: List<String> = listOf()

    /**
     * @return the validity of the JSON List. Should only be called when visitation is over.
     */
    fun isValid(): Boolean = isValid

    /**
     * Visits a [JsonList] and performs the Visitor's job.
     */
    override fun visit(list: JsonList) {

        if (list.elements.all { it is JsonObject }) isValid =
            list.elements.map { it as JsonObject }
                .map { it.attributes.map { attribute -> attribute.name to attribute.value::class } }
                .distinctBy { it }
                .size == 1

        else isValid = list.elements.distinctBy { it::class }.size == 1
    }
}