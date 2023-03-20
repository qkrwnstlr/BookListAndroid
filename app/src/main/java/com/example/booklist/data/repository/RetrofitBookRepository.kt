package com.example.booklist.data.repository

import com.example.booklist.data.service.BookService
import com.example.booklist.model.dto.AddBookRequestDTO
import com.example.booklist.model.dto.FindBookRequestDTO
import com.example.booklist.model.entity.BookEntity

object RetrofitBookRepository : BookRepository {
  private val service = RetrofitAPI.retrofitService(BookService::class.java)
  override suspend fun findBook(findBookRequestDTO: FindBookRequestDTO): List<BookEntity> {
    TODO("Not yet implemented")
  }

  override suspend fun addNewBook(addBookRequestDTO: AddBookRequestDTO): BookEntity {
    TODO("Not yet implemented")
  }

  override suspend fun editBook(id: Long): BookEntity {
    TODO("Not yet implemented")
  }
}