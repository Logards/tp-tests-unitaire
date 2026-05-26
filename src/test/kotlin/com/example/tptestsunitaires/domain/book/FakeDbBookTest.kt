package com.example.tptestsunitaires.domain.book

import com.example.tptestsunitaires.domain.book.model.Book
import com.example.tptestsunitaires.domain.book.port.IBookRepository

class FakeDbBookTest : IBookRepository{
    private val db = mutableListOf<Book>()

    override fun findAll(): List<Book> {
        return db
    }

    override fun addBook(book: Book) {
        db.add(book)
    }
}