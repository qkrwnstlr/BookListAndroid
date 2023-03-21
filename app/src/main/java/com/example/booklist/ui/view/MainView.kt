package com.example.booklist.ui.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.booklist.model.entity.BookEntity
import com.example.booklist.model.type.Country
import com.example.booklist.model.type.Genre
import com.example.booklist.ui.common.*
import com.example.booklist.ui.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainView(modifier: Modifier = Modifier) {
  val viewModel = viewModel<MainViewModel>()
  viewModel.updateRecommendList()
  viewModel.updateSearchList()
  Scaffold(
    topBar = {
      TopAppBar(
        title = { Text("Book List") },
      )
    },
    floatingActionButton = {
      FloatingActionButton(
        onClick = viewModel::onIsAddBookPopupExpendedChanged,
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
    val focusManger = LocalFocusManager.current
    Box(modifier = Modifier.clickable(
      interactionSource = MutableInteractionSource(),
      indication = null
    ) { focusManger.clearFocus() }) {
      ConstraintLayout(
        modifier
          .fillMaxSize()
          .padding(it)
      ) {
        val (recommendLayout, searchLayout) = createRefs() // 제약에 사용할 참조 생성
        RecommendLayout(
          modifier = Modifier.constrainAs(recommendLayout) {
            width = Dimension.fillToConstraints
            centerHorizontallyTo(parent)
            top.linkTo(parent.top)
          }
        )
        SearchLayout(
          modifier = Modifier.constrainAs(searchLayout) {
            width = Dimension.fillToConstraints
            centerHorizontallyTo(parent)
            top.linkTo(recommendLayout.bottom)
            bottom.linkTo(parent.bottom)
          }
        )
      }
      AddListDataPopup(
        expanded = viewModel.isAddListDataPopupExpended,
        onDismissRequest = viewModel::onIsAddBookPopupExpendedChanged,
        onAddButtonClicked = viewModel::onAddBookButtonClicked,
        titleFieldController = viewModel.addTitleTextFieldController,
        writerFieldController = viewModel.addWriterTextFieldController,
        countryDropdownMenuController = viewModel.addCountryDropdownMenuController,
        genreDropdownMenuController = viewModel.addGenreDropdownMenuController,
        descriptionFieldController = viewModel.addDescriptionTextFieldController
      )
    }
  }
}

@Composable
fun RecommendLayout(modifier: Modifier = Modifier) {
  val viewModel = viewModel<MainViewModel>()
  ImageSlider(
    modifier = modifier,
    imageList = viewModel.recommendImageList,
    itemList = viewModel.recommendList,
    onItemClicked = viewModel::showDetail
  )
}

@Composable
fun SearchLayout(modifier: Modifier = Modifier) {
  val viewModel = viewModel<MainViewModel>()
  Column(
    modifier, verticalArrangement = Arrangement.spacedBy(10.dp)
  ) {
    SearchHead(
      viewModel.searchTitleTextFieldController,
      viewModel.searchWriterTextFieldController,
      viewModel.searchCountryDropdownMenuController,
      viewModel.searchGenreDropdownMenuController,
      viewModel::onSearchButtonClicked,
    )
    SearchTop(
      viewModel.checkBoxListController,
    )
    LazyColumn { // 임마는 왜 자꾸 호출됨...?
      items(items = viewModel.searchList) { bookEntity ->
        ListItem(
          bookEntity,
          viewModel.isCheckedList[bookEntity] ?: false,
          viewModel.checkBoxListController,
          viewModel::onEditButtonClicked
        )
      }
    }
  }
}

@Composable
fun SearchHead(
  titleFieldController: CustomTextFieldController,
  writerFieldController: CustomTextFieldController,
  countryDropdownMenuController: CustomDropdownMenuController<Country>,
  genreDropdownMenuController: CustomDropdownMenuController<Genre>,
  onSearchButtonClicked: () -> Unit,
  modifier: Modifier = Modifier
) {
  Column(
    modifier = modifier,
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    CustomTextField(titleFieldController) {
      Icon(Icons.Rounded.Title, "Title Icon")
    }
    CustomTextField(writerFieldController) {
      Icon(Icons.Rounded.Person, "Person Icon")
    }
    Row(
      horizontalArrangement = Arrangement.SpaceBetween,
      modifier = modifier,
      verticalAlignment = Alignment.CenterVertically
    ) {
      CustomTextDropdownMenu(countryDropdownMenuController)
      CustomTextDropdownMenu(genreDropdownMenuController)
      IconButton(
        onClick = onSearchButtonClicked,
        modifier = Modifier
      ) {
        Icon(
          imageVector = Icons.Rounded.Search,
          contentDescription = "Search Btn",
          tint = Color.Gray
        )
      }
    }
  }
}

@Composable
fun SearchTop(
  checkBoxListController: CheckBoxListController<BookEntity>,
  modifier: Modifier = Modifier,
) {
  Row(
    horizontalArrangement = Arrangement.SpaceBetween,
    modifier = modifier.fillMaxWidth(),
    verticalAlignment = Alignment.CenterVertically
  ) {
    Checkbox(
      checked = checkBoxListController.isAllChecked,
      onCheckedChange = { checkBoxListController.onAllCheckedChanged() },
    )
    Text(
      text = "TODO",
      modifier = Modifier.weight(4f),
      textAlign = TextAlign.Center,
      fontWeight = FontWeight.Bold
    )
    Text(
      text = "Finish",
      modifier = Modifier
        .width(ButtonDefaults.MinWidth)
        .padding(horizontal = 10.dp)
        .weight(1f),
      textAlign = TextAlign.Center,
      fontWeight = FontWeight.Bold
    )
  }
}

@Composable
fun AddListDataPopup(
  expanded: Boolean,
  onDismissRequest: () -> Unit,
  onAddButtonClicked: () -> Unit,
  titleFieldController: CustomTextFieldController,
  writerFieldController: CustomTextFieldController,
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
          Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically
          ) {
            CustomTextDropdownMenu(countryDropdownMenuController)
            CustomTextDropdownMenu(genreDropdownMenuController)
          }
          CustomTextField(
            descriptionFieldController,
          ) { Icon(Icons.Rounded.Description, "Description Icon") }
          Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()) {
            Button(onClick = onAddButtonClicked) {
              Text("추가")
            }
          }
        }
      }
    }
}

@Composable
fun ListItem(
  bookEntity: BookEntity,
  isChecked: Boolean,
  checkBoxListController: CheckBoxListController<BookEntity>,
  onEditButtonClick: (BookEntity) -> Unit,
  modifier: Modifier = Modifier
) {
  Row(
    horizontalArrangement = Arrangement.SpaceBetween,
    modifier = modifier.fillMaxSize(),
    verticalAlignment = Alignment.CenterVertically
  ) {
    Checkbox(
      checked = isChecked,
      onCheckedChange = { checkBoxListController.onCheckedChange(bookEntity) },
    )
    Text(
      text = bookEntity.title,
      /*
      FIXME : TextDecoration이 처음에 None이 아닌 다른 것으로 설정된 후에는 정상적으로 바뀌지 않는 버그
      None에서 시작하면 다른걸로 바껴도 정상적으로 작동하나, None이 아닌 다른 것으로 시작하면 안 바뀜
      */
      modifier = Modifier.weight(4f),
      textAlign = TextAlign.Center
    )
    Button(
      onClick = { onEditButtonClick(bookEntity) },
      contentPadding = PaddingValues(0.dp),
      modifier = Modifier
        .padding(horizontal = 10.dp)
        .weight(1f)
    ) {
      Text(text = "수정")
    }
  }
}

@Preview
@Composable
fun Preview() {

}