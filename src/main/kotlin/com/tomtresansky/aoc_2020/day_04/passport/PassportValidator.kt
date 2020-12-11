package com.tomtresansky.aoc_2020.day_04.passport

interface IPassportValidator {
    fun isValid(passport: Passport): Boolean
}

open class SimplePassportValidator: IPassportValidator {
    companion object {
        private val REQUIRED_FIELD_TYPES = (Passport.FieldType.values().toSet() - Passport.FieldType.COUNTRY_ID)
    }

    override fun isValid(passport: Passport): Boolean {
        val allFieldTypes = passport.fields.map { it.key }
        return allFieldTypes.containsAll(REQUIRED_FIELD_TYPES)
    }
}

class ThoroughPassportValidator: SimplePassportValidator() {
    override fun isValid(passport: Passport): Boolean {
        val missingRequiredField = !super.isValid(passport)
        val invalidFields = passport.fields.values.filter { !it.isValid() }

        return when {
            missingRequiredField -> {
                println("MISSING REQUIRED FIELD: $passport")
                false
            }
            invalidFields.isNotEmpty() -> {
                println("INVALID FIELD(S) ${invalidFields.map { it.type.desc }.joinToString(", ") }: $passport")
                false
            }
            else -> {
                println("VALID: $passport")
                true
            }
        }
    }
}
