package com.example.tptestsunitaires.infrastructure.driving.book.dto

import com.example.tptestsunitaires.domain.book.Exception.BookException
import com.example.tptestsunitaires.domain.book.usecase.BookUseCase
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/books")
class BookController(private val bookUseCase: BookUseCase) {

    @ExceptionHandler(BookException::class)
    fun handleBookException(ex: BookException): ResponseEntity<String> {
        return ResponseEntity(ex.message, HttpStatus.BAD_REQUEST)
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
        bookUseCase.addBook(BookMapping.toModel(bookDTO))
        return bookDTO
    }
}