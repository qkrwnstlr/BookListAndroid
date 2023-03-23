package com.example.booklist.ui.common

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
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
      .height(ButtonDefaults.MinHeight * customTextFieldController.maxLine)
      .fillMaxWidth(),
    keyboardOptions = customTextFieldController.keyboardOptions,
  ) {
    TextFieldDefaults.TextFieldDecorationBox(
      value = customTextFieldController.text,
      innerTextField = it,
      enabled = false,
      singleLine = customTextFieldController.maxLine == 1,
      visualTransformation = VisualTransformation.None,
      interactionSource = remember { MutableInteractionSource() },
      leadingIcon = leadingIcon,
      contentPadding =
      if (customTextFieldController.maxLine == 1) PaddingValues(0.dp, 0.dp, 10.dp, 0.dp) // 패딩 삭제
      else PaddingValues(0.dp, 10.dp, 10.dp, 10.dp),
      shape =
      if (customTextFieldController.maxLine == 1) ButtonDefaults.shape
      else RoundedCornerShape(10.dp),
      colors = TextFieldDefaults.textFieldColors(
        disabledIndicatorColor = Color.Transparent // 밑줄 삭제
      )
    )
  }
}

class CustomTextFieldController(
  val keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
  val maxLine: Int = 1,
  initValue: String = "",
  private val onTextChangeCallback: () -> Unit = {}
) {
  init {
    println("CustomTexFieldController is created : $initValue")
  }
  var text by mutableStateOf(initValue)
  fun onTextChange(value: String, callback: () -> Unit = onTextChangeCallback) {
    text = value
    println("CustomTexField : $text, $value")
    callback()
  }

  fun clearText() {
    text = ""
  }
}