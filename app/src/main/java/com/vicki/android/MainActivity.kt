package com.vicki.android

import android.annotation.SuppressLint
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.launch

const val TAG = "MainActivity"

class MainActivity : ComponentActivity() {
    val catViewModel: CatViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HomeScreen()
        }
    }

    private fun clickRandom() {
        catViewModel.fetchCatFact()
    }

    private fun clickByron() {
        catViewModel.callLordByron()
    }

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun HomeScreen() {
        val snackbarHostState = remember { SnackbarHostState() }
        val scope = rememberCoroutineScope()

        Scaffold(
            snackbarHost = {
                SnackbarHost(snackbarHostState) {
                    Snackbar(
                        actionContentColor = Color.Red,
                        snackbarData = it
                    )
                }
            },
            content = {
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
                val word = catViewModel.wordState.collectAsState()

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
                        ContentBody()
                    }
                    CatFactButton()
                    Button(
                        onClick = {
                            clickByron()
                            scope.launch {
                                snackbarHostState.showSnackbar(
                                    message = word.value,
                                    actionLabel = "dismiss",
                                    duration = SnackbarDuration.Indefinite
                                )
                            }
                        },
                        Modifier
                            .padding(20.dp)
                            .padding(bottom = 50.dp),
                    ) {
                        Text(
                            textAlign = TextAlign.Center,
                            text = "Hi Lord Byron!",
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                }
            }
        )
    }

    @Composable
    fun CatFactButton() {
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

    @Composable
    fun ContentBody() {
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
    }
}
