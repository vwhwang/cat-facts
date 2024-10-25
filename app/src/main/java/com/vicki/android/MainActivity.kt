package com.vicki.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.accompanist.systemuicontroller.rememberSystemUiController

const val TAG = "MainActivity"

class MainActivity : ComponentActivity() {
    val catViewModel: CatViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val systemUiController = rememberSystemUiController()
            val darkTheme: Boolean = isSystemInDarkTheme()
            if (darkTheme) {
                systemUiController.setSystemBarsColor(
                    color = Color.Gray
                )
            } else {
                systemUiController.setSystemBarsColor(
                    color = Color.White
                )
            }
            Column(
                Modifier
                    .fillMaxHeight()
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(
                    Modifier
                        .padding(20.dp)
                ) {
                    Text(
                        textAlign = TextAlign.Center,
                        text = "Here's a random fact about cats!",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(50.dp)
                    )
                    val cat = catViewModel.catState.collectAsState()
                    Text(
                        textAlign = TextAlign.Center,
                        text = cat.value.fact,
                        style = MaterialTheme.typography.titleLarge
                    )
                }
                Row(
                    Modifier
                        .padding(start = 20.dp, end = 40.dp),
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.cat),
                        contentDescription = "I'm a cool cat!"
                    )
                    Image(
                        painter = painterResource(id = R.drawable.words),
                        contentDescription = "I'm a cool cat!"
                    )
                }

                Button(
                    onClick = { clickRandom() },
                    Modifier
                        .padding(20.dp),
                ) {
                    Text(
                        textAlign = TextAlign.Center,
                        text = "Give me another random fact!",
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            }
        }
    }

    private fun clickRandom() {
        catViewModel.fetchCatFact()
    }
}
