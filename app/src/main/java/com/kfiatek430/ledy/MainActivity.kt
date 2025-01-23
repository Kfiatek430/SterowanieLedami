package com.kfiatek430.ledy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.skydoves.colorpicker.compose.ColorEnvelope
import com.github.skydoves.colorpicker.compose.HsvColorPicker
import com.kfiatek430.ledy.ui.theme.SterowanieLedamiTheme
import com.github.skydoves.colorpicker.compose.rememberColorPickerController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            SterowanieLedamiTheme {
                val controller = rememberColorPickerController()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
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

                            PowerButton()
                        }

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
                                println(colorEnvelope.color)
                            }
                            )
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun PowerButton() {
        var isPowered by remember { mutableStateOf(false) }

        val handlePowerButtonClick: () -> Unit = {
            isPowered = !isPowered
        }

        val buttonColor = if (isPowered) Color.Red else Color.Blue

        Button(
            onClick = handlePowerButtonClick,
            modifier = Modifier.size(70.dp),
            shape = CircleShape,
            colors = ButtonDefaults.buttonColors(
                containerColor = buttonColor
            )
        ) {
            Image(
                painter = painterResource(id = R.drawable.baseline_power_settings_new_36),
                contentDescription = "Power button",
            )
        }
    }
}
