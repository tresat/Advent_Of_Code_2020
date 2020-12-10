package com.tomtresansky.aoc_2020.day_04.passport

typealias SerializedField = String
private fun SerializedField.deserialize(): Passport.Field {
    val components = this.split(':')
    return Passport.Field(Passport.FieldType.forKey(components[0]), components[1])
}

typealias SerializedPassport = List<SerializedField>
class Passport(val fields: List<Field>) {
    companion object {
        private val REQUIRED_FIELD_TYPES = (FieldType.values().toSet() - FieldType.COUNTRY_ID)

        fun deserialize(data: SerializedPassport) = Passport(data.map { it.deserialize() })
    }

    fun isValid():Boolean {
        val allFieldTypes = fields.map { it.type }
        return allFieldTypes.containsAll(REQUIRED_FIELD_TYPES)
    }

    data class Field(val type: FieldType, val value: String)

    enum class FieldType(val key: String, val desc: String) {
        BIRTH_YEAR("byr", "Birth Year"),
        ISSUE_YEAR("iyr", "Issue Year"),
        EXPIRATION("eyr", "Expiration Year"),
        HEIGHT("hgt", "Height"),
        HAIR_COLOR("hcl","Hair Color"),
        EYE_COLOR("ecl", "Eye Color"),
        PASSPORT_ID("pid", "Passport ID"),
        COUNTRY_ID("cid", "Country ID");

        companion object {
            fun forKey(spec: String): FieldType {
                for (currType in values()) {
                    if (spec == currType.key) {
                        return currType
                    }
                }

                throw IllegalFieldTypeException(spec)
            }
        }

        class IllegalFieldTypeException(spec: String): RuntimeException("Unexpected value: $spec")
    }
}