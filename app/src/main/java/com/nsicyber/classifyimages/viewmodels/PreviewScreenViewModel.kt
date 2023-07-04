package com.nsicyber.classifyimages.viewmodels

import android.content.Intent
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.io.File
import javax.inject.Inject


@HiltViewModel
class PreviewScreenViewModel @Inject constructor() : ViewModel() {
    val imageList =
        mutableStateOf<List<String>?>(null)



    fun getImageListFromFolder(folderUri:String?,includeSubdirectories:Boolean?) {
        val photoExtensions = listOf(".jpg", ".jpeg", ".png", ".gif") // Fotoğraf dosya uzantıları
        val photoFilePaths = mutableListOf<String>() // Fotoğraf dosya yollarını içerecek liste

        val directory = File(folderUri+"/")
        if (directory.isDirectory) {
            // Ana klasördeki fotoğraf dosyalarını ekle
            val files = directory.listFiles()
            println(files)
            files?.forEach { file ->
                if (file.isFile && photoExtensions.any { file.name.endsWith(it) }) {
                    photoFilePaths.add(file.absolutePath)
                }
            }

            if (includeSubdirectories == true) {
                // Alt klasörlerdeki fotoğraf dosyalarını ekle
                val subdirectories = files?.filter { it.isDirectory }
                subdirectories?.forEach { subdirectory ->
                    val subFiles = subdirectory.listFiles()
                    subFiles?.forEach { file ->
                        if (file.isFile && photoExtensions.any { file.name.endsWith(it) }) {
                            photoFilePaths.add(file.absolutePath)
                        }
                    }
                }
            }
        }

        imageList.value=photoFilePaths.toList()



    }


}