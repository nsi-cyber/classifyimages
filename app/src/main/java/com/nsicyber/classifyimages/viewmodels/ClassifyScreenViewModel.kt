package com.nsicyber.classifyimages.viewmodels

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.mlkit.vision.common.InputImage
import com.nsicyber.classifyimages.models.ClassiedImageModel
import com.nsicyber.classifyimages.repositories.ImageClassifyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import java.io.File
import java.io.IOException
import javax.inject.Inject


@HiltViewModel
class ClassifyScreenViewModel @Inject constructor(private var imageClassifyRepository: ImageClassifyRepository) :
    ViewModel() {
    val imageList =
        mutableStateOf<ArrayList<ClassiedImageModel?>?>(null)


 suspend fun classifyImages(context: Context, list:List<String>) {
     list?.forEach{
         uri->
         getLabel(context, uri)
     }

 }

    suspend fun getLabel(context: Context, uri: String) {

        imageClassifyRepository.labeler.process(InputImage.fromFilePath(context, Uri.fromFile(File(uri))
        ))
            .addOnSuccessListener { labels ->

                imageList.value?.add(ClassiedImageModel(labels.map { it.text },uri))
            }
            .addOnFailureListener { e ->
                // Task failed with an exception
                // ...
            }

    }


}



