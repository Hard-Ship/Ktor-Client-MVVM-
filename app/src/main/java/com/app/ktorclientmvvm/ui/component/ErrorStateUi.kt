package com.app.ktorclientmvvm.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.app.ktorclientmvvm.ui.theme.Typography

@Composable
fun ErrorStateUi(message: String, modifier: Modifier = Modifier) {
    Box(modifier , contentAlignment = Alignment.Center) {
        Text(message, style = Typography.labelSmall, color = MaterialTheme.colorScheme.error)
    }
}