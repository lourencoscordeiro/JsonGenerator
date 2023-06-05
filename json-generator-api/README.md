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
- [JSON Null Node](#JSON Null Node) 
- [JSON String](#JSON String) 
- [JSON Boolean](#JSON Boolean)
- [JSON Number](#JSON Number) 
- [JSON Array](#JSON Array) 
- [JSON Object](#JSON Object)

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
represents a JSON Object.
To create a JSON Array by hand
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