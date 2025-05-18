package com.srnyndrs.next_stop.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.srnyndrs.next_stop.app.presentation.NextStopApp
import com.srnyndrs.next_stop.app.presentation.ui.theme.NextStopTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NextStopTheme {
                NextStopApp()
            }
        }
    }
}