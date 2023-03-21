package com.example.booklist.model.entity

import com.example.booklist.model.dto.FindBookResponseDTO
import com.example.booklist.model.type.Country
import com.example.booklist.model.type.Genre

data class BookEntity(
  val id: Long,
  val title: String,
  val writer: String,
  val country: Country,
  val genre: Genre,
  val price: Int,
  val description: String,
) {
  companion object {
    fun fromDTO(findBookResponseDTO: FindBookResponseDTO): BookEntity = BookEntity(
      id = findBookResponseDTO.id,
      title = findBookResponseDTO.title,
      writer = findBookResponseDTO.writer,
      country = Country.fromString(findBookResponseDTO.country),
      genre = Genre.fromString(findBookResponseDTO.genre),
      price = findBookResponseDTO.price,
      description = findBookResponseDTO.description
    )
  }
}