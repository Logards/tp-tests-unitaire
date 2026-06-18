package com.example.tptestsunitaires.domain.book

import com.example.tptestsunitaires.domain.book.model.Book
import com.example.tptestsunitaires.domain.book.port.IBookRepository

class FakeDbBookTest : IBookRepository {
    private val db = mutableListOf<Book>()

    override fun findById(id: Long): Book? {
        return db.firstOrNull { it.id == id }
    }

    override fun findAll(): List<Book> {
        return db
    }

    override fun addBook(book: Book): Book {
        val newId = (db.maxOfOrNull { it.id ?: 0 } ?: 0) + 1
        val bookWithId = Book(
            id = newId,
            author = book.author,
            name = book.name,
            isReserved = book.isReserved
        )
        db.add(bookWithId)
        return bookWithId
    }

    override fun reserveBook(id: Long) {
        val index = db.indexOfFirst { it.id == id }
        if (index >= 0) {
            val current = db[index]
            db[index] = Book(
                author = current.author,
                name = current.name,
                isReserved = true,
                id = current.id
            )
        }
    }

    fun clear() {
        db.clear()
    }
}