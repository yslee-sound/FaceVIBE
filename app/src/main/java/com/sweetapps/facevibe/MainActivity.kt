package com.sweetapps.facevibe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.sweetapps.facevibe.ui.theme.FaceVIBETheme
import com.sweetapps.facevibe.ui.AppNav

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FaceVIBETheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    AppNav()
                }
            }
        }
    }
}
