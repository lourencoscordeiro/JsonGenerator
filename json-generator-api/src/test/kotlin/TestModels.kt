import json.generator.annotations.AsJsonString
import json.generator.annotations.JsonExclude
import json.generator.annotations.RenamedJsonProperty

enum class TestEnum {
    PA
}

data class SimpleTestDataClass(
    val number: Number,
    val name: String,
    val isInternational: Boolean
)

data class SimpleTestDataClassWithRenamedProperty(
    val number: Number,
    val name: String,
    @RenamedJsonProperty("isForeigner") val isInternational: Boolean
)

data class SimpleTestDataClassWithAsJsonString(
    val number: Number,
    val name: String,
    @AsJsonString val isInternational: Boolean
)

data class SimpleTestDataClassWithJsonIgnore(
    val number: Number,
    val name: String,
    @JsonExclude val isInternational: Boolean
)

data class ComplexTestDataClass(
    val subject: TestEnum,
    val etcs: Number,
    val examDate: String?,
    val students: List<SimpleTestDataClass>
)