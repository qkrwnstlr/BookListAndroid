package com.example.booklist.ui.common

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Scale
import com.example.booklist.model.entity.BookEntity
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.calculateCurrentOffsetForPage
import com.google.accompanist.pager.rememberPagerState
import kotlin.math.absoluteValue

@OptIn(ExperimentalPagerApi::class)
@Composable
fun ImageSlider(modifier: Modifier = Modifier, sliderList: List<String> = listOf()) {
  val pagerState = rememberPagerState(initialPage = 1)
  HorizontalPager(
    count = sliderList.size, state = pagerState,
    contentPadding = PaddingValues(horizontal = 150.dp),
    verticalAlignment = Alignment.CenterVertically,
    modifier = modifier
  ) { page ->
    Card(
      backgroundColor = Color.Transparent,
      shape = RoundedCornerShape(10.dp),
      elevation = 0.dp,
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
          .data(sliderList[page])
          .crossfade(true)
          .scale(Scale.FILL)
          .build(),
        contentDescription = null,
        modifier = Modifier
          /*.offset {
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