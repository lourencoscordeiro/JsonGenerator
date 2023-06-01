package json.generator.annotations

/**
 * Should be used on object properties that are not meant to be read when converting to JsonElement.
 */
@Target(AnnotationTarget.PROPERTY)
annotation class JsonExclude