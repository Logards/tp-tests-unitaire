package com.example.tptestsunitaires.infrastructure.driving.book.dto

import com.example.tptestsunitaires.domain.book.model.Book
import com.example.tptestsunitaires.domain.book.port.IBookRepository
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Service

@Service
class BookDAO(private val namedParameterJdbcTemplate: NamedParameterJdbcTemplate) : IBookRepository {
    override fun findAll(): List<Book> {
        return namedParameterJdbcTemplate
        .query("SELECT * FROM books", emptyMap<String, Any>()) { rs, _ ->
            Book(rs.getString("author"), rs.getString("name"))
        }
    }

    override fun addBook(book: Book) {
        val result = namedParameterJdbcTemplate
            .update(
                "INSERT INTO books (author, name) VALUES (:author, :name)",
                mapOf("author" to book.author, "name" to book.name)
            )
        if (result == 0) throw Exception("Error adding book")
        else
            println("Book added successfully")
    }

}