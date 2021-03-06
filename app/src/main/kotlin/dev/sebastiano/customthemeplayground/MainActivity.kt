package dev.sebastiano.customthemeplayground

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.text
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dev.sebastiano.customthemeplayground.ui.theme.MyDesignSystemTheme
import dev.sebastiano.customthemeplayground.ui.theme.MyTheme
import dev.sebastiano.customthemeplayground.widgets.Button
import dev.sebastiano.customthemeplayground.widgets.ChoiceButton
import dev.sebastiano.customthemeplayground.widgets.ImageButton
import dev.sebastiano.customthemeplayground.widgets.ProgressBar
import dev.sebastiano.customthemeplayground.widgets.Text

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            val systemUiController = rememberSystemUiController()
            val useDarkIcons = !isSystemInDarkTheme()
            SideEffect {
                systemUiController.setSystemBarsColor(Color.Transparent, darkIcons = useDarkIcons)
            }

            MyDesignSystemTheme {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .safeDrawingPadding()
                        .background(MyTheme.colors.windowBackground)
                ) {
                    MyContent(Modifier.align(Alignment.Center))
                }
            }
        }
    }
}

@Composable
fun MyContent(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.padding(16.dp)
    ) {
        var progress by remember { mutableStateOf(0f) }
        TopBar(progress)

        Spacer(Modifier.height(48.dp))

        Text(text = "Select the correct characters for ??????????????????", fontSize = 24.sp, fontWeight = FontWeight.ExtraBold)

        Spacer(Modifier.height(48.dp))

        var currentChoice by remember { mutableStateOf<ChoiceItem>(ChoiceItem.None) }
        Choices(
            currentChoice = currentChoice,
            choices = listOf("?????????", "?????????", "?????????", "?????????"),
            onChoiceSelected = { currentChoice = it },
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        )

        Spacer(Modifier.height(48.dp))

        Button(
            onClick = {
                progress = (if (progress < 1f) (progress + .1f).coerceAtMost(1f) else 0f)
                currentChoice = ChoiceItem.None
            },
            Modifier.fillMaxWidth(),
            enabled = currentChoice !is ChoiceItem.None
        ) {
            Text(text = "Hello, button!")
        }
    }
}

@Composable
private fun Choices(
    currentChoice: ChoiceItem,
    choices: List<String>,
    onChoiceSelected: (ChoiceItem.Position) -> Unit,
    modifier: Modifier = Modifier,
) {
    check(choices.size == 4) { "This composable only supports exactly 4 choices" }

    val choiceIndex = (currentChoice as? ChoiceItem.Position)?.index ?: -1
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Row(Modifier
            .fillMaxWidth()
            .weight(1f), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            ChoiceButton(
                selected = choiceIndex == 0,
                onClick = { onChoiceSelected(ChoiceItem.Position(0)) },
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
            ) {
                Text(text = choices[0], fontSize = 36.sp, fontWeight = FontWeight.Bold)
            }
            ChoiceButton(
                selected = choiceIndex == 1,
                onClick = { onChoiceSelected(ChoiceItem.Position(1)) },
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
            ) {
                Text(text = choices[1], fontSize = 36.sp, fontWeight = FontWeight.Bold)
            }
        }
        Row(Modifier
            .fillMaxWidth()
            .weight(1f), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            ChoiceButton(
                selected = choiceIndex == 2,
                onClick = { onChoiceSelected(ChoiceItem.Position(2)) },
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
            ) {
                Text(text = choices[2], fontSize = 36.sp, fontWeight = FontWeight.Bold)
            }
            ChoiceButton(
                selected = choiceIndex == 3,
                onClick = { onChoiceSelected(ChoiceItem.Position(3)) },
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
            ) {
                Text(text = choices[3], fontSize = 36.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

private sealed class ChoiceItem {

    object None : ChoiceItem()

    data class Position(val index: Int) : ChoiceItem()
}

@Composable
private fun TopBar(progress: Float) {
    Row(
        Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val context = LocalContext.current
        ImageButton(
            painter = painterResource(R.drawable.ic_cog),
            contentDescription = stringResource(R.string.settings_content_description),
            onClick = { Toast.makeText(context, R.string.settings, Toast.LENGTH_SHORT).show() }
        )

        ProgressBar(
            progress = progress,
            modifier = Modifier
                .weight(1f)
                .height(16.dp)
        )

        ImageWithText(painterResource(R.drawable.ic_lightning), "0", Color(0xFFFFC800))

        ImageWithText(painterResource(R.drawable.ic_heart), "???", Color(0xFF2B70C9), imageSize = 26.dp)
    }
}

@Composable
fun ImageWithText(
    painter: Painter,
    labelText: String,
    textColor: Color,
    modifier: Modifier = Modifier,
    imageSize: Dp = 32.dp,
    textSize: TextUnit = 17.sp,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier.semantics(mergeDescendants = true) {
            text = AnnotatedString(labelText)
            role = Role.Image
        }
    ) {
        Image(painter = painter, contentDescription = null, modifier = Modifier.size(imageSize))
        Text(text = labelText, color = textColor, fontSize = textSize, fontWeight = FontWeight.ExtraBold)
    }
}

@Preview(name = "Main Activity", showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun DefaultPreview() {
    MyDesignSystemTheme {
        MyContent(Modifier.background(MyTheme.colors.windowBackground))
    }
}
