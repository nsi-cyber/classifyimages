package com.nsicyber.classifyimages.screens

import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.nsicyber.classifyimages.models.ClassifiedFolderModel
import com.nsicyber.classifyimages.models.FolderModel
import com.nsicyber.classifyimages.models.ImagesModel
import com.nsicyber.classifyimages.toJson
import com.nsicyber.classifyimages.viewmodels.ClassifyScreenViewModel
import com.nsicyber.classifyimages.viewmodels.PreviewScreenViewModel
import java.util.Locale


@Composable
fun ClassifyScreen(
    context: Context,
    navController: NavController, model: ImagesModel
) {
    var scope = rememberCoroutineScope()

    var viewModel = hiltViewModel<ClassifyScreenViewModel>()

    LaunchedEffect(Unit) {
        viewModel.classifyImages(context, model.imageList!!)
    }


    var category by remember {
        mutableStateOf("")
    }
    var imageCategory by remember {
        mutableStateOf("")
    }

    val heightTextFields by remember {
        mutableStateOf(55.dp)
    }

    var textFieldSize by remember {
        mutableStateOf(Size.Zero)
    }

    var expanded by remember {
        mutableStateOf(false)
    }
    val interactionSource = remember {
        MutableInteractionSource()
    }
    Box() {


        Column(Modifier.padding(start = 8.dp, top = 60.dp, end = 8.dp)) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.wrapContentSize()
            ) {
                items(viewModel.classifyTitles.value.keys.size) { index ->
                    FolderCard(
                        model = viewModel.classifyTitles.value.keys.toList().get(index)
                    ) {
                        navController.navigate(
                            "preview_classified?model=${
                                ClassifiedFolderModel(it ,viewModel.classifyTitles.value.get(
                                    it
                                )).toJson()
                             
                            }"
                        )
                    }
                }
            }
        }


        // Category Field
        Column(
            modifier = Modifier
                .padding(30.dp)
                .fillMaxWidth()
                .clickable(
                    interactionSource = interactionSource,
                    indication = null,
                    onClick = {
                        expanded = false
                    }
                )
        ) {

            Text(
                modifier = Modifier.padding(start = 3.dp, bottom = 2.dp),
                text = "Classify",
                fontSize = 16.sp,
                color = Color.Black,
            )

            Column(modifier = Modifier.fillMaxWidth()) {

                Row(modifier = Modifier.fillMaxWidth()) {
                    TextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(heightTextFields)
                            .border(
                                width = 1.8.dp,
                                color = Color.Black,
                                shape = RoundedCornerShape(15.dp)
                            )
                            .onGloballyPositioned { coordinates ->
                                textFieldSize = coordinates.size.toSize()
                            },
                        value = category,
                        onValueChange = {
                            category = it
                            expanded = true
                        },
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            cursorColor = Color.Black
                        ),
                        textStyle = TextStyle(
                            color = Color.Black,
                            fontSize = 16.sp
                        ),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Done
                        ),
                        singleLine = true,
                        trailingIcon = {
                            IconButton(onClick = { expanded = !expanded }) {
                                Icon(
                                    modifier = Modifier.size(24.dp),
                                    imageVector = Icons.Rounded.KeyboardArrowDown,
                                    contentDescription = "arrow",
                                    tint = Color.Black
                                )
                            }
                        }
                    )
                }

                AnimatedVisibility(visible = expanded) {
                    Card(
                        modifier = Modifier
                            .padding(horizontal = 5.dp)
                            .width(textFieldSize.width.dp),
                        elevation = 15.dp,
                        shape = RoundedCornerShape(10.dp)
                    ) {

                        LazyColumn(
                            modifier = Modifier.heightIn(max = 150.dp)
                        ) {
                            val filteredItems = viewModel.classifyTitles.value.filter {
                                it.key.lowercase().contains(category)
                            }
                            val itemCount = filteredItems.size

                            items(itemCount) { index ->
                                val item = filteredItems.keys.toList()[index]
                                val count = filteredItems.values.toList()[index].size

                                CategoryItems(
                                    title = item,
                                    count = count
                                ) { title ->
                                    navController.navigate(
                                        "preview_classified?model=${
                                            ClassifiedFolderModel(title ,viewModel.classifyTitles.value.get(
                                                title
                                            )).toJson()
                                        }"
                                    )
                                    imageCategory = title
                                    category = title
                                    expanded = false
                                }
                            }
                        }


                    }
                }

            }

        }


    }


}


@Composable
fun CategoryItems(
    title: String, count: Int?,
    onSelect: (String) -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onSelect(title)
            }
            .padding(10.dp)
    ) {
        Text(text = title, fontSize = 16.sp)

        Text(text = count.toString(), fontSize = 16.sp)
    }

}