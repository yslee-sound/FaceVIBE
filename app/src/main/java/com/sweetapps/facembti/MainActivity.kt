package com.sweetapps.facembti

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.sweetapps.facembti.ui.theme.FaceMBTITheme
import com.sweetapps.facembti.ui.AppNav

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FaceMBTITheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    AppNav()
                }
            }
        }
    }
}
