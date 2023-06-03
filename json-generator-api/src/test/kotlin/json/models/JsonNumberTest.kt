package json.models

import org.junit.jupiter.api.Assertions.*
import org.junit.Test

internal class JsonNumberTest {

    @Test
    fun `returns correct values`() {
        val jsonNumber = JsonNumber(101)

        assertEquals(101, jsonNumber.value)
        assertEquals("101", jsonNumber.toPrettyJsonString(0))
    }

}