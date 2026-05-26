package com.example.tptestsunitaires.domain.book.model

class Book {
    val author: String
    val name: String

    constructor(author: String, name: String) {
        if (author.trim().isEmpty()) {
            throw IllegalArgumentException("Author cannot be empty")
        }

        if (name.trim().isEmpty()) {
            throw IllegalArgumentException("Name cannot be empty")
        }

        this.author = author
        this.name = name
    }
}