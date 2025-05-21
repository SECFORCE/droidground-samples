package com.droidground.hiddenactivity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.droidground.hiddenactivity.ui.theme.HiddenActivityTheme

val flag = "DROIDGROUND_FLAG_PLACEHOLDER"

class HiddenActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HiddenActivityTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Flag(
                        name = flag,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Flag(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "The flag is $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun FlagPreview() {
    HiddenActivityTheme {
        Flag(flag)
    }
}