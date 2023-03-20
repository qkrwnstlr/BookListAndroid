package com.example.booklist.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.booklist.data.repository.RetrofitAPI
import com.example.booklist.data.service.BookService

class MainViewModel: ViewModel() {
  private val bookService by lazy { RetrofitAPI.retrofitService(BookService::class.java) }
  fun showAddListDataView() {

  }
}