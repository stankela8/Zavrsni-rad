package com.example.prvanogometnaliga.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.prvanogometnaliga.R
import com.example.prvanogometnaliga.viewmodel.AuthViewModel

@Composable
fun HomeScreen(navController: NavController, authViewModel: AuthViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val buttonModifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(Color(0xFFB71C1C), Color(0xFFD32F2F)),
                    startX = 0f,
                    endX = 1000f
                ),
                shape = RoundedCornerShape(10.dp)
            )

        val buttonColors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD32F2F))

        HomeScreenButton("Standings", R.drawable.standings_icon, navController, "standings", buttonModifier, buttonColors)
        HomeScreenButton("Matches", R.drawable.matches_icon, navController, "matches", buttonModifier, buttonColors)
        HomeScreenButton("Squads", R.drawable.lineup, navController, "squads", buttonModifier, buttonColors)
        HomeScreenButton("Highlights", R.drawable.highlights_icon, navController, "highlights", buttonModifier, buttonColors)
        HomeScreenButton("Statistics", R.drawable.statistics_icon, navController, "statistics", buttonModifier, buttonColors)
        HomeScreenButton("Transfers", R.drawable.transfer_icon, navController, "transfers", buttonModifier, buttonColors)

        Button(
            onClick = {
                authViewModel.signOut()
                navController.navigate("login") {
                    popUpTo("home") { inclusive = true }
                }
            },
            modifier = buttonModifier,
            colors = buttonColors
        ) {
            Image(
                painter = painterResource(id = R.drawable.logout),
                contentDescription = "Log Out",
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text("Log Out", color = Color.White, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
        }
    }
}

@Composable
fun HomeScreenButton(text: String, iconResId: Int, navController: NavController, route: String, modifier: Modifier, colors: ButtonColors) {
    Button(
        onClick = { navController.navigate(route) },
        modifier = modifier,
        colors = colors
    ) {
        Image(
            painter = painterResource(id = iconResId),
            contentDescription = text,
            modifier = Modifier.size(24.dp),
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(text, color = Color.White, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold, textAlign = TextAlign.Left)
    }
}
