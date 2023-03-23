package com.example.booklist.ui.common

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.FilterList
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp

@Composable
fun <T> CustomIconDropDownMenu(
  controller: CustomDropdownMenuController<T>,
  icon: ImageVector
) {
  Box {
    IconButton(onClick = controller::onShowRequest) {
      Icon(
        icon,
        "Search Btn"
      )
    }
    CustomDropdownMenu(controller)
  }
}

@Composable
fun <T> CustomTextDropdownMenu(
  controller: CustomDropdownMenuController<T>,
  modifier: Modifier = Modifier
) {
  Row(
    modifier = modifier
      .height(ButtonDefaults.MinHeight), verticalAlignment = Alignment.CenterVertically
  ) {
    Text(
      controller.currentValue.toString(),
      fontWeight = FontWeight.Bold,
      modifier = Modifier.weight(1f),
      textAlign = TextAlign.Center
    )
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
  initValue: T,
  var dropdownMenuItemList: List<T>,
  val onDismissRequestCallBack: () -> Unit = {}
) {
  var expanded by mutableStateOf<Boolean>(false)
  var currentValue by mutableStateOf<T>(initValue)
  fun onShowRequest() {
    expanded = true
  }

  fun onDismissRequest() {
    expanded = false
    onDismissRequestCallBack()
  }

  fun onDropdownMenClicked(value: T) {
    currentValue = value
    onDismissRequest()
  }
}