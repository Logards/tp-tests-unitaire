package com.example.tptestsunitaires.domain.book.port

import com.example.tptestsunitaires.domain.book.model.Book

interface IBookRepository {
    fun findAll(): List<Book>
    fun addBook(book: Book)
}