package com.example.booklist.ui.view

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.booklist.ui.viewmodel.MainViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainView(modifier: Modifier = Modifier) {
  val viewModel = viewModel<MainViewModel>()
  Scaffold(
    topBar = {
      TopAppBar(
        title = { Text("Book List") },
      )
    },
    floatingActionButton = {
      FloatingActionButton(
        onClick = viewModel::showAddListDataView,
        shape = RoundedCornerShape(16.dp),
      ) {
        Icon(
          imageVector = Icons.Rounded.Add,
          contentDescription = "Add FAB",
          tint = Color.White,
        )
      }
    }
  ) {
    ConstraintLayout(
      modifier
        .fillMaxSize()
        .padding(it)
    ) {
      val (recommendLayout, searchLayout) = createRefs() // 제약에 사용할 참조 생성

    }
  }
}

@OptIn(ExperimentalPagerApi::class, ExperimentalMaterial3Api::class)
@Preview
@Composable
fun Preview() {
  val pagerState = rememberPagerState()

}