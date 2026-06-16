package com.example.tptestsunitaires.domain.book.model

import com.example.tptestsunitaires.domain.book.Exception.BookException

class Book {
    val author: String
    val name: String

    constructor(author: String, name: String) {
        if (author.trim().isEmpty()) {
            throw BookException("Author cannot be empty")
        }

        if (name.trim().isEmpty()) {
            throw BookException("Name cannot be empty")
        }

        this.author = author
        this.name = name
    }
}