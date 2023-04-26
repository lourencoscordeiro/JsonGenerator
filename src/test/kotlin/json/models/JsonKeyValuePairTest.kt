package json.models

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class JsonKeyValuePairTest {

    @Test
    fun `returns correct values`() {
        val jsonNumber = JsonNumber(101, 3)
        val jsonKeyParValue = JsonKeyValuePair("property", jsonNumber)

        assertEquals(3, jsonKeyParValue.depth)
        assertEquals(jsonNumber, jsonKeyParValue.value)
        assertEquals("\"property\": 101", jsonKeyParValue.toPrettyJsonString())
    }

}