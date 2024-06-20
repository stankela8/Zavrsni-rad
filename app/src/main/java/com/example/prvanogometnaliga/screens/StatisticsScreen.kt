package com.example.prvanogometnaliga.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import com.example.prvanogometnaliga.model.PlayerResponse
import com.example.prvanogometnaliga.viewmodel.FootballLeagueViewModel

@Composable
fun StatisticsScreen(footballLeagueViewModel: FootballLeagueViewModel = viewModel()) {
    var selectedCategory by remember { mutableStateOf("Top Scorers") }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            TextButton(onClick = { selectedCategory = "Top Scorers" }) {
                Text("Top Scorers")
            }
            TextButton(onClick = { selectedCategory = "Top Yellow Cards" }) {
                Text("Top Yellow Cards")
            }
        }

        when (selectedCategory) {
            "Top Scorers" -> TopScorersScreen(footballLeagueViewModel)
            "Top Yellow Cards" -> TopYellowCardsScreen(footballLeagueViewModel)
        }
    }
}

@Composable
fun TopScorersScreen(footballLeagueViewModel: FootballLeagueViewModel) {
    val topScorers by footballLeagueViewModel.topScorers.observeAsState(emptyList())

    LaunchedEffect(Unit) {
        footballLeagueViewModel.fetchTopScorers(211, 2023) // Zamijeniti sa stvarnim leagueId i season
    }

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(topScorers) { playerResponse ->
            PlayerItem(playerResponse)
        }
    }
}

@Composable
fun TopYellowCardsScreen(footballLeagueViewModel: FootballLeagueViewModel) {
    val topYellowCards by footballLeagueViewModel.topYellowCards.observeAsState(emptyList())

    LaunchedEffect(Unit) {
        footballLeagueViewModel.fetchTopYellowCards(211, 2023) // Zamijeniti sa stvarnim leagueId i season
    }

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(topYellowCards) { playerResponse ->
            PlayerItem(playerResponse)
        }
    }
}
@Composable
fun PlayerItem(playerResponse: PlayerResponse) {
    val playerStatistics = playerResponse.statistics.firstOrNull()

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            Image(
                painter = rememberImagePainter(data = playerResponse.player.photo),
                contentDescription = null,
                modifier = Modifier.size(64.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = playerResponse.player.name, style = MaterialTheme.typography.bodyLarge)
                playerStatistics?.let {
                    val teamName = it.team.name
                    val goals = it.goals.total
                    val yellowCards = it.cards.yellow

                    Text(text = teamName, style = MaterialTheme.typography.bodyMedium)
                    Spacer(modifier = Modifier.height(8.dp))
                    if (goals != null) {
                        Text(text = "Goals: $goals", style = MaterialTheme.typography.bodySmall)
                    }
                    if (yellowCards != null) {
                        Text(text = "Yellow Cards: $yellowCards", style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
        }
    }
}