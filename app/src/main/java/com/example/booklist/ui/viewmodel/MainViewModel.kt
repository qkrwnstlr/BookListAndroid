package com.example.booklist.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.booklist.data.repository.RetrofitAPI
import com.example.booklist.data.service.BookService
import com.example.booklist.model.dto.AddBookRequestDTO
import com.example.booklist.model.dto.FindBookRequestDTO
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

  private val _recommendList = mutableListOf<BookEntity>()
  val recommendList: List<BookEntity> = _recommendList
  val recommendImageList: List<String> = mutableListOf()

  fun updateRecommendList() {
    val findBookRequestDTO = FindBookRequestDTO(
      title = "",
      writer = "",
      country = Country.NONE,
      genre = Genre.NONE,
      price = Int.MAX_VALUE,
    )
    viewModelScope.launch {
      println("MainViewModel(viewModelScope) : test1")
      var newSearchList: Response<List<BookEntity>>
      try {
        newSearchList = _bookService.getBookList(findBookRequestDTO.toMap())
      } catch (e: Exception) {
        println("test2")
        throw Exception("MainViewModel(viewModelScope) : updateRecommendList")
      }
      println("MainViewModel(viewModelScope) : test3")
      _recommendList.clear()
      if (newSearchList.isSuccessful && newSearchList.body() != null) {
        _recommendList.addAll(newSearchList.body()!!)
      }
    }
  }

  private val _searchList = mutableListOf<BookEntity>()
  val searchList: List<BookEntity> = _recommendList

  val searchTitleTextFieldController = CustomTextFieldController(::updateSearchList)
  val searchWriterTextFieldController = CustomTextFieldController(::updateSearchList)
  var searchCountryValue by mutableStateOf<Country>(Country.NONE)
  var searchGenreValue by mutableStateOf<Genre>(Genre.NONE)
  var searchPriceValue by mutableStateOf<Int>(0)

  private val _isCheckedList = mutableStateMapOf<BookEntity, Boolean>()
  val checkBoxListController by lazy { CheckBoxListController(searchList, _isCheckedList) }
  val isCheckedList: Map<BookEntity, Boolean> = _isCheckedList

  fun updateSearchList() {
    val findBookRequestDTO = FindBookRequestDTO(
      title = searchTitleTextFieldController.text,
      writer = searchWriterTextFieldController.text,
      country = searchCountryValue,
      genre = searchGenreValue,
      price = searchPriceValue,
    )
    viewModelScope.launch {
      val newSearchList = _bookService.getBookList(findBookRequestDTO.toMap())
      _searchList.clear()
      if (newSearchList.isSuccessful && newSearchList.body() != null) {
        _searchList.addAll(newSearchList.body()!!)
      }
    }
  }

  private var _searchCountryDropdownMenuExpended by mutableStateOf(false)
  private var _searchGenreDropdownMenuExpended by mutableStateOf(false)

  val searchCountryDropdownMenuController = CustomDropdownMenuController(
    _searchCountryDropdownMenuExpended,
    searchCountryValue,
    Country.values().toList()
  )
  val searchGenreDropdownMenuController = CustomDropdownMenuController(
    _searchGenreDropdownMenuExpended,
    searchGenreValue,
    Genre.values().toList()
  )

  fun onSearchButtonClicked() {

  }

  fun onEditButtonClicked(bookEntity: BookEntity) {

  }

  val addTitleTextFieldController = CustomTextFieldController(::updateSearchList)
  val addWriterTextFieldController = CustomTextFieldController(::updateSearchList)
  var addCountryValue by mutableStateOf<Country>(Country.NONE)
  var addGenreValue by mutableStateOf<Genre>(Genre.NONE)
  var addPriceValue by mutableStateOf<Int>(0)
  val addDescriptionTextFieldController = CustomTextFieldController(::updateSearchList)

  val addCountryDropdownMenuController = CustomDropdownMenuController(
    _searchCountryDropdownMenuExpended,
    addCountryValue,
    Country.values().toList()
  )
  val addGenreDropdownMenuController = CustomDropdownMenuController(
    _searchGenreDropdownMenuExpended,
    addGenreValue,
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
      country = addCountryValue,
      genre = addGenreValue,
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