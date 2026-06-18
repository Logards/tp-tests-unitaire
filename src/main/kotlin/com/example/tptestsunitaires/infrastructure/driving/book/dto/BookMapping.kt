package com.example.tptestsunitaires.infrastructure.driving.book.dto

import com.example.tptestsunitaires.domain.book.model.Book

class BookMapping {
    companion object {
        fun toDTO(book: Book): BookDTO {
            return BookDTO(
                id = book.id,
                author = book.author,
                name = book.name,
                isReserved = book.isReserved
            )
        }

        fun toModel(book: BookDTO): Book {
            return Book(
                id = book.id,
                author = book.author,
                name = book.name,
                isReserved = book.isReserved ?: false,
            )
        }
    }
}