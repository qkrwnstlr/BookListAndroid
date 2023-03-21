package com.example.booklist.model.dto

import com.example.booklist.model.type.Country
import com.example.booklist.model.type.Genre
import java.util.Objects

data class FindBookRequestDTO(
  val title: String,
  val writer: String,
  val country: Country,
  val genre: Genre,
  val price: Int,
) {
  fun toMap(): Map<String, Any> = mapOf<String, Any>(
    "title" to title,
    "writer" to writer,
    "country" to country.toString(),
    "genre" to genre.toString(),
    "price" to price
  )
}