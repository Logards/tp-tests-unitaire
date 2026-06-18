package com.example.tptestsunitaires.infrastructure.driving.book.controller

import com.example.tptestsunitaires.domain.book.Exception.BookAlreadyReservedException
import com.example.tptestsunitaires.domain.book.Exception.BookException
import com.example.tptestsunitaires.domain.book.usecase.BookUseCase
import com.example.tptestsunitaires.infrastructure.driving.book.dto.BookDTO
import com.example.tptestsunitaires.infrastructure.driving.book.dto.BookMapping
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/books")
class BookController(private val bookUseCase: BookUseCase) {

    @ExceptionHandler(BookException::class)
    fun handleBookException(ex: BookException): ResponseEntity<String> {
        return ResponseEntity(ex.message, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(BookAlreadyReservedException::class)
    fun handleBookAlreadyReservedException(ex: BookAlreadyReservedException): ResponseEntity<String> {
        return ResponseEntity(ex.message, HttpStatus.CONFLICT)
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun getBooks(): List<BookDTO> {
        return bookUseCase.findAll()
            .map { BookMapping.toDTO(it) }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createBook(
        @RequestBody bookDTO: BookDTO
    ): BookDTO {
        val createdBook = bookUseCase.addBook(BookMapping.toModel(bookDTO))
        return BookMapping.toDTO(createdBook)
    }

    @PostMapping("/{id}/reserve")
    @ResponseStatus(HttpStatus.OK)
    fun reserveBook(
        @PathVariable id: Long
    ): ResponseEntity<String> {
        bookUseCase.reserveBook(id)
        return ResponseEntity.ok("Book reserved successfully")
    }
}