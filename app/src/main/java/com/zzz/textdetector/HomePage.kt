package com.zzz.textdetector

import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.FileProvider
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.mlkit.vision.common.InputImage
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Objects

@Composable
fun HomePage(
    modifier: Modifier = Modifier,
    recognizerViewModel: RecognizerViewModel = RecognizerViewModel(),
) {

    val context = LocalContext.current
    val file = context.getImageFile()
    val uri = FileProvider.getUriForFile(
        Objects.requireNonNull(context),
        context.packageName + ".provider",file
    )

    var image by remember { mutableStateOf<InputImage?>(null) }


    var currentImage by remember{ mutableStateOf<Uri?>(null) }
    var extractedText by remember{ mutableStateOf<String>("") }

    val galleryImagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) {imageUri->
        imageUri?.let {
            currentImage = it
        }
    }

    val cameraImagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) {
        currentImage = uri
    }

    LaunchedEffect(currentImage) {
        currentImage?.let {
            image = InputImage.fromFilePath(context,it)
        }
    }

    Column (
        modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ){
        Button(
            onClick = {
                cameraImagePicker.launch(uri)
            }
        ) {
            Text("Camera")
        }
        Button(
            onClick = {
                galleryImagePicker.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            }
        ) {
            Text("Gallery")
        }
        currentImage?.let {
            AsyncImage(
                modifier = Modifier
                    .size(400.dp),
                model = ImageRequest.Builder(context)
                    .data(currentImage)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
        }

        Spacer(Modifier.height(20.dp))
        Text("Extracted Text Is")
        AnimatedContent(
            extractedText,
            label = "",
            transitionSpec = {
                slideInVertically(animationSpec = tween(1000)) + fadeIn() togetherWith slideOutVertically()

            }
        ) {text->
            Text(text, fontSize = 20.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
        }

        image?.let {

            recognizerViewModel.extractTextFromImage(
                it,
                onSuccess = {task->
                    extractedText = task.result.text
                },
                onError = {exception->
                    println(exception.printStackTrace())
                }
            )


        }
    }
}
fun Context.getImageFile() : File{
    val time = SimpleDateFormat("yyyy MM dd").format(Date())
    val fileName = "JPEG$time"
    val image = File.createTempFile(
        fileName,
        ".jpg",
        externalCacheDir
    )
    return image
}


