package com.example.tptestsunitaires.features

import io.cucumber.java.en.When
import io.cucumber.java.en.Then
import io.restassured.RestAssured
import io.restassured.http.ContentType

class BookStepsReservation()  {

    @When("the user reserves the book with id 1")
    fun reserveBook() {
        RestAssured.given()
            .contentType(ContentType.JSON)
            .`when`()
            .post("/books/1/reserve")
            .then()
            .statusCode(200)
    }
    @Then("the book with id 1 should be reserved")
    fun checkBookReserved() {
        RestAssured.given()
            .contentType(ContentType.JSON)
            .`when`()
            .get("/books")
            .then()
            .statusCode(200)
            .body("find { it.id == 1 }.isReserved", org.hamcrest.Matchers.equalTo(true))
    }
}