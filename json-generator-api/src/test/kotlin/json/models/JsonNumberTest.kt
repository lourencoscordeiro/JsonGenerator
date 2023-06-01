package json.models

import org.junit.jupiter.api.Assertions.*
import org.junit.Test

internal class JsonNumberTest {

    @Test
    fun `returns correct values`() {
        val jsonNumber = JsonNumber(101, 3)

        assertEquals(3, jsonNumber.depth)
        assertEquals(101, jsonNumber.value)
        assertEquals("101", jsonNumber.toPrettyJsonString())
    }

}