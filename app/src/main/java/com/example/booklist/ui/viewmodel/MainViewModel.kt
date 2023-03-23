package com.example.booklist.ui.viewmodel

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.*
import androidx.compose.ui.text.input.KeyboardType
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.booklist.data.repository.RetrofitAPI
import com.example.booklist.data.service.BookService
import com.example.booklist.model.dto.AddBookRequestDTO
import com.example.booklist.model.dto.FindBookRequestDTO
import com.example.booklist.model.dto.FindBookResponseDTO
import com.example.booklist.model.entity.BookEntity
import com.example.booklist.model.type.Country
import com.example.booklist.model.type.Genre
import com.example.booklist.ui.common.CheckBoxListController
import com.example.booklist.ui.common.CustomDropdownMenuController
import com.example.booklist.ui.common.CustomTextFieldController
import kotlinx.coroutines.launch
import retrofit2.Response

class MainViewModel : ViewModel() {
  private val _bookService by lazy { RetrofitAPI.retrofitService(BookService::class.java) }

  val recommendList = mutableStateListOf<BookEntity>()
  val recommendImageList = mutableStateListOf<String>()

  fun updateRecommendList() {
    viewModelScope.launch {
      val newRecommendList: Response<List<FindBookResponseDTO>>
      try {
        newRecommendList = _bookService.getBook()
      } catch (e: Exception) {
        throw Exception("MainViewModel(viewModelScope) : updateRecommendList $e")
      }
      recommendList.clear()
      recommendImageList.clear()
      if (newRecommendList.isSuccessful && newRecommendList.body() != null) {
        newRecommendList.body()!!.forEach {
          recommendList.add(BookEntity.fromDTO(it))
        }
        for (i in 0 until recommendList.size) recommendImageList.add("https://picsum.photos/id/${recommendList[i].id * 30}/1200/1800")
//        _recommendImageList.addAll(newSearchList.body()!!.image)
        println("MainViewModel(viewModelScope) : ${recommendList.size}")
      }
    }
  }

  fun initSearchList() {
    viewModelScope.launch {
      val newSearchList = _bookService.getBook()
      searchList.clear()
      if (newSearchList.isSuccessful && newSearchList.body() != null) {
        newSearchList.body()!!.forEach {
          searchList.add(BookEntity.fromDTO(it))
        }
        println("MainViewModel(viewModelScope) : ${searchList.size}")
      }
    }
  }

  val searchList = mutableStateListOf<BookEntity>()

  val searchTitleTextFieldController = CustomTextFieldController()
  val searchWriterTextFieldController = CustomTextFieldController()
  val searchPriceTextFieldController =
    CustomTextFieldController(KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number))

  private val _isCheckedList = mutableStateMapOf<BookEntity, Boolean>()
  val checkBoxListController by lazy { CheckBoxListController(searchList, _isCheckedList) }
  val isCheckedList: Map<BookEntity, Boolean> = _isCheckedList

  val searchCountryDropdownMenuController = CustomDropdownMenuController(
    Country.ALL,
    Country.values().toList()
  )
  val searchGenreDropdownMenuController = CustomDropdownMenuController(
    Genre.ALL,
    Genre.values().toList()
  )

  private fun updateSearchList() {
    val findBookRequestDTO = FindBookRequestDTO(
      title = searchTitleTextFieldController.text,
      writer = searchWriterTextFieldController.text,
      country = searchCountryDropdownMenuController.currentValue,
      genre = searchGenreDropdownMenuController.currentValue,
      price = searchPriceTextFieldController.text.toIntOrNull() ?: 0,
    )
    println("MainViewModel : updateSearchList ${findBookRequestDTO.title}")
    viewModelScope.launch {
      val newSearchList = _bookService.getBookList(findBookRequestDTO.toMap())
      searchList.clear()
      if (newSearchList.isSuccessful && newSearchList.body() != null) {
        newSearchList.body()!!.forEach {
          searchList.add(BookEntity.fromDTO(it))
        }
        println("MainViewModel(viewModelScope) : ${searchList.size}")
      }
    }
  }

  fun onRefreshButtonClicked() {
    searchCountryDropdownMenuController.currentValue = Country.ALL
    searchGenreDropdownMenuController.currentValue = Genre.ALL
    searchTitleTextFieldController.clearText()
    searchWriterTextFieldController.clearText()
    searchPriceTextFieldController.clearText()
    initSearchList()
    updateRecommendList()
  }

  fun onSearchButtonClicked() {
    updateSearchList()
  }

  val menuCountryDropdownMenuController = CustomDropdownMenuController(
    Country.ALL,
    Country.values().toList(),
    ::onMenuButtonClicked
  )

  fun onMenuButtonClicked() {
    searchCountryDropdownMenuController.currentValue =
      menuCountryDropdownMenuController.currentValue
    updateSearchList()
  }

  val addTitleTextFieldController = CustomTextFieldController()
  val addWriterTextFieldController = CustomTextFieldController()
  val addPriceTextFieldController =
    CustomTextFieldController(KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number))
  val addDescriptionTextFieldController = CustomTextFieldController(maxLine = 3)

  val addCountryDropdownMenuController = CustomDropdownMenuController(
    Country.ALL,
    Country.values().toList()
  )
  val addGenreDropdownMenuController = CustomDropdownMenuController(
    Genre.ALL,
    Genre.values().toList()
  )

  var isAddBookPopupExpended by mutableStateOf(false)
  fun onIsAddBookPopupExpendedChanged() {
    isAddBookPopupExpended = !isAddBookPopupExpended
    addCountryDropdownMenuController.currentValue = Country.ALL
    addGenreDropdownMenuController.currentValue = Genre.ALL
    addTitleTextFieldController.clearText()
    addWriterTextFieldController.clearText()
    addPriceTextFieldController.clearText()
    addDescriptionTextFieldController.clearText()
  }

  fun onAddBookButtonClicked() {
    val addBookRequestDTO = AddBookRequestDTO(
      title = addTitleTextFieldController.text,
      writer = addWriterTextFieldController.text,
      country = addCountryDropdownMenuController.currentValue,
      genre = addGenreDropdownMenuController.currentValue,
      price = addPriceTextFieldController.text.toIntOrNull() ?: 0,
      description = addDescriptionTextFieldController.text,
    )
    onIsAddBookPopupExpendedChanged()
    viewModelScope.launch {
      _bookService.addBook(addBookRequestDTO)
      onRefreshButtonClicked()
    }
  }

  fun onDeleteButtonClicked() {
    viewModelScope.launch {
      _isCheckedList.forEach {
        try {
          if (it.value) _bookService.deleteBook(it.key.id)
        } catch (e: Exception) {
          println("MainViewModel : onDeleteButtonClicked $e")
        }
      }
      onRefreshButtonClicked()
    }
  }

  val detailViewModel = DetailViewModel(BookEntity(0, "", "", Country.ALL, Genre.ALL, 0, ""))
  var isDetailViewExpected by mutableStateOf(false)
  fun onDetailButtonClicked(bookEntity: BookEntity) {
    detailViewModel.bookEntity = bookEntity
    isDetailViewExpected = true
  }

  fun onDetailButtonClose() {
    onRefreshButtonClicked()
    isDetailViewExpected = false
  }
}