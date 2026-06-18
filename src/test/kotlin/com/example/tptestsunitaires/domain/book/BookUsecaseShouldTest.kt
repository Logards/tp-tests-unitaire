package com.example.tptestsunitaires.domain.book

import com.example.tptestsunitaires.domain.book.Exception.BookAlreadyReservedException
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
    val fakeDb = FakeDbBookTest()
    val bookUsecase = BookUseCase(fakeDb)

    afterEach {
        fakeDb.clear()
    }
    test("add a book to the repository should add a book") {
        val book = Book("author1", "name1", false)

        val addedBook = bookUsecase.addBook(book)

        val allBooks = bookUsecase.findAll()

        allBooks.size shouldBe 1
        allBooks[0] shouldBe addedBook

    }

    test("add 2 book to the repository and return all books should return all books sorted") {
        val book = Book("author1", "name1", false)
        val book2 = Book("author2", "name2", false)

        val addedBook = bookUsecase.addBook(book)
        val addedBook2 = bookUsecase.addBook(book2)

        val allBooks = bookUsecase.findAll()
        allBooks.size shouldBe 2
        allBooks[0] shouldBe addedBook
        allBooks[1] shouldBe addedBook2
    }

    test("add books should return all books sorted by name") {
        val bookArb = arbitrary {
            val title = Arb.string(10..20).bind()
            val author = Arb.string(10..20).bind()
            Book(author, title, false)
        }
        checkAll( Arb.list(bookArb, 5..20) ) { books ->
            fakeDb.clear()

            val addedBooks = books.map { bookUsecase.addBook(it) }

            val allBooks = bookUsecase.findAll()

            allBooks shouldBe addedBooks.sortedBy { it.name }
        }
    }

    test("reserved a book should reserved a book") {
        val book = Book("author1", "name1", false)

        bookUsecase.addBook(book)
        bookUsecase.reserveBook(1)

        val allBooks = bookUsecase.findAll()
        allBooks[0].isReserved shouldBe true
    }

    test("reserved a book already reserved should throw an exception") {
        val book = Book("author1", "name1", true)

        bookUsecase.addBook(book)

        try {
            bookUsecase.reserveBook(1)
        } catch (e: BookAlreadyReservedException) {
            e.message shouldBe "Book is already reserved"
        }
    }
})