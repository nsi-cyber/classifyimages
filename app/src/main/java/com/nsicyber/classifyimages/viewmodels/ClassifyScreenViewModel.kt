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
    var imageList =
        mutableStateOf<ArrayList<ClassiedImageModel?>?>(null)

    var classifyTitles = mutableStateOf<HashMap<String, ArrayList<String>>>(hashMapOf())

    suspend fun classifyImages(context: Context, list: List<String>) {
        list?.forEach { uri ->
            getLabel(context, uri)
        }

    }

    fun labelToMap(list: List<String>, uri: String) {

        for (i in list) {
            val temp = classifyTitles.value[i]
            if (temp == null) {
                classifyTitles.value[i] = arrayListOf(uri)
            } else {
                classifyTitles.value[i]?.add(uri)
            }
        }
    }

    suspend fun getLabel(context: Context, uri: String) {
        if (classifyTitles.value == hashMapOf<String, ArrayList<String>>()) {
            imageClassifyRepository.labeler.process(
                InputImage.fromFilePath(
                    context, Uri.fromFile(File(uri))
                )
            )
                .addOnSuccessListener { labels ->

                    imageList.value?.add(ClassiedImageModel(labels.map { it.text }, uri))
                    labelToMap(labels.map { it.text }, uri)
                }
                .addOnFailureListener { e ->
                    // Task failed with an exception
                    // ...
                }

        }

    }


}



