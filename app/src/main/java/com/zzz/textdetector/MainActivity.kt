package com.zzz.textdetector

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import com.zzz.textdetector.ui.theme.TextDetectorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContent {
            TextDetectorTheme {
                Surface() {
                    val permissionLauncher = rememberLauncherForActivityResult(
                        contract = ActivityResultContracts.RequestPermission()
                    ) {}
                    if(!isCameraGranted(this)){
                        SideEffect {
                            permissionLauncher.launch(Manifest.permission.CAMERA)
                        }

                    }
                    HomePage()
                }
            }
        }
    }
}

private fun isCameraGranted(context : Context) : Boolean{
    val result = ContextCompat.checkSelfPermission(
        context,
        Manifest.permission.CAMERA
    )
    return (result == PackageManager.PERMISSION_GRANTED)
}



