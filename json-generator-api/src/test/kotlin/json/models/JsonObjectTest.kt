package json.models

import org.junit.jupiter.api.Assertions.*
import org.junit.Test

internal class JsonObjectTest {

    @Test
    fun `returns correct values`() {
        val jsonString = JsonString("attr-one")
        val jsonNumber = JsonNumber(12278)
        val jsonBool = JsonBoolean(false)

        val jsonStringAttribute = JsonKeyValuePair("json-string", jsonString)
        val jsonNumberAttribute = JsonKeyValuePair("json-number", jsonNumber)
        val jsonBoolAttribute = JsonKeyValuePair("json-boolean", jsonBool)

        val jsonString1 = JsonString("attr-one")
        val jsonNumber1 = JsonNumber(12278)
        val jsonBool1 = JsonBoolean(false)

        val jsonString2 = JsonString("attr-one")
        val jsonNumber2 = JsonNumber(12278)
        val jsonBool2 = JsonBoolean(false)

        val jsonStringAttribute1 = JsonKeyValuePair("json-string", jsonString2)
        val jsonNumberAttribute1 = JsonKeyValuePair("json-number", jsonNumber2)
        val jsonBoolAttribute1 = JsonKeyValuePair("json-boolean", jsonBool2)

        val innerObj = JsonObject(listOf(jsonStringAttribute1, jsonNumberAttribute1, jsonBoolAttribute1))
        val innerObjAttribute = JsonKeyValuePair("inner-object", innerObj)

        val jsonList = JsonArray(
            listOf(
                jsonString1,
                jsonNumber1,
                jsonBool1,
            )
        )
        val jsonListAttribute = JsonKeyValuePair("json-list", jsonList)

        val attributes = listOf(
            jsonStringAttribute,
            jsonNumberAttribute,
            jsonBoolAttribute,
            jsonListAttribute,
            innerObjAttribute)

        val obj = JsonObject(attributes)

        val expectedStr = """
            {
            	"json-string": "attr-one", 
            	"json-number": 12278, 
            	"json-boolean": false, 
            	"json-list": [
            		"attr-one",
             		12278,
             		false
            	], 
            	"inner-object": {
            		"json-string": "attr-one", 
            		"json-number": 12278, 
            		"json-boolean": false
            	}
            }
        """.trimIndent()

        assertEquals(attributes, obj.attributes)
        assertEquals(expectedStr, obj.toPrettyJsonString(0))
    }

}