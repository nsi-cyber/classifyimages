package com.nsicyber.classifyimages

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.nsicyber.classifyimages.models.ClassifiedFolderModel
import com.nsicyber.classifyimages.models.FolderModel
import com.nsicyber.classifyimages.models.ImagesModel
import com.nsicyber.classifyimages.screens.ClassifyScreen
import com.nsicyber.classifyimages.screens.PreviewClassifiedScreen
import com.nsicyber.classifyimages.screens.PreviewScreen
import com.nsicyber.classifyimages.screens.StartScreen
import com.nsicyber.classifyimages.ui.theme.ClassifyImagesTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ClassifyImagesTheme {
                // A surface container using the 'background' color from the theme
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "start_screen") {

                    composable("start_screen") {
                        var context = LocalContext.current

                        StartScreen(
                            context,
                            navController = navController
                        )
                    }

                    composable(
                        "preview_screen?model={model}",
                        arguments = listOf(
                            navArgument("model") {
                                type = NavType.StringType
                            }
                        )
                    ) {


                        val model = remember {
                            it.arguments?.getString("model")
                        }
                        var context = LocalContext.current

                        PreviewScreen(context,
                            navController = navController,
                            model?.fromJson(FolderModel::class.java)
                        )
                    }

                    composable(
                        "classify_screen?model={model}",
                        arguments = listOf(
                            navArgument("model") {
                                type = NavType.StringType
                            }
                        )
                    ) {


                        val model = remember {
                            it.arguments?.getString("model")
                        }
                        var context = LocalContext.current

                        ClassifyScreen(context,
                            navController = navController,
                            model?.fromJson(ImagesModel::class.java)!!
                        )
                    }


                    composable(
                        "preview_classified?model={model}",
                        arguments = listOf(
                            navArgument("model") {
                                type = NavType.StringType
                            }
                        )
                    ) {


                        val model = remember {
                            it.arguments?.getString("model")
                        }
                        var context = LocalContext.current

                        PreviewClassifiedScreen(context,
                            navController = navController,
                            model?.fromJson(ClassifiedFolderModel::class.java)!!
                        )
                    }


                }

            }
        }
    }
}
