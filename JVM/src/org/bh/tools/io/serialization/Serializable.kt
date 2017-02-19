package org.bh.tools.io.serialization

import com.google.gson.Gson

///**
// * Copyright BHStudios ©2016 BH-1-PS. Made for Snek.
// *
// * Transforms itself from a RAM type to a serialized type
// *
// * @author Kyli Rouge
// * @since 2016-11-01
// */
//interface Serializable<FromType, ToType> {
//    fun serialize(): ToType
//    constructor(serialized: ToType)
//}

/**
 * Copyright BHStudios ©2016 BH-1-PS. Made for Snek.
 *
 * Transforms a given object from a RAM object to a serialized one
 *
 * @author Kyli Rouge
 * @since 2016-11-01
 */
interface SerializationFactory<FromType, ToType> {

    /**
     * Transforms the given RAM data into a serialized form
     *
     * @param unserialized the JVM object to serialize
     *
     * @return The serialized form of the given object
     */
    fun serialize(unserialized: FromType): ToType

    /**
     * Deserializes the given string into the RAM type
     *
     * @param serialized The already-serialized data
     * @param unusedTypeSample Don't use this; it's only here to gain type information from the JVM. Or do use it; I
     *                         don't care. It's just not intended to be used and will likely be ignored.
     *
     * @return The deserialized form of the given data
     */
    fun deserialize(serialized: ToType, vararg unusedTypeSample: FromType): FromType
}

/**
 * Copyright BHStudios ©2016 BH-1-PS. Made for Husky-IO.
 *
 * Specifies a factory that serializes to JSON
 *
 * @author Kyli Rouge
 * @since 2016-11-01
 */
class JsonSerializatinFactory<FromType : Any> : SerializationFactory<FromType, String> {

    val gson = Gson()

    override fun serialize(unserialized: FromType): String {
        return gson.toJson(unserialized)
    }

    @Suppress("UNCHECKED_CAST") // manually checked
    override fun deserialize(serialized: String, vararg unusedTypeSample: FromType): FromType {
        return gson.fromJson(serialized, unusedTypeSample::class.java.componentType as Class<FromType>)
    }
}

/**
 * The [Gson] instance shared by this file
 */
private val _sharedGson = Gson()

/**
 * Transforms this RAM object into a JSON string
 *
 * @return The JSON-serialized form of this object
 */
val <FromType> FromType.jsonValue: String get() = _sharedGson.toJson(this)

/**
 * Deserializes this JSON string into the RAM type
 *
 * @param unusedTypeSample Don't use this; it's only here to gain type information from the JVM. Or do use it; I
 *                         don't care. It's just not intended to be used and will likely be ignored.
 *
 * @return The deserialized form of this JSON string
 */
@Suppress("UNCHECKED_CAST")
fun <FromType> String.fromJson(vararg unusedTypeSample: FromType): FromType = _sharedGson.fromJson(this,
        unusedTypeSample
        ::class.java.componentType as Class<FromType>)
