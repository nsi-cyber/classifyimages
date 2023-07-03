package com.nsicyber.classifyimages.viewmodels

import android.content.Intent
import android.os.Environment
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.io.File
import javax.inject.Inject



@HiltViewModel
class StartScreenViewModel @Inject constructor() : ViewModel() {
    val selectedFolder =
        mutableStateOf<String?>(null)
    val getChild =
        mutableStateOf<Boolean?>(false)



    fun openFolderPicker(): Intent {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT_TREE)
        intent.addCategory(Intent.CATEGORY_DEFAULT)
        return intent
    }


}