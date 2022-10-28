package com.example.wavetable_synthesizer

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.wavetable_synthesizer.ui.theme.WavetablesythesizerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        setContent {
            WavetablesythesizerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    wavetableSynthesizerApp(Modifier)
                }
            }
        }
    }
}


@Composable
fun wavetableSynthesizerApp(modifier: Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        WavetableSelectionPanel(modifier)
        ControlsPanel(modifier)
    }
}

@Composable
fun WavetableSelectionPanel(modifier: Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight(0.5f)
            .border(BorderStroke(3.dp, Color.Black)),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(stringResource(R.string.wavetable))
            WaveTableSelectionButtons(modifier)
        }
    }
}

@Composable
fun WaveTableSelectionButtons(modifier: Modifier) {
    Row(modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
        ) {
        for(wavetable in arrayOf("Sine", "Triangle", "Square", "Saw")) {
            WaveTableButton(
                modifier = modifier,
                onClick= {},
                label = wavetable,
            )
        }
    }
}

@Composable
fun WaveTableButton (modifier: Modifier, onClick: ()-> Unit, label: String ){
    Button(modifier = modifier, onClick = onClick) {
        Text(label)
    }
}

@Composable
fun ControlsPanel(modifier: Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .border(BorderStroke(3.dp, Color.Black)),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = modifier
            .fillMaxHeight()
            .fillMaxWidth(0.7f)
            .border(BorderStroke(3.dp, Color.Black)),
        horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            PitchControl(modifier)
            PlayControl(modifier)
        }
        Column(modifier = modifier
            .fillMaxSize()
            .border(BorderStroke(3.dp, Color.Black)),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text("VolumeControl")
        }
    }
}

@Composable
fun PitchControl(modifier: Modifier) {
    val frequency = rememberSaveable { mutableStateOf(300F) }
    Text(stringResource(R.string.frequency))
    Slider(
        modifier = modifier,
        value = frequency.value,
        onValueChange = { frequency.value = it },
        valueRange = 40F..3000F
    )
    Text(stringResource(R.string.frequency_value, frequency.value))
}

@Composable
fun PlayControl(modifier: Modifier) {
    Text(text = "PlayControl")
}