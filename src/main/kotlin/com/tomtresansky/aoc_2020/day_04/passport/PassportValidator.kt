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
        val hasInvalidFields = passport.fields.values.any { !it.isValid() }
        return !(missingRequiredField || hasInvalidFields)
    }
}
