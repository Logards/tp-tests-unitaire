package com.example.tptestsunitaires.domain.book.model

import com.example.tptestsunitaires.domain.book.Exception.BookException

class Book(
    val author: String,
    val name: String,
    val isReserved: Boolean?,
    val id: Long? = null
) {
    init {
        if (author.trim().isEmpty()) {
            throw BookException("Author cannot be empty")
        }

        if (name.trim().isEmpty()) {
            throw BookException("Name cannot be empty")
        }

    }
}