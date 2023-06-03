package json.models

import org.junit.jupiter.api.Assertions.*
import org.junit.Test

internal class JsonStringTest {

    @Test
    fun `returns correct values`() {
        val jsonString = JsonString("some text", 3)

        assertEquals(3, jsonString.depth)
        assertEquals("some text", jsonString.value)
        assertEquals("\"some text\"", jsonString.toPrettyJsonString())
    }

//    @Test
//    fun `updates value correctly`() {
//        val jsonString = JsonString("some text")
//        jsonString.updateValue("some other text")
//        assertEquals("some other text", jsonString.value)
//    }

}