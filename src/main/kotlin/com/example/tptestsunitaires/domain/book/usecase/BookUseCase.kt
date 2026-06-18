package com.example.tptestsunitaires.domain.book.usecase

import com.example.tptestsunitaires.domain.book.Exception.BookAlreadyReservedException
import com.example.tptestsunitaires.domain.book.Exception.BookException
import com.example.tptestsunitaires.domain.book.model.Book
import com.example.tptestsunitaires.domain.book.port.IBookRepository

class BookUseCase(private val bookRepository: IBookRepository) {

    public fun findAll(): List<Book> {
        val allBooks = bookRepository.findAll()
        return allBooks.sortedBy { it.name }
    }

    public fun addBook(book: Book) : Book {
        val bookAdded = bookRepository.addBook(book)
        return bookAdded
    }

    public fun reserveBook(id: Long) {
        val bookIsReserved = bookRepository.findById(id)?.isReserved
        if (bookIsReserved == true) {
            throw BookAlreadyReservedException("Book is already reserved")
        }
        bookRepository.reserveBook(id)
    }
}