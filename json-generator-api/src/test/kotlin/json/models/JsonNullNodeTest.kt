package json.models

import org.junit.jupiter.api.Assertions.*
import org.junit.Test

internal class JsonNullNodeTest {

    @Test
    fun `returns correct values`() {
        val jsonNullNode = JsonNullNode()
        assertEquals("null", jsonNullNode.toPrettyJsonString(0))
    }

}