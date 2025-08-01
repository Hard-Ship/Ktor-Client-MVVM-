package com.app.ktorclientmvvm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.app.ktorclientmvvm.ui.screen.TodoScreen
import com.app.ktorclientmvvm.ui.theme.KtorClientMVVMTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KtorClientMVVMTheme {
                TodoScreen()
            }
        }
    }
}