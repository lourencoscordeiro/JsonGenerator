# JSON Generator API

## Introduction

This repository module contains an API built with the goal of generating JSON elements and serializing them. All this 
is done in an agnostic way.

## Installation

### Gradle

The latest stable SDK release can be retrieved from [Maven Central](https://search.maven.org/search?q=g:[todo]) in the
`build.gradle.kts` file:

```gradle
ext {
    versions = [
        json-generator-api: "1.0-SNAPSHOT"
    ]
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("pt.iscte.pa:json-generator-api:${versions.json-generator-api}")
}
```

If your integration with Gradle is not powered by Kotlin, add the dependency to your `build.gradle` file:

```gradle
ext {
    versions = [
        json-generator-api: "1.0-SNAPSHOT"
    ]
}

repositories {
    mavenCentral()
}

dependencies {
    implementation 'pt.iscte.pa:json-generator-api:${versions.json-generator-api}'
}
```

### Java SDK with Maven

```maven
<properties>
    <json-generator-api.version>1.0-SNAPSHOT</json-generator-api.version>
</properties>

<dependencies>
    <dependency>
      <groupId>pt.iscte.pa</groupId>
      <artifactId>json-generator-api</artifactId>
      <version>${json-generator-api.version}</version>
    </dependency>
</dependencies>
```

## Integration

### JSON Models

JSON Generator API is able to convert your standard data classes into JSON Models. Currently, the available JSON Models are:
- JSON Null Node
- JSON String
- JSON Boolean
- JSON Number
- JSON Array
- JSON Object

All these elements are of type JSON Elements and allow adding observers and integrating them with the visitor design 
pattern (these and other features will be discussed more far ahead).

#### JSON Null Node

[JSON Null Node](https://github.com/lourencoscordeiro/JsonGenerator/blob/main/json-generator-api/src/main/kotlin/json/models/JsonNullNode.kt)
represents a null value in a JSON Context. To create a JSON Null Node by hand (for testing purposes, for example), you could:

```kotlin
val jsonNullNode = JsonNullNode()
```

#### JSON String

[JSON String](https://github.com/lourencoscordeiro/JsonGenerator/blob/main/json-generator-api/src/main/kotlin/json/models/JsonString.kt) 
represents a String in a JSON Context. To create a JSON String by hand (for testing purposes, for example), you could:

```kotlin
val jsonString = JsonString("some text")
```

#### JSON Boolean

[JSON Boolean](https://github.com/lourencoscordeiro/JsonGenerator/blob/main/json-generator-api/src/main/kotlin/json/models/JsonBoolean.kt) 
represents a Boolean in a JSON Context. To create a JSON Boolean by hand (for testing purposes, for example), you could:

```kotlin
val jsonBoolean = JsonBoolean(true)
```

#### JSON Number

[JSON Number](https://github.com/lourencoscordeiro/JsonGenerator/blob/main/json-generator-api/src/main/kotlin/json/models/JsonNumber.kt)
represents a Number in a JSON Context, this could be an Integer, Double, Float, and others - check 
[Kotlin documentation](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-number/) for the Number concept.
To create a JSON Boolean by hand (for testing purposes, for example), you could:

```kotlin
val intJsonNumber = JsonNumber(132)
val doubleJsonNumber = JsonNumber(132.32)
val byteJsonNumber = JsonNumber("some text".toByte())
```

#### JSON Array

[JSON Array](https://github.com/lourencoscordeiro/JsonGenerator/blob/main/json-generator-api/src/main/kotlin/json/models/JsonArray.kt)
represents a JSON Array of other types of JSON Elements (without exception). To create a JSON Array by hand 
(for testing purposes, for example), you could:

```kotlin
// creating list of JSON Elements
// considering used variables already existed
val listOfJsonElements: List<JsonElement> = listOf(
    jsonNullNode,
    jsonString,
    JsonBoolean,
    intJsonNumber,
    jsonObject)

val jsonArray = JsonArray(listOfJsonElements)
```

#### JSON Object

[JSON Object](https://github.com/lourencoscordeiro/JsonGenerator/blob/main/json-generator-api/src/main/kotlin/json/models/JsonObject.kt)
represents an object in a JSON Context. A JSON Object is composed by a list of JSON Key Pair Values.
To create a JSON Object by hand
(for testing purposes, for example), you could:

```kotlin
// creating list of JSON Elements
// considering used variables already existed
val listOfJsonElements: List<JsonKeyPairValue> = listOf(
    JsonKeyPairValue("null-node", jsonNullNode),
    JsonKeyPairValue("string", jsonString),
    JsonKeyPairValue("boolean", JsonBoolean),
    JsonKeyPairValue("number", intJsonNumber),
    JsonKeyPairValue("array", jsonObject))

val jsonObject = JsonObject(listOfJsonElements)
```

### JSON Generator

#### Simple

JSON Generator is the core tool of this API. This is the element that allows converting Kotlin objects (of any kind,
including null values) into JSON Models. It could be used for objects, string, number, null nodes, lists, arrays, maps, 
between others.

```kotlin
data class ExampleObject(val name: String, age: Int) { }

val example = ExampleObject("John", 23)
val jsonObject = JsonGenerator().toJsonElement(example)
```

#### With Custom Type Mapping

JSON Generator is decoupled from the element responsible for doing the actual data conversion, Type Mapping. Imagine that you have your
own implementation of JsonString for some reason. This way you can re-implement Type Mapping and use it in JSON Generator.

```kotlin
data class CustomJsonString(val name: String) : JsonString { 
    // details of your implementation
}

class CustomTypeMappingWithDelegation : TypeMapping {
    
    private val delegate: TypeMapping = DefaultTypeMapping()
    
    // delegate methods to default implementation of Type Mapping
    
    // custom implementation of string mapping
    override fun convertString(string: String): JsonString = CustomJsonString(string)
}

val example = ExampleObject("John", 23)
val jsonObject = JsonGenerator(CustomTypeMappingWithDelegation()).toJsonElement(example)
```

### JSON Annotations

This API has some annotations available that can manipulate the serialization of objects.

#### JSON Exclude

This annotation is responsible for excluding an object attribute from the serialized JSON.

```kotlin
data class SimpleTestDataClassWithJsonIgnore(
    val age: Number,
    val name: String,
    @JsonExclude val isInternational: Boolean
)

val example = SimpleTestDataClassWithJsonIgnore(23, "John", true)
val serializedJSON = JsonGenerator().toJsonElement(example).toPrettyJsonString()
```

This will produce:

```json
{
  "age": 23,
  "name": "John"
}
```

#### JSON Property Rename

This annotation is responsible for renaming a property once serializing to JSON.

```kotlin
data class SimpleTestDataClassWithRenamedProperty(
    val age: Number,
    val name: String,
    @RenamedJsonProperty("isForeigner") val isInternational: Boolean
)

val example = SimpleTestDataClassWithRenamedProperty(23, "John", true)
val serializedJSON = JsonGenerator().toJsonElement(example).toPrettyJsonString()
```

This will produce:

```json
{
  "age": 23,
  "name": "John",
  "isForeigner": true
}
```

#### JSON Property As String

This annotation is responsible for converting a property to String.

```kotlin
data class SimpleTestDataClassWithAsJsonString(
    val age: Number,
    val name: String,
    @AsJsonString val isInternational: Boolean
)

val example = SimpleTestDataClassWithAsJsonString(23, "John", true)
val serializedJSON = JsonGenerator().toJsonElement(example).toPrettyJsonString()
```

This will produce:

```json
{
  "age": 23,
  "name": "John",
  "isForeigner": "true"
}
```

### Visitors

JSON Models allow the usage of visitors. Following the Visitor Design Pattern, this is an entrypoint for the API user
to be able to perform actions within the models.

```kotlin
data class CustomVisitor : Visitor {

    var isValid = true

    override fun visit(number: JsonNumber) {
        if (number.value != 18) {
            isValid = false
        }
    }

    fun isDataValid() = isValid

}

val example = ExampleObject("John", 23)
val jsonObject = JsonGenerator(CustomTypeMappingWithDelegation())
jsonObject.accept(CustomVisitor())
```

### Observability

JSON Models respond to observability. This means that if you need something to happen triggered by an operation of 
an JSON Element, it is possible.

```kotlin
data class ExampleObject(val name: String, age: Int) { }

class ExampleObserver : JsonElementObserver {
    
    override fun addedElement(newValue: JsonElement) {
        println("$newValue was added to the list")
    }
    
}

val jsonList = JsonGenerator().toJsonElement(listOf(1,2,3,4))
```

With the code above, when executing

```kotlin
jsonList.addElement(5)
```

The element `5` will be added to the JSON List and `5 was added to the list` will be printed to the console.

