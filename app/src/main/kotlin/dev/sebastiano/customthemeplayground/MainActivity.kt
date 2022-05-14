package dev.sebastiano.customthemeplayground

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import dev.sebastiano.customthemeplayground.ui.theme.MyDesignSystemTheme
import dev.sebastiano.customthemeplayground.ui.theme.MyTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyDesignSystemTheme {
                Box(modifier = Modifier.fillMaxSize()) {
                    Greeting("Android", Modifier.align(Alignment.Center))
                }
            }
        }
    }
}

@Composable
fun Greeting(
    name: String,
    modifier: Modifier = Modifier
) {
    BasicText(text = "Hello $name!", modifier = modifier.background(MyTheme.colors.control))
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyDesignSystemTheme {
        Greeting("Android")
    }
}
