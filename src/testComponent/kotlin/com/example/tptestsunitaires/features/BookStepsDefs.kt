package com.example.tptestsunitaires.features

import com.example.tptestsunitaires.infrastructure.driving.book.dto.BookDTO
import io.cucumber.datatable.DataTable
import io.cucumber.java.Before
import io.cucumber.java.Scenario
import io.cucumber.java.en.And
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import io.kotest.matchers.collections.shouldContain
import io.restassured.RestAssured
import io.restassured.http.ContentType
import org.springframework.boot.test.web.server.LocalServerPort

class BookStepsDefs() {

    @LocalServerPort
    private var port: Int = 0

    private lateinit var allDatas: List<BookDTO>

    @Before
    fun setup(scenario: Scenario) {
        RestAssured.baseURI = "http://localhost:$port"
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails()
    }

    @Given("the user creates the first book {string} with author {string}")
    fun createFirstBook(name: String, author: String) {
        val bookDTO = BookDTO(name = name, author = author)
        RestAssured.given()
            .contentType(ContentType.JSON)
            .and()
            .body(
                """
                    {
                      "author": "${bookDTO.author}",
                      "name": "${bookDTO.name}"
                    }
                """.trimIndent()
            )
            .`when`()
            .post("/books")
            .then()
            .statusCode(201)
    }

    @And("the user creates the second book {string} with author {string}")
    fun createSecondBook(name: String, author: String) {
        val bookDTO = BookDTO(name = name, author = author)
        RestAssured.given()
            .contentType(ContentType.JSON)
            .and()
            .body(
                """
                    {
                      "author": "${bookDTO.author}",
                      "name": "${bookDTO.name}"
                    }
                """.trimIndent()
            )
            .`when`()
            .post("/books")
            .then()
            .statusCode(201)
    }

    @When("the user get all books")
    fun getAllBooks() {
        allDatas = RestAssured.given()
            .contentType(ContentType.JSON)
            .`when`()
            .get("/books")
            .then()
            .statusCode(200)
            .extract()
            .jsonPath()
            .getList(".", BookDTO::class.java)
    }

    @Then("the books lists should contains")
    fun booksListsShouldContains(table: DataTable) {
        val expectedBooks = table.asList().map { row ->
            val parts = row.split(",")
            val name = parts[0].trim()
            val author = parts[1].trim().removeSurrounding("\"")
            BookDTO(name = name, author = author)
        }

        val actualBooks = allDatas.map { BookDTO(name = it.name, author = it.author) }

        expectedBooks.forEach { expected ->
            actualBooks shouldContain expected
        }
    }
}
