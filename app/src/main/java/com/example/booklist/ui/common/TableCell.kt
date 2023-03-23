package com.example.booklist.ui.common

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun RowScope.TableCell(
  text: String,
  weight: Float,
  modifier: Modifier = Modifier
) {
  Text(
    text = text,
    modifier
      .border(1.dp, Color.Black)
      .weight(weight)
      .padding(8.dp),
    fontWeight = FontWeight.Bold
  )
}