package com.example.booklist.ui.viewmodel

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.KeyboardType
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.booklist.data.repository.RetrofitAPI
import com.example.booklist.data.service.BookService
import com.example.booklist.model.dto.EditBookRequestDTO
import com.example.booklist.model.dto.FindBookResponseDTO
import com.example.booklist.model.entity.BookEntity
import com.example.booklist.model.type.Country
import com.example.booklist.model.type.Genre
import com.example.booklist.ui.common.CustomDropdownMenuController
import com.example.booklist.ui.common.CustomTextFieldController
import kotlinx.coroutines.launch
import retrofit2.Response

class DetailViewModel(_bookEntity: BookEntity) : ViewModel() {
  private val _bookService by lazy { RetrofitAPI.retrofitService(BookService::class.java) }
  var bookEntity by mutableStateOf<BookEntity>(_bookEntity)

  val editTitleTextFieldController by lazy { CustomTextFieldController(initValue = bookEntity.title) }
  val editWriterTextFieldController by lazy { CustomTextFieldController(initValue = bookEntity.writer) }
  val editPriceTextFieldController by lazy {
    CustomTextFieldController(
      initValue = bookEntity.price.toString(),
      keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
    )
  }
  val editDescriptionTextFieldController by lazy {
    CustomTextFieldController(initValue = bookEntity.description, maxLine = 3)
  }

  val editCountryDropdownMenuController by lazy {
    CustomDropdownMenuController(
      bookEntity.country,
      Country.values().toList()
    )
  }
  val editGenreDropdownMenuController by lazy {
    CustomDropdownMenuController(
      bookEntity.genre,
      Genre.values().toList()
    )
  }

  var isEditListDataPopupExpended by mutableStateOf(false)
  fun onIsEditBookPopupExpendedChanged() {
    isEditListDataPopupExpended = !isEditListDataPopupExpended
    editTitleTextFieldController.clearText()
    editWriterTextFieldController.clearText()
    editPriceTextFieldController.clearText()
    editDescriptionTextFieldController.clearText()
  }

  fun onEditBookButtonClicked() {
    val editBookRequestDTO = EditBookRequestDTO(
      title = editTitleTextFieldController.text,
      writer = editWriterTextFieldController.text,
      country = editCountryDropdownMenuController.currentValue,
      genre = editGenreDropdownMenuController.currentValue,
      price = editPriceTextFieldController.text.toIntOrNull() ?: 0,
      description = editDescriptionTextFieldController.text,
    )
    onIsEditBookPopupExpendedChanged()
    viewModelScope.launch {
      val newBookEntity: Response<FindBookResponseDTO>
      try {
        newBookEntity = _bookService.editBook(bookEntity.id, editBookRequestDTO)
      } catch (e: Exception) {
        throw Exception("DetailViewModel : (viewModelScope) $e")
      }
      if (newBookEntity.isSuccessful && newBookEntity.body() != null) {
        bookEntity = BookEntity.fromDTO(newBookEntity.body()!!)
      }
    }
  }
}