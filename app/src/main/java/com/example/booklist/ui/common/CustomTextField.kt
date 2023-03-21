package com.example.booklist.ui.common

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTextField(
  customTextFieldController: CustomTextFieldController,
  leadingIcon: @Composable (() -> Unit)? = null
) {
  println("CustomTexField : $customTextFieldController")
  BasicTextField(
    value = customTextFieldController.text,
    onValueChange = customTextFieldController::onTextChange,
    modifier = Modifier
      .height(ButtonDefaults.MinHeight)
      .fillMaxWidth()
  ) {
    TextFieldDefaults.TextFieldDecorationBox(
      value = customTextFieldController.text,
      innerTextField = it,
      enabled = false,
      singleLine = true,
      visualTransformation = VisualTransformation.None,
      interactionSource = remember { MutableInteractionSource() },
      leadingIcon = leadingIcon,
      contentPadding = PaddingValues(0.dp, 0.dp, 10.dp, 0.dp), // 패딩 삭제
      shape = ButtonDefaults.shape,
      colors = TextFieldDefaults.textFieldColors(
        disabledIndicatorColor = Color.Transparent // 밑줄 삭제
      )
    )
  }
}

class CustomTextFieldController(private val onTextChangeCallback: () -> Unit = {}) {
  var text by mutableStateOf("")
  fun onTextChange(value: String, callback: () -> Unit = onTextChangeCallback) {
    text = value
    println("CustomTexField : $text, $value")
    callback()
  }

  fun clearText() {
    text = ""
  }
}