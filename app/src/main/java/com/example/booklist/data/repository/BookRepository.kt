package com.example.booklist.data.repository

import com.example.booklist.model.dto.AddBookRequestDTO
import com.example.booklist.model.dto.FindBookRequestDTO
import com.example.booklist.model.entity.BookEntity

interface BookRepository {
  suspend fun findBook(findBookRequestDTO: FindBookRequestDTO): List<BookEntity>
  suspend fun addNewBook(addBookRequestDTO: AddBookRequestDTO): BookEntity
  suspend fun editBook(id: Long): BookEntity
}