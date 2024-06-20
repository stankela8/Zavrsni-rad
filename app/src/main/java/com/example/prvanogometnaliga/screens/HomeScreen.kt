package com.example.prvanogometnaliga.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
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
        Button(onClick = { navController.navigate("standings") }, modifier = Modifier.fillMaxWidth().padding(8.dp)) {
            Text("Standings")
        }
        Button(onClick = { navController.navigate("matches") }, modifier = Modifier.fillMaxWidth().padding(8.dp)) {
            Text("Matches")
        }
        Button(onClick = { navController.navigate("news") }, modifier = Modifier.fillMaxWidth().padding(8.dp)) {
            Text("News")
        }
        Button(onClick = { navController.navigate("highlights") }, modifier = Modifier.fillMaxWidth().padding(8.dp)) {
            Text("Highlights")
        }
        Button(onClick = { navController.navigate("statistics") }, modifier = Modifier.fillMaxWidth().padding(8.dp)) {
            Text("Statistics")
        }
        Button(onClick = { navController.navigate("transfers") }, modifier = Modifier.fillMaxWidth().padding(8.dp)) {
            Text("Transfers")
        }
        Button(
            onClick = {
                authViewModel.signOut()
                navController.navigate("auth") {
                    popUpTo("home") { inclusive = true }
                }
            },
            modifier = Modifier.fillMaxWidth().padding(8.dp)
        ) {
            Text("Log Out")
        }
    }
}
