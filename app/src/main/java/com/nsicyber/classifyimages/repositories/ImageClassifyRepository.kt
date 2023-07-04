package com.nsicyber.classifyimages.repositories

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.util.Log
import com.google.mlkit.vision.label.ImageLabeler
import com.google.mlkit.vision.label.ImageLabeling
import com.google.mlkit.vision.label.defaults.ImageLabelerOptions
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class ImageClassifyRepository @Inject constructor() {

   lateinit var labeler: ImageLabeler
 init {
     labeler = ImageLabeling.getClient(ImageLabelerOptions.DEFAULT_OPTIONS)
 }


}