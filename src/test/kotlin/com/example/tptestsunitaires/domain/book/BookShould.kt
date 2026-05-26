package com.example.tptestsunitaires.domain.book

import com.example.tptestsunitaires.domain.book.model.Book
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class BookShould: FunSpec( {
    test("create a book with an empty string should return an exception") {
        val exception = shouldThrow<IllegalArgumentException> {
            val book = Book("", "book")
        }

        exception.message shouldBe "Author cannot be empty"
    }

    test("create a book with an empty name should return an exception") {
        val exception = shouldThrow<IllegalArgumentException> {
            val book = Book("author", "")
        }

        exception.message shouldBe "Name cannot be empty"
    }

    test("create a book should create a book") {
        val book = Book("author", "name")

        book.author shouldBe "author"
        book.name shouldBe "name"
    }
})