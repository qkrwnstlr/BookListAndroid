package com.example.booklist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.booklist.ui.view.MainView

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    WindowCompat.setDecorFitsSystemWindows(window, false) // 키보드에 따른 뷰 변화
    setContent {
      // 네비게이션 컨트롤러 선언
      val navController = rememberNavController()
      NavHost( // 경로 지정
        navController = navController, // 컨트롤러
        startDestination = "MainView" // 시작 화면의 route명
      ) {
        composable("MainView") { MainView() } // 화면과 route명을 연결
      }
    }
  }
}