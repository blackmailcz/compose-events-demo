package net.nooii.eventsdemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import net.nooii.eventsdemo.ui.theme.EventsDemoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EventsDemoTheme {
                val viewModel = viewModel<MainViewModel>()
                var text by remember { mutableStateOf("") }

                EventEffect(viewModel.oneTimeEvent) {
                    text = "OneTimeEvent (${System.currentTimeMillis()})"
                }

                EventEffect(viewModel.oneTimeDataEvent) { data ->
                    text = "OneTimeDataEvent - $data - (${System.currentTimeMillis()})"
                }

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column {
                        Button(onClick = { viewModel.onButton1Clicked() }) {
                            Text(text = "OneTimeEvent")
                        }

                        Button(onClick = { viewModel.onButton2Clicked() }) {
                            Text(text = "OneTimeDataEvent")
                        }

                        Text(text = text)
                    }
                }
            }
        }
    }
}