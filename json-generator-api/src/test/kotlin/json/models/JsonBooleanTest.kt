package json.models

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class JsonBooleanTest {

    @Test
    fun `returns correct values`() {
        val jsonBoolean = JsonBoolean(true, 3)

        assertEquals(3, jsonBoolean.depth)
        assertEquals(true, jsonBoolean.value)
        assertEquals("true", jsonBoolean.toPrettyJsonString())
    }
}