package json.generator.annotations

/**
 * Should be used on object properties that are meant to be renamed on the Json Element.
 */
@Target(AnnotationTarget.PROPERTY)
annotation class RenamedJsonProperty(val name: String)