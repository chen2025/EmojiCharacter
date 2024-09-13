package com.example.emojicharacter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Centered TextField with curved outlines
        TextField(
            value = name,
            onValueChange = {
                name = it
                showEmoji = false
            },
            label = { Text("Enter text") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .background(Color(0xFFB3E5FC), RoundedCornerShape(10.dp))
                .padding(6.dp)
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(vertical = 1.dp)
        ) {
            Switch(
                checked = isFemale,
                onCheckedChange = {
                    isFemale = it
                    showEmoji = false
                },
            )
            val genderLabel = if (isFemale) "Female" else "Male"
            Text(" Gender: $genderLabel")
        }

        // Panel for age slider
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFB3E5FC))
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Age: ${age.toInt()}", fontWeight = FontWeight.Bold)
                Slider(
                    value = age,
                    onValueChange = {
                        age = it
                        showEmoji = false
                    },
                    valueRange = 0f..80f,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        // Panel for skin tone selection
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(6.dp),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFB3E5FC))
        ) {
            Column(
                modifier = Modifier.padding(10.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Text("Select Skin Tone:", fontWeight = FontWeight.Bold)
                listOf(
                    "Dark", "Medium Dark", "Medium", "Medium Light", "Light", "Yellow"
                ).forEach { tone ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(vertical = 2.dp)
                    ) {
                        Checkbox(
                            checked = selectedSkinTone == tone,
                            onCheckedChange = {
                                if (it) {
                                    focusManager.clearFocus()
                                    selectedSkinTone = tone
                                    showEmoji = false
                                }
                            },
                            colors = CheckboxDefaults.colors(
                                // Outer color when unchecked
                            )
                        )
                        Text(tone)
                    }
                }
            }
        }

        // Button
        Button(
            onClick = {
                focusManager.clearFocus()
                showEmoji = true
            },
            enabled = name.isNotEmpty() && selectedSkinTone.isNotEmpty() && age > 0,
            modifier = Modifier.padding(vertical = 4.dp)
        ) {
            Text("Show Text")
        }

        // Emoji Display Panel
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

            // Panel for name and emoji
            Card(
                modifier = Modifier
                    .size(200.dp) // Adjust the size to make the card square-shaped
                    .align(Alignment.CenterHorizontally) // Center the card horizontally
                    .padding(8.dp),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFB3E5FC))
            ) {
                Column(
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(text = name, fontSize = 30.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = emoji, fontSize = 65.sp)
                }
            }
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