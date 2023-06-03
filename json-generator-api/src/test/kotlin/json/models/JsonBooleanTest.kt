package json.models

import org.junit.jupiter.api.Assertions.*
import org.junit.Test

internal class JsonBooleanTest {

    @Test
    fun `returns correct values`() {
        val jsonBoolean = JsonBoolean(true)

        assertEquals(true, jsonBoolean.value)
        assertEquals("true", jsonBoolean.toPrettyJsonString(0))
    }
}