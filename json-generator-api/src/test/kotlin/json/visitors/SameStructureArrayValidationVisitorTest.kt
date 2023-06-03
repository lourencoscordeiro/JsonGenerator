package json.visitors

import json.models.*
import org.junit.jupiter.api.Assertions.*
import org.junit.Test

internal class SameStructureArrayValidationVisitorTest {

    @Test
    fun `different structure array - example 2`() {
        val differentStructureArrayExample2 = JsonArray(listOf(
            JsonNumber(1),
            JsonString("1"),
            JsonNumber(1),
        ))

        val visitor = SameStructureArrayValidationVisitor()
        differentStructureArrayExample2.accept(visitor)

        assertFalse(visitor.isValid())
    }

    @Test
    fun `same structure array - example 2`() {
        val sameStructureArrayExample2 = JsonArray(listOf(
            JsonNumber(1),
            JsonNumber(2),
            JsonNumber(3),
        ))

        val visitor = SameStructureArrayValidationVisitor()
        sameStructureArrayExample2.accept(visitor)

        assertTrue(visitor.isValid())
    }


}