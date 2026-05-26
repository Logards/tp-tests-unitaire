package com.example.tptestsunitaires.domain.book

import com.example.tptestsunitaires.domain.book.model.Book
import com.example.tptestsunitaires.domain.book.usecase.BookUseCase
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.property.Arb
import io.kotest.property.arbitrary.arbitrary
import io.kotest.property.arbitrary.list
import io.kotest.property.arbitrary.string
import io.kotest.property.checkAll

class BookUsecaseShouldTest: FunSpec({
    test("add a book to the repository should add a book") {
        val book = Book("author1", "name1")
        val fakeDb = FakeDbBookTest()
        val bookUsecase = BookUseCase(fakeDb)

        bookUsecase.addBook(book)

        val allBooks = bookUsecase.findAll()

        allBooks.size shouldBe 1
        allBooks[0] shouldBe book
    }

    test("add 2 book to the repository and return all books should return all books sorted") {
        val book = Book("author1", "name1")
        val book2 = Book("author2", "name2")
        val fakeDb = FakeDbBookTest()
        val bookUseCase = BookUseCase(fakeDb)

        bookUseCase.addBook(book)
        bookUseCase.addBook(book2)

        val allBooks = bookUseCase.findAll()
        allBooks.size shouldBe 2
        allBooks[0] shouldBe book
        allBooks[1] shouldBe book2
    }

    test("add books should return all books sorted by name") {
        val bookArb = arbitrary {
            val title = Arb.string(10..20).bind()
            val author = Arb.string(10..20).bind()
            Book(author, title)
        }
        checkAll( Arb.list(bookArb, 5..20) ) { books ->
            val fakeDb = FakeDbBookTest()
            val bookUseCase = BookUseCase(fakeDb)

            books.forEach { bookUseCase.addBook(it) }

            val allBooks = bookUseCase.findAll()

            allBooks shouldBe books.sortedBy { it.name }
        }
    }
})