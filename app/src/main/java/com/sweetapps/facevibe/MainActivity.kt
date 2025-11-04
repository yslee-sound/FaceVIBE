package com.sweetapps.facevibe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.sweetapps.facevibe.ui.theme.FaceVIBETheme
import com.sweetapps.facevibe.ui.AppNav

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        // 스플래시 화면 설치 (setContentView 호출 전에 실행)
        installSplashScreen()

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
