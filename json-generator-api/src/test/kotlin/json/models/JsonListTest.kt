package json.models

import org.junit.jupiter.api.Assertions.*
import org.junit.Test

internal class JsonListTest {

    @Test
    fun `returns correct values`() {
        val jsonNumber1 = JsonNumber(101)
        val jsonNumber2 = JsonNumber(102)
        val jsonNumber3 = JsonNumber(103)
        val listOfJsonNumbers = listOf(jsonNumber1, jsonNumber2, jsonNumber3)

        val jsonList = JsonArray(listOfJsonNumbers)

        assertEquals(listOfJsonNumbers, jsonList.elements)

        val expectedStr = """
            [
            	101,
             	102,
             	103
            ]
        """.trimIndent()
        assertEquals(expectedStr, jsonList.toPrettyJsonString(0))
    }

}