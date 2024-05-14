package com.example.authtest

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    user: String? = "User"
){
    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
        ){
            Text(text = "Welcome $user", color = Color.Black)
    }
}