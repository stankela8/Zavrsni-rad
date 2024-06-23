// SquadDetailsScreen.kt
package com.example.prvanogometnaliga.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import com.example.prvanogometnaliga.network.models.PlayerInfo
import com.example.prvanogometnaliga.viewmodel.FootballLeagueViewModel

@Composable
fun SquadDetailsScreen(teamId: Int, footballLeagueViewModel: FootballLeagueViewModel = viewModel()) {
    val squadResponse by footballLeagueViewModel.squadResponse.collectAsState()
    val validCharactersRegex = Regex(pattern = "^[\\p{L} .'-]+$")

    LaunchedEffect(teamId) {
        footballLeagueViewModel.fetchSquad(teamId)
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = "Squad",
                style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onBackground)
            )
            Spacer(modifier = Modifier.height(16.dp))
            squadResponse?.let { squadResponse ->
                LazyColumn {
                    items(squadResponse.response.firstOrNull()?.players?.filter { player ->
                        validCharactersRegex.matches(player.name)
                    } ?: emptyList()) { player ->
                        PlayerItem(player = player)
                    }
                }
            }
        }
    }
}

@Composable
fun PlayerItem(player: PlayerInfo) {
    val backgroundColor = when (player.position) {
        "Goalkeeper" -> Color(0xFFFFFF99) // Svijetlo žuta
        "Defender" -> Color(0xFF99FF99) // Svijetlo zelena
        "Midfielder" -> Color(0xFFFF9999) // Svijetlo crvena
        "Attacker" -> Color(0xFF99CCFF) // Svijetlo plava
        else -> Color.White
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .background(backgroundColor)
    ) {
        Image(
            painter = rememberImagePainter(data = player.photo),
            contentDescription = null,
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(
                text = player.name,
                style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onBackground))
            Text(
                text = player.position,
                style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onBackground)
            )
        }
    }
}
