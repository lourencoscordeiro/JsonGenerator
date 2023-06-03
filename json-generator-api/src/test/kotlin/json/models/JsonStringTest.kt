package json.models

import org.junit.jupiter.api.Assertions.*
import org.junit.Test

internal class JsonStringTest {

    @Test
    fun `returns correct values`() {
        val jsonString = JsonString("some text")

        assertEquals("some text", jsonString.value)
        assertEquals("\"some text\"", jsonString.toPrettyJsonString(0))
    }

//    @Test
//    fun `updates value correctly`() {
//        val jsonString = JsonString("some text")
//        jsonString.updateValue("some other text")
//        assertEquals("some other text", jsonString.value)
//    }

}