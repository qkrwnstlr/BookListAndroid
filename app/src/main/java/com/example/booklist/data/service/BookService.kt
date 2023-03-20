package com.example.booklist.data.service

import com.example.booklist.model.entity.BookEntity
import retrofit2.Response
import retrofit2.http.*
import java.util.*

interface BookService {
  @FormUrlEncoded
  @GET("book")
  suspend fun getBookList(@FieldMap param: Map<String, Objects>): Response<List<BookEntity>>
}