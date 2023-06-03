package json.generator.mapping

import json.models.*
import org.junit.Test
import kotlin.test.assertEquals

internal class DefaultTypeMappingTest {

    private val toTest = DefaultTypeMapping()

    @Test
    fun `convertNumber returns correct value`() {
        val number: Number = 3

        val simpleJsonNumber = JsonNumber(number)
        val jsonNumberWithLength = JsonNumber(number)

        assertEquals(simpleJsonNumber, toTest.convertNumber(number))
        assertEquals(jsonNumberWithLength, toTest.convertNumber(number))
    }

    @Test
    fun `convertString returns correct value`() {
        val string = "string"

        val simpleJsonString = JsonString(string)
        val jsonStringWithLength = JsonString(string)

        assertEquals(simpleJsonString, toTest.convertString(string))
        assertEquals(jsonStringWithLength, toTest.convertString(string))
    }

    @Test
    fun `convertBoolean returns correct value`() {
        val boolean = true

        val simpleJsonBoolean = JsonBoolean(boolean)
        val jsonBooleanWithLength = JsonBoolean(boolean)

        assertEquals(simpleJsonBoolean, toTest.convertBoolean(boolean))
        assertEquals(jsonBooleanWithLength, toTest.convertBoolean(boolean))
    }

    @Test
    fun `convertList returns correct value`() {
        val jsonString1 = JsonString("some text")
        val jsonString2 = JsonString("some other text")
        val listOfJsonElements = listOf(jsonString1, jsonString2)

        val simpleJsonList = JsonArray(listOfJsonElements)
        val jsonListWithDepth = JsonArray(listOfJsonElements)

        assertEquals(simpleJsonList, toTest.convertList(listOfJsonElements))
        assertEquals(jsonListWithDepth, toTest.convertList(listOfJsonElements))
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
        val jsonObjectWithDepth = JsonObject(listOfJsonKeyValuePair)

        assertEquals(simpleJsonObject, toTest.convertObject(mapOfJsonElements))
        assertEquals(jsonObjectWithDepth, toTest.convertObject(mapOfJsonElements))
    }

}