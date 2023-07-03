package com.nsicyber.classifyimages

import android.net.Uri
import android.os.Environment
import com.google.gson.Gson
import java.io.File
import java.util.Objects


fun getRealPathFromURI(uri: Uri?): String? {
    val paths: List<String>? = Objects.requireNonNull(uri?.path)?.split(":")

    return Environment.getExternalStorageDirectory().toString() +
            if (paths?.size!! > 1) File.separator + (paths.get(1)) else ""
}

fun getLastDirectoryName(uri: String): String {

    // Uri'yi "/" karakterine göre böleriz
    val splittedUri = removePrimary3A(uri).split("/")

    // Bölünen dizinin son elemanını döndürürüz
    return splittedUri.last()
}

fun removePrimary3A(input: String): String {
    val targetString = "primary%3A"

    if (input.contains(targetString)) {
        val startIndex = input.indexOf(targetString)
        val endIndex = startIndex + targetString.length

        return input.removeRange(startIndex, endIndex)
    }

    return input
}


fun <T> T.toJson(): String? {
    return Gson().toJson(this)
}
fun <T> String.fromJson(type: Class<T>): T? {
    try {
        return Gson().fromJson(this, type);
    } catch (e: Exception) {
        print(e)
    }
    return null;
}