package json.models

import org.junit.jupiter.api.Assertions.*
import org.junit.Test

internal class JsonListTest {

    @Test
    fun `returns correct values`() {
        val jsonNumber1 = JsonNumber(101, 1)
        val jsonNumber2 = JsonNumber(102, 1)
        val jsonNumber3 = JsonNumber(103, 1)
        val listOfJsonNumbers = listOf(jsonNumber1, jsonNumber2, jsonNumber3)

        val jsonList = JsonArray(listOfJsonNumbers, 0)

        assertEquals(0, jsonList.depth)
        assertEquals(listOfJsonNumbers, jsonList.elements)

        val expectedStr = """
            [
            	101,
             	102,
             	103
            ]
        """.trimIndent()
        assertEquals(expectedStr, jsonList.toPrettyJsonString())
    }

}