package com.example.booklist.model.entity

import com.example.booklist.model.type.Country
import com.example.booklist.model.type.Genre
import org.json.JSONObject

data class BookEntity(
  val id: Long,
  val title: String,
  val writer: String,
  val country: Country,
  val genre: Genre,
  val price: Int,
  val description: String
) {
  companion object {
    fun fromJson(json: JSONObject): BookEntity = BookEntity(
      json.getLong("id"),
      json.getString("title"),
      json.getString("writer"),
      Country.fromString(json.getString("country")),
      Genre.fromString(json.getString("genre")),
      json.getInt("price"),
      json.getString(json.getString("description"))
    )
  }
}