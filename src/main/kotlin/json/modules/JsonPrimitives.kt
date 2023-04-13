package json.modules

import json.generator.JsonGenerator

data class JsonNumber(private val value: Number): JsonElement{
    //void accept()
}
data class JsonBool(private val value:Boolean):JsonElement{
    //void accept()
}
class JsonNull:JsonElement{
    //void accept()
}