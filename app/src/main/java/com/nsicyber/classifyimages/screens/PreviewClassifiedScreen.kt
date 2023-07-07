package com.nsicyber.classifyimages.screens

import android.app.Activity
import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.nsicyber.classifyimages.getRealPathFromURI
import com.nsicyber.classifyimages.models.ClassifiedFolderModel
import com.nsicyber.classifyimages.models.FolderModel
import com.nsicyber.classifyimages.models.ImagesModel
import com.nsicyber.classifyimages.toJson
import com.nsicyber.classifyimages.viewmodels.PreviewClassifiedScreenViewModel
import com.nsicyber.classifyimages.viewmodels.PreviewScreenViewModel


@Composable
fun PreviewClassifiedScreen(
    context: Context,
    navController: NavController, model: ClassifiedFolderModel?
) {
    var viewModel = hiltViewModel<PreviewClassifiedScreenViewModel>()



    var launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val uri: Uri? = result.data?.data
                viewModel.copyImagesToFolder(model?.title,model?.images,getRealPathFromURI(uri))

            }
        }

    model?.images?.let {
        Box(
            modifier = Modifier
                .padding(start = 20.dp, top = 32.dp, end = 20.dp)
                .fillMaxWidth(1f)
                .clip(
                    shape = RoundedCornerShape(size = 8.dp)
                )
                .background(Color.Blue)
        ) {
            Column(Modifier.padding(start = 8.dp, top = 8.dp, end = 8.dp)) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    modifier = Modifier.wrapContentSize()
                ) {
                    items(model.images!!.size) { index ->
                        ImageCard(model = model.images!![index])

                    }
                }


                    Button(
                        modifier = Modifier
                            .clip(
                                shape = RoundedCornerShape(size = 8.dp)
                            )
                            .padding(bottom = 34.dp),
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF892EFF)),

                        onClick = {

                            launcher.launch(viewModel.openFolderPicker())


                        }) {

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center,
                        ) {


                            Text(
                                text = "Extract to folder",
                                fontSize = 14.sp,
                                color = Color.White,
                                textAlign = TextAlign.Center

                            )



                    }

                }
            }




        }

    }

}

