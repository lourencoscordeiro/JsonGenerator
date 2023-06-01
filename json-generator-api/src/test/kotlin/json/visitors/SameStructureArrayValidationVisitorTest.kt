package json.visitors

import json.models.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class SameStructureArrayValidationVisitorTest {

    @Test
    fun `different structure array - example 1`() {
        val differentStructureArrayExample1 = JsonArray(listOf(
            JsonObject(listOf(
                JsonKeyValuePair("some-text", JsonString("some-text-value", 2)),
                JsonKeyValuePair("some-int", JsonNumber(101, 2))
            ), 1),
            JsonObject(listOf(
                JsonKeyValuePair("some-text", JsonString("some-text-value", 2)),
                JsonKeyValuePair("some-bool", JsonBoolean(false, 2))
            ), 1)
        ))

        val visitor = SameStructureArrayValidationVisitor()
        differentStructureArrayExample1.accept(visitor)

        assertFalse(visitor.isValid())
    }

    @Test
    fun `same structure array - example 1`() {
        val sameStructureArrayExample1 = JsonArray(listOf(
            JsonObject(listOf(
                JsonKeyValuePair("some-text", JsonString("some-text-value", 2)),
                JsonKeyValuePair("some-int", JsonNumber(101, 2))
            ), 1),
            JsonObject(listOf(
                JsonKeyValuePair("some-text", JsonString("some-text-value-2", 2)),
                JsonKeyValuePair("some-int", JsonNumber(200, 2))
            ), 1)
        ))

        val visitor = SameStructureArrayValidationVisitor()
        sameStructureArrayExample1.accept(visitor)

        assertTrue(visitor.isValid())
    }

    @Test
    fun `different structure array - example 2`() {
        val differentStructureArrayExample2 = JsonArray(listOf(
            JsonNumber(1, 1),
            JsonString("1", 1),
            JsonNumber(1, 1),
        ))

        val visitor = SameStructureArrayValidationVisitor()
        differentStructureArrayExample2.accept(visitor)

        assertFalse(visitor.isValid())
    }

    @Test
    fun `same structure array - example 2`() {
        val sameStructureArrayExample2 = JsonArray(listOf(
            JsonNumber(1, 1),
            JsonNumber(2, 1),
            JsonNumber(3, 1),
        ))

        val visitor = SameStructureArrayValidationVisitor()
        sameStructureArrayExample2.accept(visitor)

        assertTrue(visitor.isValid())
    }


}