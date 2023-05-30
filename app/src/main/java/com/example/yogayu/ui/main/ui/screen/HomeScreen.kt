package com.example.yogayu.ui.main.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

@Preview(
    showBackground = true,
    device = Devices.PIXEL_3A,
    showSystemUi = true
)
@Composable
fun HomeScreen() {

    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "Home Screen",
            fontSize = 20.sp
        )
    }
}