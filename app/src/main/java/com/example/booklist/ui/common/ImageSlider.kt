package com.example.booklist.ui.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Scale
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.calculateCurrentOffsetForPage
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue

@OptIn(ExperimentalPagerApi::class)
@Composable
fun <T> ImageSlider(
  modifier: Modifier = Modifier,
  imageList: List<String> = listOf(),
  itemList: List<T> = listOf(),
  onItemClicked: (T) -> Unit
) {
  val pagerState = rememberPagerState(initialPage = 0)
  LaunchedEffect(key1 = pagerState.currentPage) { // 자동 스크롤
    launch {
      delay(2000)
      with(pagerState) {
        val target =
          if (imageList.isNotEmpty()) (currentPage + 1) % imageList.size else 0
        animateScrollToPage(page = target)
      }
    }
  }
  HorizontalPager(
    count = imageList.size, state = pagerState,
    contentPadding = PaddingValues(horizontal = 100.dp),
    verticalAlignment = Alignment.Top,
    modifier = modifier
      .wrapContentHeight()
  ) { page ->
    Card(
      backgroundColor = Color.Transparent,
      shape = RoundedCornerShape(10.dp),
      elevation = 5.dp,
      modifier = Modifier
        .graphicsLayer {
          val pageOffset = calculateCurrentOffsetForPage(page).absoluteValue
          lerp(
            start = 0.85.dp,
            stop = 0.85.dp,
            fraction = 1f - pageOffset.coerceIn(0f, 1f)
          ).also { scale ->
            scaleX = scale.value
            scaleY = scale.value
          }
          alpha = lerp(
            start = 1.dp,
            stop = 1.dp,
            fraction = 1f - pageOffset.coerceIn(0f, 1f)
          ).value
        }
      // .aspectRatio(0.5f)
    ) {
      AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
          .data(imageList[page])
          .crossfade(true)
          .scale(Scale.FILL)
          .build(),
        contentDescription = null,
        modifier = Modifier.clickable(
          interactionSource = MutableInteractionSource(),
          indication = null,
        ) { onItemClicked(itemList[page]) }
        /*modifier = Modifier
          .offset {
            // Calculate the offset for the current page from the
            // scroll position
            val pageOffset =
              this@HorizontalPager.calculateCurrentOffsetForPage(page)
            // Then use it as a multiplier to apply an offset
            IntOffset(
              x = (70.dp * pageOffset).roundToPx(),
              y = 0,
            )
          }*/
      )
    }
  }
}