package com.example.booklist.model.dto

import com.example.booklist.model.type.Country
import com.example.booklist.model.type.Genre

data class EditBookRequestDTO(
  val title: String,
  val writer: String,
  val country: Country,
  val genre: Genre,
  val price: Int,
  val description: String
)