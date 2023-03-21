package com.example.booklist.ui.viewmodel

import androidx.compose.runtime.*
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

  private val _recommendList = mutableStateListOf<BookEntity>()
  val recommendList: List<BookEntity> = _recommendList
  private val _recommendImageList = mutableStateListOf<String>()
  val recommendImageList: List<String> = _recommendImageList

  fun updateRecommendList() {
    val findBookRequestDTO = FindBookRequestDTO(
      title = "",
      writer = "",
      country = Country.NONE,
      genre = Genre.NONE,
      price = Int.MAX_VALUE,
    )
    viewModelScope.launch {
      val newSearchList: Response<List<FindBookResponseDTO>>
      try {
        newSearchList = _bookService.getBookList(findBookRequestDTO.toMap())
      } catch (e: Exception) {
        throw Exception("MainViewModel(viewModelScope) : updateRecommendList $e")
      }
      _recommendList.clear()
      if (newSearchList.isSuccessful && newSearchList.body() != null) {
        newSearchList.body()!!.forEach {
          _recommendList.add(BookEntity.fromDTO(it))
        }
        for (i in 0 until _recommendList.size) _recommendImageList.add("https://picsum.photos/1200/1800")
//        _recommendImageList.addAll(newSearchList.body()!!.image)
        println("MainViewModel(viewModelScope) : ${_recommendList.size}")
      }
    }
  }

  private val _searchList = mutableStateListOf<BookEntity>()
  val searchList: List<BookEntity> = _recommendList

  val searchTitleTextFieldController = CustomTextFieldController(::updateSearchList)
  val searchWriterTextFieldController = CustomTextFieldController(::updateSearchList)
  var searchPriceValue by mutableStateOf<Int>(0)

  private val _isCheckedList = mutableStateMapOf<BookEntity, Boolean>()
  val checkBoxListController by lazy { CheckBoxListController(searchList, _isCheckedList) }
  val isCheckedList: Map<BookEntity, Boolean> = _isCheckedList

  val searchCountryDropdownMenuController = CustomDropdownMenuController(
    Country.NONE,
    Country.values().toList()
  )
  val searchGenreDropdownMenuController = CustomDropdownMenuController(
    Genre.NONE,
    Genre.values().toList()
  )

  fun updateSearchList() {
    val findBookRequestDTO = FindBookRequestDTO(
      title = searchTitleTextFieldController.text,
      writer = searchWriterTextFieldController.text,
      country = searchCountryDropdownMenuController.currentValue,
      genre = searchGenreDropdownMenuController.currentValue,
      price = searchPriceValue,
    )
    viewModelScope.launch {
      val newSearchList = _bookService.getBookList(findBookRequestDTO.toMap())
      _searchList.clear()
      if (newSearchList.isSuccessful && newSearchList.body() != null) {
        newSearchList.body()!!.forEach {
          _searchList.add(BookEntity.fromDTO(it))
        }
        println("MainViewModel(viewModelScope) : ${_searchList.size}")
      }
    }
  }

  fun onSearchButtonClicked() {

  }

  fun onEditButtonClicked(bookEntity: BookEntity) {

  }

  val addTitleTextFieldController = CustomTextFieldController(::updateSearchList)
  val addWriterTextFieldController = CustomTextFieldController(::updateSearchList)
  var addPriceValue by mutableStateOf<Int>(0)
  val addDescriptionTextFieldController = CustomTextFieldController(::updateSearchList)

  val addCountryDropdownMenuController = CustomDropdownMenuController(
    Country.NONE,
    Country.values().toList()
  )
  val addGenreDropdownMenuController = CustomDropdownMenuController(
    Genre.NONE,
    Genre.values().toList()
  )

  var isAddListDataPopupExpended by mutableStateOf(false)
  fun onIsAddBookPopupExpendedChanged() {
    isAddListDataPopupExpended = !isAddListDataPopupExpended
    addTitleTextFieldController.clearText()
  }

  fun onAddBookButtonClicked() {
    val addBookRequestDTO = AddBookRequestDTO(
      title = addTitleTextFieldController.text,
      writer = addWriterTextFieldController.text,
      country = addCountryDropdownMenuController.currentValue,
      genre = addGenreDropdownMenuController.currentValue,
      price = addPriceValue,
      description = addDescriptionTextFieldController.text,
    )
    onIsAddBookPopupExpendedChanged()
    viewModelScope.launch {
      _bookService.addBook(addBookRequestDTO)
      updateSearchList()
    }
  }

  fun showDetail(bookEntity: BookEntity) {
    // TODO : 상세보기 페이지로 이동
  }
}