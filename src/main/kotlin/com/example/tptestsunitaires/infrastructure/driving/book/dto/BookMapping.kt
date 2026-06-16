package com.example.tptestsunitaires.infrastructure.driving.book.dto

import com.example.tptestsunitaires.domain.book.model.Book

class BookMapping {
    companion object {
        fun toDTO(book: Book): BookDTO {
            return BookDTO(
                author = book.author,
                name = book.name
            )
        }

        fun toModel(book: BookDTO): Book {
            return Book(
                author = book.author,
                name = book.name
            )
        }
    }
}