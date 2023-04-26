package json.generator.annotations

/**
 * Should be used on object properties that are meant to be mapped as String whether their type has a corresponding JSON type or not.
 */
// ASK IF IT SHOULD ALSO BE USABLE FOR ENTIRE DATA CLASS
@Target(AnnotationTarget.PROPERTY)
annotation class AsJsonString
