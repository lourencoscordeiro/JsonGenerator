package json.models

import org.junit.jupiter.api.Assertions.*
import org.junit.Test

internal class JsonKeyValuePairTest {

    @Test
    fun `returns correct values`() {
        val jsonNumber = JsonNumber(101)
        val jsonKeyParValue = JsonKeyValuePair("property", jsonNumber)

        assertEquals(jsonNumber, jsonKeyParValue.value)
        assertEquals("\"property\": 101", jsonKeyParValue.toPrettyJsonString(0))
    }

}