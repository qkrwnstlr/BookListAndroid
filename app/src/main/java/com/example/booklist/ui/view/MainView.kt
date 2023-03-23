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
  if (!viewModel.isDetailViewExpected) Scaffold(
    topBar = {
      TopAppBar(
        title = { Text("Main View${if (viewModel.menuCountryDropdownMenuController.currentValue != Country.ALL) " > " + viewModel.menuCountryDropdownMenuController.currentValue.toString() else ""}") },
        navigationIcon = {
          IconButton(onClick = {}) {
            CustomIconDropDownMenu(
              viewModel.menuCountryDropdownMenuController,
              Icons.Rounded.Menu
            )
          }
        },
        actions = {
          IconButton(onClick = viewModel::onRefreshButtonClicked) {
            Icon(Icons.Rounded.Refresh, "Refresh Btn")
          }
        }
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
    Box(modifier = Modifier
      .imePadding()
      .clickable(
        interactionSource = MutableInteractionSource(),
        indication = null
      ) { focusManger.clearFocus() }) {
      LazyColumn(modifier.padding(it), verticalArrangement = Arrangement.spacedBy(10.dp)) {
        item {
          RecommendLayout(modifier = Modifier.padding(10.dp))
        }
        item {
          SearchLayout()
        }
        items(items = viewModel.searchList) { bookEntity ->
          println("MainView : SearchLayout ${bookEntity.id}, ${bookEntity.title}, ${bookEntity.country}")
          ListItem(
            bookEntity,
            viewModel.isCheckedList[bookEntity] ?: false,
            viewModel.checkBoxListController,
            viewModel::onDetailButtonClicked
          )
        }
      }
      /*ConstraintLayout(
        modifier
          .fillMaxSize()
          .padding(it)
      ) {
        val (recommendLayout, searchLayout) = createRefs() // 제약에 사용할 참조 생성
        RecommendLayout(
          modifier = Modifier
            .constrainAs(recommendLayout) {
              width = Dimension.fillToConstraints
              height = Dimension.percent(0.3f)
              centerHorizontallyTo(parent)
              top.linkTo(parent.top)
              bottom.linkTo(searchLayout.top)
            }
            .padding(horizontal = 10.dp)
        )
        SearchLayout(
          modifier = Modifier
            .constrainAs(searchLayout) {
              width = Dimension.fillToConstraints
              height = Dimension.percent(0.7f)
              centerHorizontallyTo(parent)
              bottom.linkTo(parent.bottom)
            }
        )
      }*/
      AddBookPopup(
        expanded = viewModel.isAddBookPopupExpended,
        onDismissRequest = viewModel::onIsAddBookPopupExpendedChanged,
        onAddButtonClicked = viewModel::onAddBookButtonClicked,
        titleFieldController = viewModel.addTitleTextFieldController,
        writerFieldController = viewModel.addWriterTextFieldController,
        priceFieldController = viewModel.addPriceTextFieldController,
        countryDropdownMenuController = viewModel.addCountryDropdownMenuController,
        genreDropdownMenuController = viewModel.addGenreDropdownMenuController,
        descriptionFieldController = viewModel.addDescriptionTextFieldController
      )
    }
    viewModel.updateRecommendList()
    viewModel.initSearchList()
  }
  else DetailView(
    viewModel = viewModel.detailViewModel,
    onNavigatePop = viewModel::onDetailButtonClose
  )
}

@Composable
fun RecommendLayout(modifier: Modifier = Modifier) {
  val viewModel = viewModel<MainViewModel>()
  ImageSlider(
    modifier = modifier,
    imageList = viewModel.recommendImageList,
    itemList = viewModel.recommendList,
    onItemClicked = viewModel::onDetailButtonClicked
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
      viewModel.searchPriceTextFieldController,
      viewModel.searchCountryDropdownMenuController,
      viewModel.searchGenreDropdownMenuController,
      viewModel::onSearchButtonClicked,
      viewModel::onDeleteButtonClicked
    )
    SearchTop(
      viewModel.checkBoxListController,
    )
  }
}

@Composable
fun SearchHead(
  titleFieldController: CustomTextFieldController,
  writerFieldController: CustomTextFieldController,
  priceFieldController: CustomTextFieldController,
  countryDropdownMenuController: CustomDropdownMenuController<Country>,
  genreDropdownMenuController: CustomDropdownMenuController<Genre>,
  onSearchButtonClicked: () -> Unit,
  onDeleteButtonClicked: () -> Unit,
  modifier: Modifier = Modifier
) {
  Column(
    modifier = modifier.padding(horizontal = 10.dp),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.spacedBy(10.dp)
  ) {
    CustomTextField(titleFieldController) {
      Icon(Icons.Rounded.Title, "Title Icon")
    }
    CustomTextField(writerFieldController) {
      Icon(Icons.Rounded.Person, "Person Icon")
    }
    CustomTextField(priceFieldController) {
      Icon(Icons.Rounded.Money, "Money Icon")
    }
    Row(
      horizontalArrangement = Arrangement.spacedBy(10.dp),
      modifier = modifier,
      verticalAlignment = Alignment.CenterVertically
    ) {
      CustomTextDropdownMenu(countryDropdownMenuController, Modifier.weight(1f))
      CustomTextDropdownMenu(genreDropdownMenuController, Modifier.weight(1f))
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
      IconButton(
        onClick = onDeleteButtonClicked,
        modifier = Modifier
      ) {
        Icon(
          imageVector = Icons.Rounded.Delete,
          contentDescription = "Delete Btn",
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
      text = "Title",
      modifier = Modifier.weight(4f),
      textAlign = TextAlign.Center,
      fontWeight = FontWeight.Bold
    )
    Text(
      text = "Detail",
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
fun ListItem(
  bookEntity: BookEntity,
  isChecked: Boolean,
  checkBoxListController: CheckBoxListController<BookEntity>,
  onDetailButtonClick: (BookEntity) -> Unit,
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
      onClick = { onDetailButtonClick(bookEntity) },
      contentPadding = PaddingValues(0.dp),
      modifier = Modifier
        .padding(horizontal = 10.dp)
        .weight(1f)
    ) {
      Text(text = "상세")
    }
  }
}

@Composable
fun AddBookPopup(
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
          Text("Add Book", fontWeight = FontWeight.Bold, modifier = Modifier.padding(start = 10.dp))
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
              Text("추가")
            }
          }
        }
      }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun Preview() {
  val viewModel = viewModel<MainViewModel>()
  Scaffold {
    ImageSlider(
      modifier = Modifier.padding(it),
      imageList = viewModel.recommendImageList,
      itemList = viewModel.recommendList,
      onItemClicked = viewModel::onDetailButtonClicked
    )
  }
}