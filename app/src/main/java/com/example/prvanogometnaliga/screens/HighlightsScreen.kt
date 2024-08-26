package com.example.prvanogometnaliga.screens

import android.annotation.SuppressLint
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay


@Composable
fun HighlightsScreen() {
    var showSplash by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        delay(7000)
        showSplash = false
    }

    val videoUrls = listOf(
        "https://www.youtube.com/watch?v=PRoTjGG9PiA&t=138s",
        "https://www.youtube.com/watch?v=Lk8gGeGhRrM&t=4s",
        "https://www.youtube.com/watch?v=pD_o0QjD8Ok&t=17s",
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        items(videoUrls) { videoUrl ->
            YouTubeVideo(videoUrl = videoUrl)
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
    Box(modifier = Modifier.fillMaxSize()) {
    if (showSplash) {
        SplashMessage(
            message = "ENG: On this screen you can see highlights of 3 most important matches between the two title contenders in season 2023./24.\n" +
                    "All content belongs to the YouTube channel MaxSport.\n\n" +
                    "CRO: Na ovom ekranu možete vidjeti 3 sažetka najbitnijih utakmica između dva favorita za osvajanje prvog mjesta.\n" +
                    "Sav sadržaj priprada Youtube kanalu MaxSport.",
            modifier = Modifier.align(Alignment.Center)
        )
    }
}
}

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun YouTubeVideo(videoUrl: String) {
    val context = LocalContext.current
    AndroidView(factory = {
        WebView(context).apply {
            settings.javaScriptEnabled = true
            settings.pluginState = WebSettings.PluginState.ON
            webViewClient = WebViewClient()
            loadUrl(videoUrl)
        }
    }, modifier = Modifier
        .fillMaxWidth()
        .height(200.dp)
        .padding(16.dp))
}

