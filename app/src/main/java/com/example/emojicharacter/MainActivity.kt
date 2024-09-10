package com.example.emojicharacter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.emojicharacter.ui.theme.EmojiCharacterTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EmojiCharacterTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    GenerateEmoji(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun GenerateEmoji(modifier: Modifier = Modifier) {
    val focusManager = LocalFocusManager.current
    var name by remember { mutableStateOf("") }
    var showEmoji by remember { mutableStateOf(false) }
    var isFemale by remember { mutableStateOf(false) }
    var age by remember { mutableStateOf(0f) }
    var selectedSkinTone by remember { mutableStateOf("") }

    Column {
        TextField(
            value = name,
            onValueChange = {
                name = it
                showEmoji = false
            },
            label = { Text("Enter text") }
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            Switch(
                checked = isFemale,
                onCheckedChange = {
                    isFemale = it
                    showEmoji = false
                },
            )
            val genderLabel = if (isFemale) "Female" else "Male"
            Text("Gender: $genderLabel")
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Age: ${age.toInt()}")
            Slider(
                value = age,
                onValueChange = {
                    age = it
                    showEmoji = false
                },
                valueRange = 0f..80f
            )
        }
        Column {
            Text("Select Skin Tone:")
            listOf(
                "Dark",
                "Medium Dark",
                "Medium",
                "Medium Light",
                "Light",
                "Yellow"
            ).forEach { tone ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = selectedSkinTone == tone,
                        onCheckedChange = {
                            if (it) {
                                selectedSkinTone = tone
                                showEmoji = false
                            }
                        }
                    )
                    Text(tone)
                }
            }
        }
        Button(
            onClick = {
                focusManager.clearFocus()
                showEmoji = true
            },
            enabled = name.isNotEmpty() && selectedSkinTone.isNotEmpty() && age > 0
        ) {
            Text("Show Text")
        }
        if (showEmoji) {
            var emoji = ""
            if (isFemale) {
                emoji += when (age.toInt()) {
                    in 0..5 -> "\uD83D\uDC76"
                    in 6..20 -> "\uD83D\uDC67"
                    in 21..60 -> "\uD83D\uDC69"
                    in 61..80 -> "\uD83D\uDC75"
                    else -> ""
                }
            } else {
                emoji += when (age.toInt()) {
                    in 0..5 -> "\uD83D\uDC76"
                    in 6..20 -> "\uD83D\uDC66"
                    in 21..60 -> "\uD83D\uDC68"
                    in 61..80 -> "\uD83D\uDC74"
                    else -> ""
                }
            }

            when (selectedSkinTone) {
                "Dark" -> emoji += "\uD83C\uDFFF"
                "Medium Dark" -> emoji += "\uD83C\uDFFE"
                "Medium" -> emoji += "\uD83C\uDFFD"
                "Medium Light" -> emoji += "\uD83C\uDFFC"
                "Light" -> emoji += "\uD83C\uDFFB"
            }
            Text(
                text = emoji + name,
                fontSize = 50.sp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    EmojiCharacterTheme {
        GenerateEmoji()
    }
}