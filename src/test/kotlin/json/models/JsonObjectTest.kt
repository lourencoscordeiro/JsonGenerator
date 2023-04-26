package json.models

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class JsonObjectTest {

    @Test
    fun `returns correct values`() {
        val jsonString = JsonString("attr-one", 1)
        val jsonNumber = JsonNumber(12278, 1)
        val jsonBool = JsonBoolean(false, 1)

        val jsonStringAttribute = JsonKeyValuePair("json-string", jsonString)
        val jsonNumberAttribute = JsonKeyValuePair("json-number", jsonNumber)
        val jsonBoolAttribute = JsonKeyValuePair("json-boolean", jsonBool)

        val jsonString1 = JsonString("attr-one", 2)
        val jsonNumber1 = JsonNumber(12278, 2)
        val jsonBool1 = JsonBoolean(false, 2)

        val jsonString2 = JsonString("attr-one", 2)
        val jsonNumber2 = JsonNumber(12278, 2)
        val jsonBool2 = JsonBoolean(false, 2)

        val jsonStringAttribute1 = JsonKeyValuePair("json-string", jsonString2)
        val jsonNumberAttribute1 = JsonKeyValuePair("json-number", jsonNumber2)
        val jsonBoolAttribute1 = JsonKeyValuePair("json-boolean", jsonBool2)

        val innerObj = JsonObject(listOf(jsonStringAttribute1, jsonNumberAttribute1, jsonBoolAttribute1), 1)
        val innerObjAttribute = JsonKeyValuePair("inner-object", innerObj)

        val jsonString3 = JsonString("attr-one", 3)
        val jsonNumber3 = JsonNumber(12278, 3)
        val jsonBool3 = JsonBoolean(false, 3)

        val jsonStringAttribute3 = JsonKeyValuePair("json-string", jsonString3)
        val jsonNumberAttribute3 = JsonKeyValuePair("json-number", jsonNumber3)
        val jsonBoolAttribute3 = JsonKeyValuePair("json-boolean", jsonBool3)

        val innerObj2 = JsonObject(listOf(jsonStringAttribute3, jsonNumberAttribute3, jsonBoolAttribute3), 2)
        val innerObjAttribute2 = JsonKeyValuePair("inner-object", innerObj2)

        val jsonList = JsonList(
            listOf(
                jsonString1,
                jsonNumber1,
                jsonBool1,
                innerObjAttribute2
            ), 1
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
             		false,
             		"inner-object": {
            			"json-string": "attr-one", 
            			"json-number": 12278, 
            			"json-boolean": false
            		}
            	], 
            	"inner-object": {
            		"json-string": "attr-one", 
            		"json-number": 12278, 
            		"json-boolean": false
            	}
            }
        """.trimIndent()

        assertEquals(0, obj.depth)
        assertEquals(attributes, obj.attributes)
        assertEquals(expectedStr, obj.toPrettyJsonString())
    }

}