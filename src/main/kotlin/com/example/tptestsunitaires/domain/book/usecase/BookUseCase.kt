package com.example.tptestsunitaires.domain.book.usecase

import com.example.tptestsunitaires.domain.book.model.Book
import com.example.tptestsunitaires.domain.book.port.IBookRepository

class BookUseCase(private val bookRepository: IBookRepository) {

    public fun findAll(): List<Book> {
        val allBooks = bookRepository.findAll()
        return allBooks.sortedBy { it.name }
    }

    public fun addBook(book: Book) {
        bookRepository.addBook(book)
    }
}