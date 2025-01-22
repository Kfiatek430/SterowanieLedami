package com.kfiatek430.ledy

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.github.skydoves.colorpicker.compose.ColorEnvelope
import com.github.skydoves.colorpicker.compose.HsvColorPicker
import com.kfiatek430.ledy.ui.theme.SterowanieLedamiTheme
import com.github.skydoves.colorpicker.compose.rememberColorPickerController

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            SterowanieLedamiTheme {
                val controller = rememberColorPickerController()
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    HsvColorPicker(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(450.dp)
                            .padding(20.dp),
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
