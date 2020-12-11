package com.tomtresansky.aoc_2020.day_04.passport

typealias Year = Int

typealias SerializedField = String
private fun SerializedField.deserialize(): Passport.Field {
    val components = this.split(':')
    return Passport.Field(Passport.FieldType.forKey(components[0]), components[1])
}

typealias SerializedPassport = List<String>
class Passport(val fields: Map<FieldType, Field>) {
    companion object {
        fun deserialize(data: SerializedPassport): Passport {
            val typedFields = data.joinToString(" ").split(' ').map { it.deserialize() }
            return Passport(typedFields.associateBy { it.type })
        }
    }

    override fun toString() = fields.values.toString()

    data class Field(val type: FieldType, val value: String) {
        fun isValid() = type.isValid(value)
    }

    @Suppress("unused")
    enum class FieldType(val key: String, val desc: String) {
        BIRTH_YEAR("byr", "Birth Year") {
            override fun isValid(data: String) = isValidYear(data, MIN_BIRTH_YEAR, MAX_BIRTH_YEAR)
        },
        ISSUE_YEAR("iyr", "Issue Year") {
            override fun isValid(data: String) = isValidYear(data, MIN_ISSUE_YEAR, MAX_ISSUE_YEAR)
        },
        EXPIRATION("eyr", "Expiration Year") {
            override fun isValid(data: String) = isValidYear(data, MIN_EXPIRATION_YEAR, MAX_EXPIRATION_YEAR)
        },
        HEIGHT("hgt", "Height") {
            override fun isValid(data: String) = isValidHeight(data)
        },
        HAIR_COLOR("hcl","Hair Color") {
            override fun isValid(data: String) = isValidPattern(data, HAIR_COLOR_REGEX)
        },
        EYE_COLOR("ecl", "Eye Color") {
            override fun isValid(data: String) = isValidPattern(data, EYE_COLOR_REGEX)
        },
        PASSPORT_ID("pid", "Passport ID") {
            override fun isValid(data: String) = isValidPattern(data, PASSPORT_ID_REGEX)
        },
        COUNTRY_ID("cid", "Country ID") {
            override fun isValid(data: String) = true
        };

        companion object {
            private const val MIN_BIRTH_YEAR = 1920
            private const val MAX_BIRTH_YEAR = 2002
            private const val MIN_ISSUE_YEAR = 2010
            private const val MAX_ISSUE_YEAR = 2020
            private const val MIN_EXPIRATION_YEAR = 2020
            private const val MAX_EXPIRATION_YEAR = 2030

            private val HEIGHT_REGEX = """(\d+)(\w+)""".toRegex()
            private const val HEIGHT_IDX = 1
            private const val HEIGHT_UNIT = 2
            private const val MIN_HEIGHT_CM = 150
            private const val MAX_HEIGHT_CM = 193
            private const val MIN_HEIGHT_IN = 56
            private const val MAX_HEIGHT_IN = 76

            private val HAIR_COLOR_REGEX = """#[0-9a-f]{6}""".toRegex()
            private val EYE_COLOR_REGEX = """amb|blu|brn|gry|grn|hzl|oth""".toRegex()
            private val PASSPORT_ID_REGEX = """[0-9]{9}""".toRegex()

            fun forKey(spec: String): FieldType {
                for (currType in values()) {
                    if (spec == currType.key) {
                        return currType
                    }
                }

                throw IllegalFieldTypeException(spec)
            }
        }

        abstract fun isValid(data: String): Boolean

        internal fun isValidYear(data: String?, lowerBound: Year, upperBound: Year) =
            try {
                val year = data?.toInt()!!
                (year in lowerBound..upperBound)
            } catch (e: Exception ) {
                false
            }

        internal fun isValidHeight(data: String?) =
            try {
                val matchResult = HEIGHT_REGEX.matchEntire(data!!)
                with (matchResult?.groupValues!!) {
                    val measure = get(HEIGHT_IDX).toInt()
                    when (get(HEIGHT_UNIT)) {
                        "cm" -> (measure in MIN_HEIGHT_CM..MAX_HEIGHT_CM)
                        "in" -> (measure in MIN_HEIGHT_IN..MAX_HEIGHT_IN)
                        else -> false
                    }
                }
            } catch (e: Exception ) {
                false
            }

        internal fun isValidPattern(data: String?, regex: Regex) = try {
                regex.matches(data!!)
            } catch (e: Exception ) {
                false
            }

        class IllegalFieldTypeException(spec: String): RuntimeException("Unexpected value: $spec")
    }
}
