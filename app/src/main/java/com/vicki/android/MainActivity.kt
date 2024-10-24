package com.vicki.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

const val TAG = "MainActivity"
class MainActivity : ComponentActivity() {
    val catViewModel: CatViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LazyColumn(
                Modifier
                    .fillMaxSize()
                    .padding(20.dp)
            ) {
                item() {
                    val cat = catViewModel.catState.collectAsState()
                    Text(
                        textAlign = TextAlign.Center,
                        text = cat.value.fact,
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            }
        }
    }
}
