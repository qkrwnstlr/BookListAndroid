package com.example.booklist.ui.common

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.FilterList
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ModifierInfo
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp

@Composable
fun <T> CustomTextDropdownMenu(
  controller: CustomDropdownMenuController<T>,
  modifier: Modifier = Modifier
) {
  Row(modifier = modifier
    .fillMaxWidth()
    .height(ButtonDefaults.MinHeight)) {
    Text(controller.currentValue.toString(), modifier = Modifier.weight(1f))
    Box {
      IconButton(onClick = controller::onShowRequest) {
        Icon(
          Icons.Rounded.FilterList,
          "Search Btn"
        )
      }
      CustomDropdownMenu(controller)
    }
  }
}

@Composable
fun <T> CustomDropdownMenu(
  controller: CustomDropdownMenuController<T>,
) {
  DropdownMenu(
    expanded = controller.expanded,
    onDismissRequest = controller::onDismissRequest,
    offset = DpOffset(0.dp, 10.dp),
  ) {
    controller.dropdownMenuItemList.forEach {
      DropdownMenuItem(
        text = {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = it.toString(), Modifier.weight(1f))
            Icon(
              imageVector = Icons.Rounded.Check,
              contentDescription = "Check Icon",
              tint = if (controller.currentValue == it) Color.Black else Color.Transparent,
              modifier = Modifier.padding(start = 16.dp)
            )
          }
        },
        onClick = { controller.onDropdownMenClicked(it) },
      )
    }
  }
}

class CustomDropdownMenuController<T>(
  var expanded: Boolean,
  var currentValue: T,
  var dropdownMenuItemList: List<T>
) {
  fun onShowRequest() {
    expanded = true
  }

  fun onDismissRequest() {
    expanded = false
  }

  fun onDropdownMenClicked(value: T) {
    currentValue = value
    onDismissRequest()
  }
}