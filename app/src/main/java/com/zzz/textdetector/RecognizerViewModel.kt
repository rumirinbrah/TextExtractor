package com.zzz.textdetector

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.Task
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.Text
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions

class RecognizerViewModel : ViewModel() {
    private val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)

    fun extractTextFromImage(image : InputImage, onError : (Exception) ->Unit, onSuccess : (Task<Text>)->Unit ){
        recognizer.process(image)
            .addOnCompleteListener{
                onSuccess(it)
            }
            .addOnFailureListener {
                onError(it)
            }
    }

}