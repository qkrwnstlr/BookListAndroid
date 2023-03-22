package com.example.booklist.data.service

import com.example.booklist.model.dto.AddBookRequestDTO
import com.example.booklist.model.dto.EditBookRequestDTO
import com.example.booklist.model.dto.FindBookResponseDTO
import com.example.booklist.model.entity.BookEntity
import retrofit2.Response
import retrofit2.http.*
import java.util.*

interface BookService {
  @JvmSuppressWildcards
  @GET("book")
  suspend fun getBookList(@QueryMap param: Map<String, Any>): Response<List<FindBookResponseDTO>>

  @GET("book/{id}")
  suspend fun getBook(@Path(value = "id") id: Long): Response<BookEntity>

  @POST("book/add")
  suspend fun addBook(@Body body: AddBookRequestDTO): Response<FindBookResponseDTO>

  @POST("book/delete/{id}")
  suspend fun deleteBook(@Path(value = "id") id: Long)

  @POST("book/edit/{id}")
  suspend fun editBook(@Path(value = "id") id: Long, @Body body: EditBookRequestDTO): Response<FindBookResponseDTO>
}