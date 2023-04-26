package json.visitors

import json.models.JsonKeyValuePair
import json.models.JsonNumber
import json.models.JsonObject
import json.models.JsonString
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class IntegerPropertyValidationVisitorTest {

    @Test
    fun `json object returns true`() {
        val obj = JsonObject(listOf(
            JsonKeyValuePair("some-text", JsonString("some-text-value", 1)),
            JsonKeyValuePair("some-int", JsonNumber(101, 1))
        ))

        val visitor = IntegerPropertyValidationVisitor("some-int")
        obj.accept(visitor)

        assertTrue(visitor.isValid())
    }

    @Test
    fun `json object returns false`() {
        val obj = JsonObject(listOf(
            JsonKeyValuePair("some-text", JsonString("some-text-value", 1)),
            JsonKeyValuePair("some-int", JsonNumber(101L, 1))
        ))

        val visitor = IntegerPropertyValidationVisitor("some-int")
        obj.accept(visitor)

        assertFalse(visitor.isValid())
    }

    @Test
    fun `json object returns true - no element with given property`() {
        val obj = JsonObject(listOf(
            JsonKeyValuePair("some-text", JsonString("some-text-value", 1)),
            JsonKeyValuePair("some-other-int", JsonNumber(101, 1))
        ))

        val visitor = IntegerPropertyValidationVisitor("some-int")
        obj.accept(visitor)

        assertTrue(visitor.isValid())
    }

}