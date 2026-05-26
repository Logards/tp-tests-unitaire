package com.example.tptestsunitaires

interface ICesarAlgorithm {
    fun encrypt(letter: Char, key: Int): Char

    fun decrypt(letter: Char, key: Int): Char
}