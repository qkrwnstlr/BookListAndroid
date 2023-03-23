package com.example.booklist.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.booklist.model.type.Country
import com.example.booklist.model.type.Genre
import com.example.booklist.ui.viewmodel.DetailViewModel
import androidx.compose.material.Card
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Scale
import com.example.booklist.model.entity.BookEntity
import com.example.booklist.ui.common.*


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailView(
  viewModel: DetailViewModel,
  onNavigatePop: () -> Unit,
  modifier: Modifier = Modifier
) {
  Scaffold(
    topBar = {
      TopAppBar(
        title = { Text("Detail View") },
        navigationIcon = {
          IconButton(onClick = onNavigatePop) {
            Icon(Icons.Rounded.ArrowBack, "Back Btn")
          }
        }
      )
    },
    floatingActionButton = {
      FloatingActionButton(
        onClick = viewModel::onIsEditBookPopupExpendedChanged,
        shape = RoundedCornerShape(16.dp),
      ) {
        Icon(
          imageVector = Icons.Rounded.Edit,
          contentDescription = "Edit FAB",
          tint = Color.White,
        )
      }
    }
  ) {
    Box(
      modifier = modifier
        .padding(it)
        .imePadding()
    ) {
      BookInfoLayout(bookEntity = viewModel.bookEntity)
      EditBookPopup(
        expanded = viewModel.isEditListDataPopupExpended,
        onDismissRequest = viewModel::onIsEditBookPopupExpendedChanged,
        onAddButtonClicked = viewModel::onEditBookButtonClicked,
        titleFieldController = viewModel.editTitleTextFieldController,
        writerFieldController = viewModel.editWriterTextFieldController,
        priceFieldController = viewModel.editPriceTextFieldController,
        countryDropdownMenuController = viewModel.editCountryDropdownMenuController,
        genreDropdownMenuController = viewModel.editGenreDropdownMenuController,
        descriptionFieldController = viewModel.editDescriptionTextFieldController
      )
    }
  }
}

@Composable
fun BookInfoLayout(modifier: Modifier = Modifier, bookEntity: BookEntity) {
  val scrollState = rememberScrollState()
  Column(
    modifier = modifier
      .padding(horizontal = 10.dp)
      .verticalScroll(scrollState),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.spacedBy(10.dp)
  ) {
    Card(
      backgroundColor = Color.Transparent,
      shape = RoundedCornerShape(10.dp),
      elevation = 10.dp,
    ) {
      AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
          .data("https://picsum.photos/id/${bookEntity.id * 30}/1200/1800")
          .crossfade(true)
          .scale(Scale.FIT)
          .build(),
        contentDescription = null,
      )
    }
    Column(
      modifier = Modifier
        .fillMaxWidth()
    ) {
      val column1Weight = .3f // 30%
      val column2Weight = .7f // 70%
      Row(verticalAlignment = Alignment.CenterVertically) {
        TableCell(text = "title", weight = column1Weight, Modifier.background(Color(0xFFE7E0EC)))
        TableCell(text = bookEntity.title, weight = column2Weight)
      }
      Row(verticalAlignment = Alignment.CenterVertically) {
        TableCell(text = "writer", weight = column1Weight, Modifier.background(Color(0xFFE7E0EC)))
        TableCell(text = bookEntity.writer, weight = column2Weight)
      }
      Row(verticalAlignment = Alignment.CenterVertically) {
        TableCell(text = "country", weight = column1Weight, Modifier.background(Color(0xFFE7E0EC)))
        TableCell(text = bookEntity.country.toString(), weight = column2Weight)
      }
      Row(verticalAlignment = Alignment.CenterVertically) {
        TableCell(text = "genre", weight = column1Weight, Modifier.background(Color(0xFFE7E0EC)))
        TableCell(text = bookEntity.genre.toString(), weight = column2Weight)
      }
      Row(verticalAlignment = Alignment.CenterVertically) {
        TableCell(text = "price", weight = column1Weight, Modifier.background(Color(0xFFE7E0EC)))
        TableCell(text = bookEntity.price.toString(), weight = column2Weight)
      }
      Row(verticalAlignment = Alignment.CenterVertically) {
        TableCell(text = "description", weight = column1Weight, Modifier.background(Color(0xFFE7E0EC)))
        TableCell(text = bookEntity.description, weight = column2Weight)
      }
    }
    Spacer(modifier = Modifier.height(10.dp))
  }
}

@Composable
fun EditBookPopup(
  expanded: Boolean,
  onDismissRequest: () -> Unit,
  onAddButtonClicked: () -> Unit,
  titleFieldController: CustomTextFieldController,
  writerFieldController: CustomTextFieldController,
  priceFieldController: CustomTextFieldController,
  countryDropdownMenuController: CustomDropdownMenuController<Country>,
  genreDropdownMenuController: CustomDropdownMenuController<Genre>,
  descriptionFieldController: CustomTextFieldController,
  modifier: Modifier = Modifier,
) {
  if (expanded)
    Dialog(onDismissRequest = onDismissRequest) {
      val focusManger = LocalFocusManager.current
      Surface(
        modifier = modifier
          .fillMaxWidth()
          .clickable(
            interactionSource = MutableInteractionSource(),
            indication = null // 클릭시 퍼짐 효과 삭제
          ) { focusManger.clearFocus() },
        shape = RoundedCornerShape(12.dp),
        color = Color.White
      ) {
        Column(
          modifier = Modifier
            .wrapContentWidth()
            .padding(20.dp),
          verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          CustomTextField(
            titleFieldController,
          ) { Icon(Icons.Rounded.Title, "Title Icon") }
          CustomTextField(
            writerFieldController,
          ) { Icon(Icons.Rounded.Person, "Person Icon") }
          CustomTextField(
            priceFieldController,
          ) { Icon(Icons.Rounded.Money, "Money Icon") }
          Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically
          ) {
            CustomTextDropdownMenu(countryDropdownMenuController, modifier.weight(1f))
            CustomTextDropdownMenu(genreDropdownMenuController, modifier.weight(1f))
          }
          CustomTextField(
            descriptionFieldController,
          ) { Icon(Icons.Rounded.Description, "Description Icon") }
          Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()) {
            Button(onClick = onAddButtonClicked) {
              Text("수정")
            }
          }
        }
      }
    }
}