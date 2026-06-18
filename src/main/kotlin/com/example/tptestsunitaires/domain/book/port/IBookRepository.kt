package com.example.tptestsunitaires.domain.book.port

import com.example.tptestsunitaires.domain.book.model.Book

interface IBookRepository {
    fun findById(id: Long): Book?
    fun findAll(): List<Book>
    fun addBook(book: Book): Book
    fun reserveBook(id: Long)
}