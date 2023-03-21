package com.example.booklist.model.dto

data class FindBookResponseDTO(
  val id: Long,
  val title: String,
  val writer: String,
  val country: String,
  val genre: String,
  val price: Int,
  val description: String,
)