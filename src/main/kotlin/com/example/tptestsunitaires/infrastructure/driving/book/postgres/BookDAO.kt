package com.example.tptestsunitaires.infrastructure.driving.book.postgres

import com.example.tptestsunitaires.domain.book.model.Book
import com.example.tptestsunitaires.domain.book.port.IBookRepository
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.jdbc.support.GeneratedKeyHolder
import org.springframework.stereotype.Service

@Service
class BookDAO(private val namedParameterJdbcTemplate: NamedParameterJdbcTemplate) : IBookRepository {
    override fun findById(id: Long): Book? {
        val sql = "SELECT * FROM books WHERE id = :id"
        val params = mapOf("id" to id)
        return namedParameterJdbcTemplate.query(sql, params) { rs, _ ->
            Book(
                author = rs.getString("author"),
                name = rs.getString("name"),
                isReserved = rs.getBoolean("isReserved"),
                id = rs.getLong("id")
            )
        }.firstOrNull()
    }

    override fun findAll(): List<Book> {
        return namedParameterJdbcTemplate
        .query("SELECT * FROM books", emptyMap<String, Any>()) { rs, _ ->
            Book(
                author = rs.getString("author"),
                name = rs.getString("name"),
                isReserved = rs.getBoolean("isReserved"),
                id = rs.getLong("id")
            )
        }
    }

    override fun addBook(book: Book): Book {
        val keyHolder = GeneratedKeyHolder()
        val params = MapSqlParameterSource(
            mapOf(
                "author" to book.author,
                "name" to book.name,
                "isReserved" to book.isReserved
            )
        )
        val result = namedParameterJdbcTemplate.update(
            "INSERT INTO books (author, name, \"isReserved\") VALUES (:author, :name, :isReserved)",
            params,
            keyHolder,
            arrayOf("id")
        )
        if (result == 0) throw Exception("Error adding book")
        val generatedId = keyHolder.keys?.get("id") as? Number
        return Book(
            author = book.author,
            name = book.name,
            isReserved = book.isReserved,
            id = generatedId?.toLong()
        )
    }

    override fun reserveBook(id: Long) {
        namedParameterJdbcTemplate
            .update(
                "UPDATE books SET \"isReserved\" = true WHERE id = :id",
                mapOf("id" to id)
            )
    }
}