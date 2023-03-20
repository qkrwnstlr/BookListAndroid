package com.example.booklist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.booklist.ui.common.ImageSlider
import com.example.booklist.ui.theme.BookListTheme
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager

class MainActivity : ComponentActivity() {
  @OptIn(ExperimentalMaterial3Api::class)
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val sliderList = mutableListOf<String>()
    for (i in 0..10) sliderList.add("https://random.imagecdn.app/1240/1880")
    setContent {
      BookListTheme {
        Scaffold {
          ImageSlider(modifier = Modifier.padding(it), sliderList = sliderList)
        }
      }
    }
  }
}