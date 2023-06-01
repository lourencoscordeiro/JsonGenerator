package json.generator.mapping

import json.models.*
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class DefaultTypeMappingTest {

    private val toTest = DefaultTypeMapping()

    @Test
    fun `convertNumber returns correct value`() {
        val number: Number = 3
        val length = 1

        val simpleJsonNumber = JsonNumber(number)
        val jsonNumberWithLength = JsonNumber(number, length)

        assertEquals(simpleJsonNumber, toTest.convertNumber(number, 0))
        assertEquals(jsonNumberWithLength, toTest.convertNumber(number, length))
    }

    @Test
    fun `convertString returns correct value`() {
        val string = "string"
        val length = 1

        val simpleJsonString = JsonString(string)
        val jsonStringWithLength = JsonString(string, length)

        assertEquals(simpleJsonString, toTest.convertString(string, 0))
        assertEquals(jsonStringWithLength, toTest.convertString(string, length))
    }

    @Test
    fun `convertBoolean returns correct value`() {
        val boolean = true
        val length = 1

        val simpleJsonBoolean = JsonBoolean(boolean)
        val jsonBooleanWithLength = JsonBoolean(boolean, length)

        assertEquals(simpleJsonBoolean, toTest.convertBoolean(boolean, 0))
        assertEquals(jsonBooleanWithLength, toTest.convertBoolean(boolean, length))
    }

    @Test
    fun `convertList returns correct value`() {
        val jsonString1 = JsonString("some text")
        val jsonString2 = JsonString("some other text")
        val listOfJsonElements = listOf(jsonString1, jsonString2)
        val depth = 1

        val simpleJsonList = JsonArray(listOfJsonElements)
        val jsonListWithDepth = JsonArray(listOfJsonElements, depth)

        assertEquals(simpleJsonList, toTest.convertList(listOfJsonElements, 0))
        assertEquals(jsonListWithDepth, toTest.convertList(listOfJsonElements, depth))
    }

    @Test
    fun `convertObject returns correct value`() {
        val jsonString1 = JsonString("some text")
        val jsonString2 = JsonString("some other text")
        val mapOfJsonElements = mapOf("string-1" to jsonString1, "string-2" to jsonString2)
        val listOfJsonKeyValuePair = listOf(
            JsonKeyValuePair("string-1", jsonString1),
            JsonKeyValuePair("string-2", jsonString2)
        )
        val depth = 1

        val simpleJsonObject = JsonObject(listOfJsonKeyValuePair)
        val jsonObjectWithDepth = JsonObject(listOfJsonKeyValuePair, depth)

        assertEquals(simpleJsonObject, toTest.convertObject(mapOfJsonElements, 0))
        assertEquals(jsonObjectWithDepth, toTest.convertObject(mapOfJsonElements, depth))
    }

    @Test
    fun `createNullNode returns correct value`() {
        val length = 1

        val simpleJsonNull = JsonNullNode()
        val jsonNullWithLength = JsonNullNode(length)

        assertEquals(simpleJsonNull, toTest.createNullNode(0))
        assertEquals(jsonNullWithLength, toTest.createNullNode(length))
    }

}