package com.example.tptestsunitaires

class CesarAlgorithm : ICesarAlgorithm {
    override fun encrypt(letter: Char, key: Int): Char {
        var correctKey = key
        if (key < 0)
            throw IllegalArgumentException("Key must be non-negative")
        if (key > 26)
            correctKey = key % 26
        if (letter !in 'A'..'Z')
            throw IllegalArgumentException("letter must be uppercase and must be a letter")
        val resultAsciiNumber = letter.code + correctKey
        if (resultAsciiNumber > 90)
            return ((resultAsciiNumber - 26)).toChar()
        if (resultAsciiNumber < 65)
            return ((resultAsciiNumber + 26)).toChar()
        return (resultAsciiNumber).toChar()
    }

    override fun decrypt(letter: Char, key: Int): Char {
        var correctKey = key
        if (key < 0)
            throw IllegalArgumentException("Key must be non-negative")
        if (key > 26)
            correctKey = key % 26
        if (letter !in 'A'..'Z')
            throw IllegalArgumentException("letter must be uppercase and must be a letter")
        val resultAsciiNumber = letter.code - correctKey
        if (resultAsciiNumber > 90)
            return ((resultAsciiNumber - 26)).toChar()
        if (resultAsciiNumber < 65)
            return ((resultAsciiNumber + 26)).toChar()
        return (resultAsciiNumber).toChar()
    }
}