package json.generator

import SimpleTestDataClass
import SimpleTestDataClassWithAsJsonString
import SimpleTestDataClassWithJsonIgnore
import SimpleTestDataClassWithRenamedProperty
import TestEnum
import json.generator.mapping.TypeMapping
import json.models.*
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals

internal class JsonGeneratorTest {

    private val typeMappingMock: TypeMapping = mock()
    private val toTest: JsonGenerator = JsonGenerator(typeMappingMock)

    @Test
    fun `maps byte correctly`() {
        val byte: Byte = 101
        val expected = JsonNumber(byte)
        whenever(typeMappingMock.convertNumber(byte)).thenReturn(expected)

        val result = toTest.toJsonElement(byte)

        verify(typeMappingMock).convertNumber(byte as Number)
        assertEquals(expected, result)
    }

    @Test
    fun `maps int correctly`() {
        val int = 101
        val expected = JsonNumber(int)
        whenever(typeMappingMock.convertNumber(int)).thenReturn(expected)

        val result = toTest.toJsonElement(int)

        verify(typeMappingMock).convertNumber(int as Number)
        assertEquals(expected, result)
    }

    @Test
    fun `maps short correctly`() {
        val short = "101".toShort()
        val expected = JsonNumber(short)
        whenever(typeMappingMock.convertNumber(short)).thenReturn(expected)

        val result = toTest.toJsonElement(short)

        verify(typeMappingMock).convertNumber(short as Number)
        assertEquals(expected, result)
    }

    @Test
    fun `maps long correctly`() {
        val long = 101L
        val expected = JsonNumber(long)
        whenever(typeMappingMock.convertNumber(long)).thenReturn(expected)

        val result = toTest.toJsonElement(long)

        verify(typeMappingMock).convertNumber(long as Number)
        assertEquals(expected, result)
    }

    @Test
    fun `maps float correctly`() {
        val float = 101f
        val expected = JsonNumber(float)
        whenever(typeMappingMock.convertNumber(float)).thenReturn(expected)

        val result = toTest.toJsonElement(float)

        verify(typeMappingMock).convertNumber(float as Number)
        assertEquals(expected, result)
    }

    @Test
    fun `maps double correctly`() {
        val double = 101.0
        val expected = JsonNumber(double)
        whenever(typeMappingMock.convertNumber(double)).thenReturn(expected)

        val result = toTest.toJsonElement(double)

        verify(typeMappingMock).convertNumber(double as Number)
        assertEquals(expected, result)
    }

    @Test
    fun `maps string correctly`() {
        val string = "some text"
        val expected = JsonString(string)
        whenever(typeMappingMock.convertString(string)).thenReturn(expected)

        val result = toTest.toJsonElement(string)

        verify(typeMappingMock).convertString(string)
        assertEquals(expected, result)
    }

    @Test
    fun `maps enum correctly`() {
        val enum: TestEnum = TestEnum.PA
        val enumTextValue: String = enum.toString()
        val expected = JsonString(enumTextValue)

        whenever(typeMappingMock.convertString(enumTextValue)).thenReturn(expected)

        val result = toTest.toJsonElement(enum)

        verify(typeMappingMock).convertString(enumTextValue)
        assertEquals(expected, result)
    }

    @Test
    fun `maps boolean correctly`() {
        val boolean = true
        val expected = JsonBoolean(boolean)
        whenever(typeMappingMock.convertBoolean(boolean)).thenReturn(expected)

        val result = toTest.toJsonElement(boolean)

        verify(typeMappingMock).convertBoolean(boolean)
        assertEquals(expected, result)
    }

    @Test
    fun `maps char correctly`() {
        val char: Char = 'a'
        val expected = JsonString(char.toString())
        whenever(typeMappingMock.convertString(char.toString())).thenReturn(expected)

        val result = toTest.toJsonElement(char)

        verify(typeMappingMock).convertString(char.toString())
        assertEquals(expected, result)
    }

    @Test
    fun `maps list correctly`() {
        val list = listOf(1, 2, 3, 4)

        val jsonNumber1 = JsonNumber(1)
        val jsonNumber2 = JsonNumber(2)
        val jsonNumber3 = JsonNumber(3)
        val jsonNumber4 = JsonNumber(4)
        val listOfJsonNumber = listOf(jsonNumber1, jsonNumber2, jsonNumber3, jsonNumber4)

        val expected = JsonArray(listOfJsonNumber)

        whenever(typeMappingMock.convertNumber(1)).thenReturn(jsonNumber1)
        whenever(typeMappingMock.convertNumber(2)).thenReturn(jsonNumber2)
        whenever(typeMappingMock.convertNumber(3)).thenReturn(jsonNumber3)
        whenever(typeMappingMock.convertNumber(4)).thenReturn(jsonNumber4)
        whenever(typeMappingMock.convertList(listOfJsonNumber)).thenReturn(expected)

        val result = toTest.toJsonElement(list)

        verify(typeMappingMock).convertList(listOfJsonNumber)
        assertEquals(expected, result)
    }

    @Test
    fun `maps set correctly`() {
        val set = setOf(1, 2, 3, 4)

        val jsonNumber1 = JsonNumber(1)
        val jsonNumber2 = JsonNumber(2)
        val jsonNumber3 = JsonNumber(3)
        val jsonNumber4 = JsonNumber(4)
        val listOfJsonNumber = listOf(jsonNumber1, jsonNumber2, jsonNumber3, jsonNumber4)

        val expected = JsonArray(listOfJsonNumber)

        whenever(typeMappingMock.convertNumber(1)).thenReturn(jsonNumber1)
        whenever(typeMappingMock.convertNumber(2)).thenReturn(jsonNumber2)
        whenever(typeMappingMock.convertNumber(3)).thenReturn(jsonNumber3)
        whenever(typeMappingMock.convertNumber(4)).thenReturn(jsonNumber4)
        whenever(typeMappingMock.convertList(listOfJsonNumber)).thenReturn(expected)

        val result = toTest.toJsonElement(set)

        verify(typeMappingMock).convertList(listOfJsonNumber)
        assertEquals(expected, result)
    }

    @Test
    fun `maps array correctly`() {
        val set = arrayOf(1, 2, 3, 4)

        val jsonNumber1 = JsonNumber(1)
        val jsonNumber2 = JsonNumber(2)
        val jsonNumber3 = JsonNumber(3)
        val jsonNumber4 = JsonNumber(4)
        val listOfJsonNumber = listOf(jsonNumber1, jsonNumber2, jsonNumber3, jsonNumber4)

        val expected = JsonArray(listOfJsonNumber)

        whenever(typeMappingMock.convertNumber(1)).thenReturn(jsonNumber1)
        whenever(typeMappingMock.convertNumber(2)).thenReturn(jsonNumber2)
        whenever(typeMappingMock.convertNumber(3)).thenReturn(jsonNumber3)
        whenever(typeMappingMock.convertNumber(4)).thenReturn(jsonNumber4)
        whenever(typeMappingMock.convertList(listOfJsonNumber)).thenReturn(expected)

        val result = toTest.toJsonElement(set)

        verify(typeMappingMock).convertList(listOfJsonNumber)
        assertEquals(expected, result)
    }

    @Test
    fun `maps map with string as key correctly`() {
        val map = mapOf(
            "property1" to "value1",
            "property2" to "value2")

        val jsonStringP1 = JsonString("value1")
        val jsonStringP2 = JsonString("value2")

        val mapOfStringToJsonElement = mapOf(
            "property1" to jsonStringP1,
            "property2" to jsonStringP2,)

        val expected = JsonObject(listOf(
            JsonKeyValuePair("property1", jsonStringP1),
            JsonKeyValuePair("property2", jsonStringP2)))

        whenever(typeMappingMock.convertString("value1")).thenReturn(jsonStringP1)
        whenever(typeMappingMock.convertString("value2")).thenReturn(jsonStringP2)
        whenever(typeMappingMock.convertObject(mapOfStringToJsonElement)).thenReturn(expected)

        val result = toTest.toJsonElement(map)

        verify(typeMappingMock).convertObject(mapOfStringToJsonElement)
        assertEquals(expected, result)
    }

    @Test
    fun `maps map with object as key correctly`() {
        val simpleTestDataClass1 = SimpleTestDataClass(101, "some text", true)
        val simpleTestDataClass2 = SimpleTestDataClass(102, "some text", true)
        val map = mapOf(
            simpleTestDataClass1 to "value1",
            simpleTestDataClass2 to "value2")

        val jsonStringP1 = JsonString("value1")
        val jsonStringP2 = JsonString("value2")

        val mapOfStringToJsonElement = mapOf(
            simpleTestDataClass1.toString() to jsonStringP1,
            simpleTestDataClass2.toString() to jsonStringP2,)

        val expected = JsonObject(listOf(
            JsonKeyValuePair(simpleTestDataClass1.toString(), jsonStringP1),
            JsonKeyValuePair(simpleTestDataClass2.toString(), jsonStringP2)))

        whenever(typeMappingMock.convertString("value1")).thenReturn(jsonStringP1)
        whenever(typeMappingMock.convertString("value2")).thenReturn(jsonStringP2)
        whenever(typeMappingMock.convertObject(mapOfStringToJsonElement)).thenReturn(expected)

        val result = toTest.toJsonElement(map)

        verify(typeMappingMock).convertObject(mapOfStringToJsonElement)
        assertEquals(expected, result)
    }

    @Test
    fun `maps object correctly`() {
        val simpleTestDataClass = SimpleTestDataClass(101, "some text", true)
        val objectDataMap = mapOf(
            "number" to JsonNumber(101),
            "name" to JsonString("some text"),
            "isInternational" to JsonBoolean(true))

        val expected = JsonObject(
            listOf(
                JsonKeyValuePair("number", JsonNumber(101)),
                JsonKeyValuePair("name", JsonString("some text")),
                JsonKeyValuePair("isInternational", JsonBoolean(true))))

        whenever(typeMappingMock.convertNumber(101)).thenReturn(JsonNumber(101))
        whenever(typeMappingMock.convertString("some text")).thenReturn(JsonString("some text"))
        whenever(typeMappingMock.convertBoolean(true)).thenReturn(JsonBoolean(true))
        whenever(typeMappingMock.convertObject(objectDataMap)).thenReturn(expected)

        val result = toTest.toJsonElement(simpleTestDataClass)

        verify(typeMappingMock).convertObject(objectDataMap)
        assertEquals(expected, result)
    }

    @Test
    fun `maps object with JsonExclude correctly`() {
        val simpleTestDataClassWithJsonIgnore = SimpleTestDataClassWithJsonIgnore(101, "some text", true)
        val objectDataMap = mapOf(
            "number" to JsonNumber(101),
            "name" to JsonString("some text"))

        val expected = JsonObject(
            listOf(
                JsonKeyValuePair("number", JsonNumber(101)),
                JsonKeyValuePair("name", JsonString("some text"))))

        whenever(typeMappingMock.convertNumber(101)).thenReturn(JsonNumber(101))
        whenever(typeMappingMock.convertString("some text")).thenReturn(JsonString("some text"))
        whenever(typeMappingMock.convertObject(objectDataMap)).thenReturn(expected)

        val result = toTest.toJsonElement(simpleTestDataClassWithJsonIgnore)

        verify(typeMappingMock).convertObject(objectDataMap)
        assertEquals(expected, result)
    }

    @Test
    fun `maps object correctly with AsJsonString`() {
        val simpleTestDataClass = SimpleTestDataClassWithAsJsonString(101, "some text", true)
        val objectDataMap = mapOf(
            "number" to JsonNumber(101),
            "name" to JsonString("some text"),
            "isInternational" to JsonString("true"))

        val expected = JsonObject(
            listOf(
                JsonKeyValuePair("number", JsonNumber(101)),
                JsonKeyValuePair("name", JsonString("some text")),
                JsonKeyValuePair("isInternational", JsonString("true"))))

        whenever(typeMappingMock.convertNumber(101)).thenReturn(JsonNumber(101))
        whenever(typeMappingMock.convertString("some text")).thenReturn(JsonString("some text"))
        whenever(typeMappingMock.convertString("true")).thenReturn(JsonString("true"))
        whenever(typeMappingMock.convertObject(objectDataMap)).thenReturn(expected)

        val result = toTest.toJsonElement(simpleTestDataClass)

        verify(typeMappingMock).convertObject(objectDataMap)
        assertEquals(expected, result)
    }

    @Test
    fun `maps object correctly wth renamed json property`() {
        val simpleTestDataClassWithRenamedProperty = SimpleTestDataClassWithRenamedProperty(101, "some text", true)
        val objectDataMap = mapOf(
            "number" to JsonNumber(101),
            "name" to JsonString("some text"),
            "isForeigner" to JsonBoolean(true))

        val expected = JsonObject(
            listOf(
                JsonKeyValuePair("number", JsonNumber(101)),
                JsonKeyValuePair("name", JsonString("some text")),
                JsonKeyValuePair("isForeigner", JsonBoolean(true))))

        whenever(typeMappingMock.convertNumber(101)).thenReturn(JsonNumber(101))
        whenever(typeMappingMock.convertString("some text")).thenReturn(JsonString("some text"))
        whenever(typeMappingMock.convertBoolean(true)).thenReturn(JsonBoolean(true))
        whenever(typeMappingMock.convertObject(objectDataMap)).thenReturn(expected)

        val result = toTest.toJsonElement(simpleTestDataClassWithRenamedProperty)

        verify(typeMappingMock).convertObject(objectDataMap)
        assertEquals(expected, result)

        println(result)
    }

    @Test
    fun `maps null value correctly`() {
        val value = null
        val expected = JsonNullNode()

        whenever(typeMappingMock.createNullNode()).thenReturn(expected)

        val result = toTest.toJsonElement(value)

        verify(typeMappingMock).createNullNode()
        assertEquals(expected, result)
    }
}