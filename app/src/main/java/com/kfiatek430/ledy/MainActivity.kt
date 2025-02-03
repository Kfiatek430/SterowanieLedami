package com.kfiatek430.ledy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.skydoves.colorpicker.compose.ColorEnvelope
import com.github.skydoves.colorpicker.compose.HsvColorPicker
import com.github.skydoves.colorpicker.compose.rememberColorPickerController
import com.kfiatek430.ledy.ui.theme.SterowanieLedamiTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            SterowanieLedamiTheme {
                MainApp()
            }
        }
    }

    @Composable
    fun MainApp() {
        var isPowered by remember { mutableStateOf(false) }
        var color by remember { mutableStateOf(Color.White) }
        var brightness by remember { mutableFloatStateOf(0f) }

        val powerSwitchChange: (Boolean) -> Unit = {
            isPowered = !isPowered
        }

        val colorChange: (Color) -> Unit = {
            color = it
            println("New color: $color")
        }

        val brightnessChange: (Float) -> Unit = {
            brightness = it;
        }

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            containerColor = Color(0xFF2B324C),
            contentColor = Color.White
        ) { innerPadding ->
            Column(
                modifier = Modifier.padding(top = innerPadding.calculateTopPadding() + 16.dp),
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Sterowanie Ledami",
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold
                    )

                    PowerSwitch(isPowered, powerSwitchChange)
                }

                if(isPowered) {
                    Controls(colorChange)
                    GridOfButtons(colorChange)
                    ColorPreviewBox(color)
                    BrightnessSlider(brightness, brightnessChange)
                }
            }
        }
    }

    @Composable
    fun PowerSwitch(isPowered: Boolean, onChange: (Boolean) -> Unit) {
        Switch(
            checked = isPowered,
            onCheckedChange = onChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                uncheckedThumbColor = Color.White,
                checkedTrackColor = Color(0xFF32B4E6),
                uncheckedTrackColor = Color(0xFF171D31)
            )
        )
    }

    @Composable
    fun Controls(colorChange: (Color) -> Unit) {
        val controller = rememberColorPickerController()

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            HsvColorPicker(
                modifier = Modifier
                    .width(300.dp)
                    .height(300.dp),
                controller = controller,
                onColorChanged = { colorEnvelope: ColorEnvelope ->
                    colorChange(colorEnvelope.color)
                }
            )
        }
    }

    @Composable
    fun GridOfButtons(colorChange: (Color) -> Unit) {
        val colors = listOf(
            Color(0xFF990F02), Color(0xFFD21404), Color(0xFFFC6A03), Color(0xFFEC9706), Color(0xFFFFD300),
            Color(0xFF3BB143), Color(0xFF00A86B), Color(0xFF0492C2), Color(0xFF2832C2), Color(0xFF8F00FF),
            Color(0xFFE4A0F7), Color(0xFFFF1694), Color(0xFFF5DEB3), Color(0xFFFEFCF7), Color(0xFF777B7E)
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(5),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 30.dp, start = 10.dp, end = 10.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Color(0xFF171D31)),
            contentPadding = PaddingValues(10.dp),
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
            items(colors) { color ->
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .shadow(8.dp, RoundedCornerShape(12.dp))
                    .clip(RoundedCornerShape(12.dp))
                    .background(color)
                    .clickable { colorChange(color) },
                )
            }
        }
    }

    @Composable
    fun ColorPreviewBox(color: Color) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .padding(top = 30.dp, start = 50.dp, end = 50.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(color)
        ) {}
    }

    @Composable
    fun BrightnessSlider(value: Float, brightnessChange: (Float) -> Unit) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 30.dp, start = 10.dp, end = 10.dp)
        ) {
            Slider(
                value = value,
                onValueChange = { newValue -> brightnessChange(newValue) },
                valueRange = 0f..100f,
                modifier = Modifier
                    .weight(1f)
                    .height(16.dp),
                colors = SliderDefaults.colors(
                    inactiveTrackColor = Color.LightGray,
                    activeTrackColor = Color(0xFF171D31),
                    thumbColor = Color.Gray
                )
            )

            Spacer(modifier = Modifier.width(10.dp))

            Image(
                painter = painterResource(id = R.drawable.baseline_wb_sunny_24),
                contentDescription = "Ikona słońca",
                modifier = Modifier.size(24.dp)
            )
        }
    }
}
