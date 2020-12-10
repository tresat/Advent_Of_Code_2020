package com.tomtresansky.aoc_2020.day_02.policy

enum class PasswordPolicyType {
    PREVIOUS {
        override fun newPolicy(valOne: Int, valTwo: Int, keyChar: Char) = PreviousPasswordPolicy(valOne, valTwo, keyChar)
    },
    OFFICIAL {
        override fun newPolicy(valOne: Int, valTwo: Int, keyChar: Char) = OfficialPasswordPolicy(valOne, valTwo, keyChar)
    };

    abstract fun newPolicy(valOne: Int, valTwo: Int, keyChar: Char): IPasswordPolicy
}

interface IPasswordPolicy {
    fun isValid(password: String): Boolean
}

class PreviousPasswordPolicy(private val minOccurs: Int, private val maxOccurs: Int, private val key: Char): IPasswordPolicy {
    override fun isValid(password: String): Boolean {
        val keyCount = password.toCharArray().count { it == key }
        return (keyCount in minOccurs..maxOccurs)
    }
}

class OfficialPasswordPolicy(private val firstIdx: Int, private val secondIdx: Int, private val key: Char): IPasswordPolicy {
    override fun isValid(password: String): Boolean {
        val firstIdxMatches = (password.toCharArray()[firstIdx - 1] == key)
        val secondIdxMatches = (password.toCharArray()[secondIdx - 1] == key)
        return (firstIdxMatches xor secondIdxMatches)
    }
}
