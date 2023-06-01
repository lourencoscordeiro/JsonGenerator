package json.visitors

import json.models.JsonKeyValuePair
import json.models.JsonNumber
import json.models.JsonObject
import json.models.JsonString
import org.junit.Test
import org.junit.jupiter.api.Assertions.*

internal class ObjectFinderVisitorTest {

    @Test
    fun `object with correct property names`() {
        val obj = JsonObject(listOf(
            JsonKeyValuePair("property-one", JsonString("some-text-value", 1)),
            JsonKeyValuePair("property-two", JsonNumber(101, 1)),
            JsonKeyValuePair("property-three", JsonNumber(101, 1)),
        ))

        val visitor = ObjectFinderVisitor(listOf("property-one", "property-two"))
        obj.accept(visitor)

        val result = visitor.getObjects()

        assertTrue(result.isNotEmpty())
        assertEquals(obj, result[0])
    }

    @Test
    fun `object with wrong property names`() {
        val obj = JsonObject(listOf(
            JsonKeyValuePair("property-one", JsonString("some-text-value", 1)),
            JsonKeyValuePair("property-two", JsonNumber(101, 1)),
            JsonKeyValuePair("property-three", JsonNumber(101, 1)),
        ))

        val visitor = ObjectFinderVisitor(listOf("property-one", "property-four"))
        obj.accept(visitor)

        val result = visitor.getObjects()

        assertTrue(result.isEmpty())
    }

}