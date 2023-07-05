package com.nsicyber.classifyimages.viewmodels

import android.content.Intent
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import javax.inject.Inject

@HiltViewModel
class PreviewClassifiedScreenViewModel @Inject constructor() : ViewModel() {
    val selectedFolder =
        mutableStateOf<String?>(null)
    val getChild =
        mutableStateOf<Boolean?>(false)



    fun openFolderPicker(): Intent {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT_TREE)
        intent.addCategory(Intent.CATEGORY_DEFAULT)
        return intent
    }


    fun copyImagesToFolder(title: String?, images: List<String>?, uri: String?) {
        val destinationPath = Paths.get("$uri/$title")
        if (!Files.exists(destinationPath)) {
            try {
                Files.createDirectories(destinationPath)
                println("'$title' klasörü oluşturuldu.")
            } catch (e: Exception) {
                println("Klasör oluşturulurken bir hata oluştu: ${e.message}")
                return
            }
        }

        if (images != null) {
            for (imagePath in images) {
                val sourceFile = File(imagePath)
                if (sourceFile.exists() && sourceFile.isFile) {
                    val destinationFile = File(destinationPath.toFile(), sourceFile.name)
                    try {
                        sourceFile.copyTo(destinationFile, true)
                        println("'$sourceFile' dosyası '$title' klasörüne kopyalandı.")
                    } catch (e: Exception) {
                        println("Dosya kopyalanırken bir hata oluştu: ${e.message}")
                    }
                } else {
                    println("'$imagePath' geçerli bir dosya yolu değil veya dosya mevcut değil.")
                }
            }
        }
    }



}