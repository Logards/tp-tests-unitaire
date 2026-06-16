package com.example.tptestsunitaires.infrastructure.application

import com.example.tptestsunitaires.domain.book.port.IBookRepository
import com.example.tptestsunitaires.domain.book.usecase.BookUseCase
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class UseCaseConfiguration() {
    @Bean
    public fun bookUseCase(bookRepository: IBookRepository) : BookUseCase {
        return BookUseCase(bookRepository);
    }
}