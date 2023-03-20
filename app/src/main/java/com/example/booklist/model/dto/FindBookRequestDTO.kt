package com.example.booklist.model.dto

import com.example.booklist.model.type.Country
import com.example.booklist.model.type.Genre
import org.json.JSONObject

data class FindBookRequestDTO(
  val title: String,
  val writer: String,
  val country: Country,
  val genre: Genre,
  val price: Int,
) {
  fun toJson(): JSONObject =
    JSONObject()
      .put("title", title)
      .put("writer", writer)
      .put("country", country.toString())
      .put("genre", genre.toString())
      .put("price", price)
}