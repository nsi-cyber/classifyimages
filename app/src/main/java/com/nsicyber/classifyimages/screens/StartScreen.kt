package com.nsicyber.classifyimages.screens

import android.app.Activity
import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MonotonicFrameClock
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.VerticalAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.nsicyber.classifyimages.R
import com.nsicyber.classifyimages.getLastDirectoryName
import com.nsicyber.classifyimages.getRealPathFromURI
import com.nsicyber.classifyimages.models.FolderModel
import com.nsicyber.classifyimages.toJson
import com.nsicyber.classifyimages.viewmodels.StartScreenViewModel
import dagger.hilt.android.AndroidEntryPoint

@Composable
fun StartScreen(
    context: Context,
    navController: NavController
) {
    var scope = rememberCoroutineScope()
    val mCheckedState = remember { mutableStateOf(false) }

    var viewModel = hiltViewModel<StartScreenViewModel>()

    val interactionSource = remember { MutableInteractionSource() }

    var launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val uri: Uri? = result.data?.data
                viewModel.selectedFolder.value = getRealPathFromURI(uri)
            }
        }




    Box(
        modifier = Modifier
            .padding(start = 20.dp, top = 32.dp, end = 20.dp)
            .fillMaxWidth(1f)
            .clip(
                shape = RoundedCornerShape(size = 8.dp)
            )
            .background(Color.Blue)
    ) {
        Column(Modifier.padding(start = 20.dp, top = 32.dp, end = 20.dp, bottom = 32.dp)) {

            Text(
                text = "Classify Images",
                fontSize = 32.sp,
                color = Color.White,
                textAlign = TextAlign.Center

            )
            Spacer(modifier = Modifier.height(24.dp))

            Column() {
                Text(text = "Choose Directory")
                Spacer(modifier = Modifier.height(8.dp))
                Box(
                    Modifier
                        .border(
                            width = 1.dp,
                            color = Color(0xFFD3D3D3),
                            shape = RoundedCornerShape(size = 8.dp)
                        )
                        .fillMaxWidth(1f)

                        .height(56.dp)
                        .background(
                            color = Color(0xFFFAFAFA),
                            shape = RoundedCornerShape(size = 8.dp)
                        )
                        .padding(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 16.dp)
                        .clickable(
                            interactionSource = interactionSource,
                            indication = null
                        ) {
//folder picker
                            launcher.launch(viewModel.openFolderPicker())

                        }
                ) {
                    Row() {
                        Image(
                            modifier = Modifier
                                .height(24.dp)
                                .padding(end = 16.dp),
                            painter = painterResource(id = R.drawable.folder_icon),
                            contentDescription = "image description",
                            contentScale = ContentScale.None
                        )
                        Text(
                            modifier = Modifier,
                            text =
                            getLastDirectoryName(
                                viewModel.selectedFolder.value.toString()
                            ),
                            fontSize = 14.sp,
                            color = Color(0xFF1A0D0D)
                        )

                    }


                }
            }
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {

                Switch(checked = mCheckedState.value, onCheckedChange = {
                    mCheckedState.value = it
                    viewModel.getChild.value = it
                })
                Spacer(modifier = Modifier.width(8.dp))

                Text(text = "Use child folders too")


            }

            Spacer(modifier = Modifier.height(24.dp))
            Button(modifier = Modifier
                .height(56.dp)
                .fillMaxWidth(1f)
                .clip(
                    shape = RoundedCornerShape(size = 8.dp)
                ),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF892EFF)),

                onClick = {

                    if (!viewModel.selectedFolder.value.isNullOrEmpty()) {

                        var model =
                            FolderModel(viewModel.selectedFolder.value, viewModel.getChild.value)
                        navController.navigate("preview_screen?model=${model.toJson()}")


                    } else
                        Toast.makeText(context, "Select a folder to classify", Toast.LENGTH_SHORT)
                            .show()


                }) {

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxSize()
                ) {


                    Text(
                        text = "Classify the folder",
                        fontSize = 14.sp,
                        color = Color.White,
                        textAlign = TextAlign.Center

                    )
                }


            }

        }

    }


}