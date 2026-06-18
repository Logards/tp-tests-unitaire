package com.example.tptestsunitaires.infrastructure.book

import com.example.tptestsunitaires.domain.book.model.Book
import com.example.tptestsunitaires.domain.book.usecase.BookUseCase
import com.example.tptestsunitaires.infrastructure.driving.book.controller.BookController
import com.ninjasquad.springmockk.MockkBean
import io.kotest.core.spec.style.FunSpec
import io.kotest.extensions.spring.SpringExtension
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post

@WebMvcTest(controllers = [BookController::class])
@ContextConfiguration(classes = [BookController::class])
class BookControllerShouldTest : FunSpec() {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockkBean
    private lateinit var bookUseCase: BookUseCase

    init {
        extensions(SpringExtension)

        beforeTest {

            val allBooks = listOf(
                Book("Author 1", "Book 1", false),
                Book("Author 2", "Book 2", false)
            )

            every { bookUseCase.findAll() } returns allBooks
            every { bookUseCase.addBook(any()) } answers { firstArg() }
            every { bookUseCase.reserveBook(any()) } just Runs
        }

        test("GetAllBooksShouldReturnListOfBooks") {
            mockMvc.get("/books").andExpect {
                status { isOk() }
                jsonPath("$[0].author") { value("Author 1") }
                jsonPath("$[0].name") { value("Book 1") }
            }.andReturn()
        }

        test("AddBookShouldReturnBook") {
            val bookJson = """{"author": "Author 1", "name": "Book 1"}"""

            mockMvc.post("/books") {
                contentType = MediaType.APPLICATION_JSON
                content = bookJson
            }.andExpect {
                status { isCreated() }
                jsonPath("$.author") { value("Author 1") }
                jsonPath("$.name") { value("Book 1") }
            }.andReturn()
        }

        test("AddBookWithoutAuthorShouldReturnBadRequest") {
            val bookJson = """{"name": "Book 1"}"""
            mockMvc.post("/books") {
                contentType = MediaType.APPLICATION_JSON
                content = bookJson
            }.andExpect {
                status { isBadRequest() }
            }
        }

        test("AddBookWithAnEmptyNameShouldReturnBadRequest") {
            val bookJson = """{"author": "Author 1", "name": ""}"""
            mockMvc.post("/books") {
                contentType = MediaType.APPLICATION_JSON
                content = bookJson
            }.andExpect {
                status { isBadRequest() }
            }
        }

        test("ReserveBookShouldReturnBook") {
            val id = 1
            mockMvc.post("/books/${id}/reserve") {
            }.andExpect {
                status { isOk() }
            }.andReturn()
        }

        test("ReserveBookWithoutIdShouldReturnBadRequest") {
            mockMvc.post("/books//reserve") {
            }.andExpect {
                status { isNotFound() }
            }.andReturn()
        }
    }
}