package com.example.tptestsunitaires.infrastructure.driving.book.dto

import com.fasterxml.jackson.annotation.JsonAlias
import com.fasterxml.jackson.annotation.JsonProperty

data class BookDTO(
    val id: Long? = null,
    val author: String,
    val name: String,
    @get:JsonProperty("isReserved")
    @param:JsonAlias("reserved")
    val isReserved: Boolean? = null
)