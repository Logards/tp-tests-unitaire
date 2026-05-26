package com.example.tptestsunitaires

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.property.Arb
import io.kotest.property.arbitrary.char
import io.kotest.property.arbitrary.int
import io.kotest.property.checkAll

class CesarAlgorithmTests : FunSpec({
    test("Encrypt method should decalate letter") {
        // ACT
        val letter = 'A'
        val number = 2

        //ARRANGE
        val result = CesarAlgorithm().encrypt(letter, number)

        //ASSERT
        result shouldBe 'C'
    }

    test("Encrypt method should throw exception when letter is not uppercase") {
        //ACT
        val letter = 'a'
        val number = 2

        //ARRANGE
        val exception = shouldThrow<IllegalArgumentException> {
            CesarAlgorithm().encrypt(letter, number)
        }

        //ASSERT
        exception.message shouldBe "letter must be uppercase and must be a letter"
    }

    test("Encrypt method should throw exception when letter is not a letter") {
        //ACT
        val letter = '5'
        val number = 2

        //ARRANGE
        val exception = shouldThrow<IllegalArgumentException> {
            CesarAlgorithm().encrypt(letter, number)
        }

        //ASSERT
        exception.message shouldBe "letter must be uppercase and must be a letter"
    }

    test("Encrypt method should decalate letter when key + letter > 26") {
        //ACT
        val letter = 'Z'
        val number = 53

        //ARRANGE
        val result = CesarAlgorithm().encrypt(letter, number)

        //ASSERT
        result shouldBe 'A'
    }

    test("Encrypt method should throw IllegalArgumentException when key is negative") {
        //ACT
        val letter = 'A'
        val number = -1

        //ARRANGE
        val exception = shouldThrow<IllegalArgumentException> {
            CesarAlgorithm().encrypt(letter, number)
        }

        //ASSERT
        exception.message shouldBe "Key must be non-negative"
    }
    
    test("Decrypt method should decrypt the char") {
        checkAll(Arb.char('A'..'Z'), Arb.int(0..Int.MAX_VALUE)) { letter, key ->
            val encryptedLetter = CesarAlgorithm().encrypt(letter, key)
            val decryptedLetter = CesarAlgorithm().decrypt(encryptedLetter, key)

            decryptedLetter shouldBe letter
        }
    }
})