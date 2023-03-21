package com.example.booklist.ui.common

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateMap

class CheckBoxListController<T>(
  private val dataList: List<T>,
  private val _isCheckedList: SnapshotStateMap<T, Boolean> = mutableStateMapOf()
) {
  private var _isCheckedCount by mutableStateOf(0)
  var isAllChecked by mutableStateOf(false)

  fun onCheckedChange(target: T, changeTo: Boolean? = null) {
    _isCheckedList[target] = changeTo ?: !(_isCheckedList[target] ?: false)
    if (_isCheckedList[target]!!) ++_isCheckedCount
    else --_isCheckedCount
    isAllChecked = _isCheckedCount == dataList.size
  }

  fun onAllCheckedChanged(changeTo: Boolean = !isAllChecked) {
    if (changeTo) setAllCheckedList()
    else clearCheckedList()
  }

  private fun clearCheckedList() {
    isAllChecked = false
    _isCheckedList.clear()
    _isCheckedCount = 0
  }

  private fun setAllCheckedList() {
    isAllChecked = true
    dataList.forEach { _isCheckedList[it] = true }
    _isCheckedCount = dataList.size
  }
}