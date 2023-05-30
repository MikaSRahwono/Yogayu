package com.example.yogayu.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.BottomNavigation
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import com.example.yogayu.R
import com.example.yogayu.ui.main.ui.navigation.BottomBarScreen
import com.example.yogayu.ui.main.ui.navigation.BottomNav
import com.example.yogayu.ui.main.ui.navigation.Screen
import com.example.yogayu.ui.main.ui.theme.YogayuTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            YogayuTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    BottomNav()
                }
            }
        }
    }
}

@Preview(
    device = Devices.PIXEL_3A,
    showSystemUi = true
)
@Composable
fun DefaultPreview() {
    YogayuTheme {
        BottomNav()
    }
}


