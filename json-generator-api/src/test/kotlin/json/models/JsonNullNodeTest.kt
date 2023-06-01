package json.models

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class JsonNullNodeTest {

    @Test
    fun `returns correct values`() {
        val jsonNullNode = JsonNullNode(3)

        assertEquals(3, jsonNullNode.depth)
        assertEquals("null", jsonNullNode.toPrettyJsonString())
    }

}