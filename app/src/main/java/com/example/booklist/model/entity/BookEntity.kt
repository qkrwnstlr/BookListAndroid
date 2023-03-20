package com.example.booklist.model.entity

import com.example.booklist.model.type.Country
import com.example.booklist.model.type.Genre

data class BookEntity(
  val id: Long,
  val title: String,
  val writer: String,
  val country: Country,
  val genre: Genre,
  val price: Int,
  val description: String
)