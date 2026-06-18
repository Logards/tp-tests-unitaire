package com.example.tptestsunitaires.infrastructure.book

import com.example.tptestsunitaires.infrastructure.driving.book.postgres.BookDAO
import io.kotest.core.spec.style.FunSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.shouldBe
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional
import org.testcontainers.containers.PostgreSQLContainer

@SpringBootTest
@ActiveProfiles("testIntegration")
@Transactional
class BookDAOShouldTest(val bookDAO: BookDAO, val namedParameterJdbcTemplate: NamedParameterJdbcTemplate) : FunSpec(){

    init {
        extensions(SpringExtension)
        beforeTest {
            container.start()
        }
        beforeEach {
            namedParameterJdbcTemplate.update("DELETE FROM books", emptyMap<String, Any>())
        }

        test("get all books should return all books") {
            namedParameterJdbcTemplate.update(
                "INSERT INTO books (author, name) VALUES (:author, :name)",
                mapOf("author" to "Author 1", "name" to "Book 1")
            )
            namedParameterJdbcTemplate.update(
                "INSERT INTO books (author, name) VALUES (:author, :name)",
                mapOf("author" to "Author 2", "name" to "Book 2")
            )
            val allBooks = bookDAO.findAll()
            allBooks.size shouldBe 2
        }

        test("add book should add a book to the database") {
            namedParameterJdbcTemplate.update(
                "INSERT INTO books (author, name) VALUES (:author, :name)",
                mapOf("author" to "Author 3", "name" to "Book 3")
            )
            val allBooks = bookDAO.findAll()
            allBooks.size shouldBe 1
            allBooks[0].author shouldBe "Author 3"
            allBooks[0].name shouldBe "Book 3"
        }

        test("reserved a book should set isReserved true") {
            namedParameterJdbcTemplate.update(
                "INSERT INTO books (author, name) VALUES (:author, :name)",
                mapOf("author" to "Author 4", "name" to "Book 4")
            )
            val insertedBook = bookDAO.findAll()[0]
            bookDAO.reserveBook(insertedBook.id!!)
            val book = bookDAO.findAll()[0]
            book.isReserved shouldBe true
        }

        afterSpec {
            container.stop()
        }
    }
    companion object {
        private val container = PostgreSQLContainer<Nothing>("postgres:17-alpine")
        init {
            container.start()
            System.setProperty("spring.datasource.url", container.jdbcUrl)
            System.setProperty("spring.datasource.username", container.username)
            System.setProperty("spring.datasource.password", container.password)
        }

    }
}