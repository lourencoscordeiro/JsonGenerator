package json.visitors

import json.models.JsonKeyValuePair
import json.models.JsonNumber
import json.models.JsonObject
import json.models.JsonString
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class ValueFinderVisitorTest {

    @Test
    fun `object with correct property name`() {
        val obj = JsonObject(listOf(
            JsonKeyValuePair("property-one", JsonString("some-text-value", 1)),
            JsonKeyValuePair("property-two", JsonNumber(101, 1))))

        val visitor = ValueFinderVisitor("property-one")
        obj.accept(visitor)

        val result = visitor.getValues()

        assertTrue(result.isNotEmpty())
        assertEquals(JsonString("some-text-value", 1), result[0])
    }

    @Test
    fun `object with wrong property name`() {
        val obj = JsonObject(listOf(
            JsonKeyValuePair("property-one-wrong", JsonString("some-text-value", 1)),
            JsonKeyValuePair("property-two", JsonNumber(101, 1))))

        val visitor = ValueFinderVisitor("property-one")
        obj.accept(visitor)

        val result = visitor.getValues()

        assertTrue(result.isEmpty())
    }

}