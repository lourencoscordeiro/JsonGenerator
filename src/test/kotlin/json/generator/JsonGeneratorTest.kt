package json.generator

import SimpleTestDataClass
import SimpleTestDataClassWithAsJsonString
import SimpleTestDataClassWithJsonIgnore
import SimpleTestDataClassWithRenamedProperty
import TestEnum
import json.generator.mapping.TypeMapping
import json.models.*
import org.junit.jupiter.api.Test
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
        whenever(typeMappingMock.convertNumber(byte, 0)).thenReturn(expected)

        val result = toTest.toJsonElement(byte)

        verify(typeMappingMock).convertNumber(byte as Number, 0)
        assertEquals(expected, result)
    }

    @Test
    fun `maps int correctly`() {
        val int = 101
        val expected = JsonNumber(int)
        whenever(typeMappingMock.convertNumber(int, 0)).thenReturn(expected)

        val result = toTest.toJsonElement(int)

        verify(typeMappingMock).convertNumber(int as Number, 0)
        assertEquals(expected, result)
    }

    @Test
    fun `maps short correctly`() {
        val short = "101".toShort()
        val expected = JsonNumber(short)
        whenever(typeMappingMock.convertNumber(short, 0)).thenReturn(expected)

        val result = toTest.toJsonElement(short)

        verify(typeMappingMock).convertNumber(short as Number, 0)
        assertEquals(expected, result)
    }

    @Test
    fun `maps long correctly`() {
        val long = 101L
        val expected = JsonNumber(long)
        whenever(typeMappingMock.convertNumber(long, 0)).thenReturn(expected)

        val result = toTest.toJsonElement(long)

        verify(typeMappingMock).convertNumber(long as Number, 0)
        assertEquals(expected, result)
    }

    @Test
    fun `maps float correctly`() {
        val float = 101f
        val expected = JsonNumber(float)
        whenever(typeMappingMock.convertNumber(float, 0)).thenReturn(expected)

        val result = toTest.toJsonElement(float)

        verify(typeMappingMock).convertNumber(float as Number, 0)
        assertEquals(expected, result)
    }

    @Test
    fun `maps double correctly`() {
        val double = 101.0
        val expected = JsonNumber(double)
        whenever(typeMappingMock.convertNumber(double, 0)).thenReturn(expected)

        val result = toTest.toJsonElement(double)

        verify(typeMappingMock).convertNumber(double as Number, 0)
        assertEquals(expected, result)
    }

    @Test
    fun `maps string correctly`() {
        val string = "some text"
        val expected = JsonString(string)
        whenever(typeMappingMock.convertString(string, 0)).thenReturn(expected)

        val result = toTest.toJsonElement(string)

        verify(typeMappingMock).convertString(string, 0)
        assertEquals(expected, result)
    }

    @Test
    fun `maps enum correctly`() {
        val enum: TestEnum = TestEnum.PA
        val enumTextValue: String = enum.toString()
        val expected = JsonString(enumTextValue)

        whenever(typeMappingMock.convertString(enumTextValue, 0)).thenReturn(expected)

        val result = toTest.toJsonElement(enum)

        verify(typeMappingMock).convertString(enumTextValue, 0)
        assertEquals(expected, result)
    }

    @Test
    fun `maps boolean correctly`() {
        val boolean = true
        val expected = JsonBoolean(boolean)
        whenever(typeMappingMock.convertBoolean(boolean, 0)).thenReturn(expected)

        val result = toTest.toJsonElement(boolean)

        verify(typeMappingMock).convertBoolean(boolean, 0)
        assertEquals(expected, result)
    }

    @Test
    fun `maps char correctly`() {
        val char: Char = 'a'
        val expected = JsonString(char.toString())
        whenever(typeMappingMock.convertString(char.toString(), 0)).thenReturn(expected)

        val result = toTest.toJsonElement(char)

        verify(typeMappingMock).convertString(char.toString(), 0)
        assertEquals(expected, result)
    }

    @Test
    fun `maps list correctly`() {
        val list = listOf(1, 2, 3, 4)

        val jsonNumber1 = JsonNumber(1, 1)
        val jsonNumber2 = JsonNumber(2, 1)
        val jsonNumber3 = JsonNumber(3, 1)
        val jsonNumber4 = JsonNumber(4, 1)
        val listOfJsonNumber = listOf(jsonNumber1, jsonNumber2, jsonNumber3, jsonNumber4)

        val expected = JsonList(listOfJsonNumber)

        whenever(typeMappingMock.convertNumber(1, 1)).thenReturn(jsonNumber1)
        whenever(typeMappingMock.convertNumber(2, 1)).thenReturn(jsonNumber2)
        whenever(typeMappingMock.convertNumber(3, 1)).thenReturn(jsonNumber3)
        whenever(typeMappingMock.convertNumber(4, 1)).thenReturn(jsonNumber4)
        whenever(typeMappingMock.convertList(listOfJsonNumber, 0)).thenReturn(expected)

        val result = toTest.toJsonElement(list)

        verify(typeMappingMock).convertList(listOfJsonNumber, 0)
        assertEquals(expected, result)
    }

    @Test
    fun `maps set correctly`() {
        val set = setOf(1, 2, 3, 4)

        val jsonNumber1 = JsonNumber(1, 1)
        val jsonNumber2 = JsonNumber(2, 1)
        val jsonNumber3 = JsonNumber(3, 1)
        val jsonNumber4 = JsonNumber(4, 1)
        val listOfJsonNumber = listOf(jsonNumber1, jsonNumber2, jsonNumber3, jsonNumber4)

        val expected = JsonList(listOfJsonNumber)

        whenever(typeMappingMock.convertNumber(1, 1)).thenReturn(jsonNumber1)
        whenever(typeMappingMock.convertNumber(2, 1)).thenReturn(jsonNumber2)
        whenever(typeMappingMock.convertNumber(3, 1)).thenReturn(jsonNumber3)
        whenever(typeMappingMock.convertNumber(4, 1)).thenReturn(jsonNumber4)
        whenever(typeMappingMock.convertList(listOfJsonNumber, 0)).thenReturn(expected)

        val result = toTest.toJsonElement(set)

        verify(typeMappingMock).convertList(listOfJsonNumber, 0)
        assertEquals(expected, result)
    }

    @Test
    fun `maps array correctly`() {
        val set = arrayOf(1, 2, 3, 4)

        val jsonNumber1 = JsonNumber(1, 1)
        val jsonNumber2 = JsonNumber(2, 1)
        val jsonNumber3 = JsonNumber(3, 1)
        val jsonNumber4 = JsonNumber(4, 1)
        val listOfJsonNumber = listOf(jsonNumber1, jsonNumber2, jsonNumber3, jsonNumber4)

        val expected = JsonList(listOfJsonNumber)

        whenever(typeMappingMock.convertNumber(1, 1)).thenReturn(jsonNumber1)
        whenever(typeMappingMock.convertNumber(2, 1)).thenReturn(jsonNumber2)
        whenever(typeMappingMock.convertNumber(3, 1)).thenReturn(jsonNumber3)
        whenever(typeMappingMock.convertNumber(4, 1)).thenReturn(jsonNumber4)
        whenever(typeMappingMock.convertList(listOfJsonNumber, 0)).thenReturn(expected)

        val result = toTest.toJsonElement(set)

        verify(typeMappingMock).convertList(listOfJsonNumber, 0)
        assertEquals(expected, result)

        println(result.toPrettyJsonString())
    }

    @Test
    fun `maps map with string as key correctly`() {
        val map = mapOf(
            "property1" to "value1",
            "property2" to "value2")

        val jsonStringP1 = JsonString("value1", 1)
        val jsonStringP2 = JsonString("value2", 1)

        val mapOfStringToJsonElement = mapOf(
            "property1" to jsonStringP1,
            "property2" to jsonStringP2,)

        val expected = JsonObject(listOf(
            JsonKeyValuePair("property1", jsonStringP1),
            JsonKeyValuePair("property2", jsonStringP2)))

        whenever(typeMappingMock.convertString("value1", 1)).thenReturn(jsonStringP1)
        whenever(typeMappingMock.convertString("value2", 1)).thenReturn(jsonStringP2)
        whenever(typeMappingMock.convertObject(mapOfStringToJsonElement, 0)).thenReturn(expected)

        val result = toTest.toJsonElement(map)

        verify(typeMappingMock).convertObject(mapOfStringToJsonElement, 0)
        assertEquals(expected, result)
    }

    @Test
    fun `maps map with object as key correctly`() {
        val simpleTestDataClass1 = SimpleTestDataClass(101, "some text", true)
        val simpleTestDataClass2 = SimpleTestDataClass(102, "some text", true)
        val map = mapOf(
            simpleTestDataClass1 to "value1",
            simpleTestDataClass2 to "value2")

        val jsonStringP1 = JsonString("value1", 1)
        val jsonStringP2 = JsonString("value2", 1)

        val mapOfStringToJsonElement = mapOf(
            simpleTestDataClass1.toString() to jsonStringP1,
            simpleTestDataClass2.toString() to jsonStringP2,)

        val expected = JsonObject(listOf(
            JsonKeyValuePair(simpleTestDataClass1.toString(), jsonStringP1),
            JsonKeyValuePair(simpleTestDataClass2.toString(), jsonStringP2)))

        whenever(typeMappingMock.convertString("value1", 1)).thenReturn(jsonStringP1)
        whenever(typeMappingMock.convertString("value2", 1)).thenReturn(jsonStringP2)
        whenever(typeMappingMock.convertObject(mapOfStringToJsonElement, 0)).thenReturn(expected)

        val result = toTest.toJsonElement(map)

        verify(typeMappingMock).convertObject(mapOfStringToJsonElement, 0)
        assertEquals(expected, result)
    }

    @Test
    fun `maps simple object correctly`() {
        val simpleTestDataClass = SimpleTestDataClass(101, "some text", true)
        val objectDataMap = mapOf(
            "number" to JsonNumber(101, 1),
            "name" to JsonString("some text", 1),
            "isInternational" to JsonBoolean(true, 1))

        val expected = JsonObject(
            listOf(
                JsonKeyValuePair("number", JsonNumber(101, 1)),
                JsonKeyValuePair("name", JsonString("some text", 1)),
                JsonKeyValuePair("isInternational", JsonBoolean(true, 1))))

        whenever(typeMappingMock.convertNumber(101, 1)).thenReturn(JsonNumber(101, 1))
        whenever(typeMappingMock.convertString("some text", 1)).thenReturn(JsonString("some text", 1))
        whenever(typeMappingMock.convertBoolean(true, 1)).thenReturn(JsonBoolean(true, 1))
        whenever(typeMappingMock.convertObject(objectDataMap, 0)).thenReturn(expected)

        val result = toTest.toJsonElement(simpleTestDataClass)

        verify(typeMappingMock).convertObject(objectDataMap, 0)
        assertEquals(expected, result)
    }

    @Test
    fun `maps simple object with JsonExclude correctly`() {
        val simpleTestDataClassWithJsonIgnore = SimpleTestDataClassWithJsonIgnore(101, "some text", true)
        val objectDataMap = mapOf(
            "number" to JsonNumber(101, 1),
            "name" to JsonString("some text", 1))

        val expected = JsonObject(
            listOf(
                JsonKeyValuePair("number", JsonNumber(101, 1)),
                JsonKeyValuePair("name", JsonString("some text", 1))))

        whenever(typeMappingMock.convertNumber(101, 1)).thenReturn(JsonNumber(101, 1))
        whenever(typeMappingMock.convertString("some text", 1)).thenReturn(JsonString("some text", 1))
        whenever(typeMappingMock.convertObject(objectDataMap, 0)).thenReturn(expected)

        val result = toTest.toJsonElement(simpleTestDataClassWithJsonIgnore)

        verify(typeMappingMock).convertObject(objectDataMap, 0)
        assertEquals(expected, result)
    }

    @Test
    fun `maps simple object correctly with AsJsonString`() {
        val simpleTestDataClass = SimpleTestDataClassWithAsJsonString(101, "some text", true)
        val objectDataMap = mapOf(
            "number" to JsonNumber(101, 1),
            "name" to JsonString("some text", 1),
            "isInternational" to JsonString("true", 1))

        val expected = JsonObject(
            listOf(
                JsonKeyValuePair("number", JsonNumber(101, 1)),
                JsonKeyValuePair("name", JsonString("some text", 1)),
                JsonKeyValuePair("isInternational", JsonString("true", 1))))

        whenever(typeMappingMock.convertNumber(101, 1)).thenReturn(JsonNumber(101, 1))
        whenever(typeMappingMock.convertString("some text", 1)).thenReturn(JsonString("some text", 1))
        whenever(typeMappingMock.convertString("true", 1)).thenReturn(JsonString("true", 1))
        whenever(typeMappingMock.convertObject(objectDataMap, 0)).thenReturn(expected)

        val result = toTest.toJsonElement(simpleTestDataClass)

        verify(typeMappingMock).convertObject(objectDataMap, 0)
        assertEquals(expected, result)
    }

    @Test
    fun `maps simple object correctly wth renamed json property`() {
        val simpleTestDataClassWithRenamedProperty = SimpleTestDataClassWithRenamedProperty(101, "some text", true)
        val objectDataMap = mapOf(
            "number" to JsonNumber(101, 1),
            "name" to JsonString("some text", 1),
            "isForeigner" to JsonBoolean(true, 1))

        val expected = JsonObject(
            listOf(
                JsonKeyValuePair("number", JsonNumber(101, 1)),
                JsonKeyValuePair("name", JsonString("some text", 1)),
                JsonKeyValuePair("isForeigner", JsonBoolean(true, 1))))

        whenever(typeMappingMock.convertNumber(101, 1)).thenReturn(JsonNumber(101, 1))
        whenever(typeMappingMock.convertString("some text", 1)).thenReturn(JsonString("some text", 1))
        whenever(typeMappingMock.convertBoolean(true, 1)).thenReturn(JsonBoolean(true, 1))
        whenever(typeMappingMock.convertObject(objectDataMap, 0)).thenReturn(expected)

        val result = toTest.toJsonElement(simpleTestDataClassWithRenamedProperty)

        verify(typeMappingMock).convertObject(objectDataMap, 0)
        assertEquals(expected, result)

        println(result)
    }

//    @Test
//    fun `maps simple object correctly`() {
//        val simpleTestDataClass = SimpleTestDataClass(101101, "Dave Farley", true)
//        val simpleTestDataClass2 = SimpleTestDataClass(101102, "Martin Fowler", true)
//        val simpleTestDataClass3 = SimpleTestDataClass(26503, "André Santos", false)
//        val listOfSimpleTestDataClass = listOf(simpleTestDataClass, simpleTestDataClass2, simpleTestDataClass3)
//        val complexTestDataClass = ComplexTestDataClass(TestEnum.PA, 6.0, null, listOfSimpleTestDataClass)
//
//        whenever(typeMappingMock.convertNumber(101101, 0)).thenReturn(JsonNumber(101101))
//        whenever(typeMappingMock.convertNumber(101102, 0)).thenReturn(JsonNumber(101102))
//        whenever(typeMappingMock.convertNumber(26503, 0)).thenReturn(JsonNumber(26503))
//
//        whenever(typeMappingMock.convertString("Dave Farley", 0)).thenReturn(JsonString("Dave Farley"))
//        whenever(typeMappingMock.convertString("Martin Fowler", 0)).thenReturn(JsonString("Martin Fowler"))
//        whenever(typeMappingMock.convertString("André Santos", 0)).thenReturn(JsonString("André Santos"))
//
//        whenever(typeMappingMock.convertBoolean(true, 0)).thenReturn(JsonBoolean(true))
//        whenever(typeMappingMock.convertBoolean(false, 0)).thenReturn(JsonBoolean(false))
//
//
//    }

    @Test
    fun `maps null value correctly`() {
        val value = null
        val expected = JsonNullNode()

        whenever(typeMappingMock.createNullNode(0)).thenReturn(expected)

        val result = toTest.toJsonElement(value)

        verify(typeMappingMock).createNullNode(0)
        assertEquals(expected, result)
    }
}