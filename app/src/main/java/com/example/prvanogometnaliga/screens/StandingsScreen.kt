package com.example.prvanogometnaliga.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.prvanogometnaliga.viewmodel.FootballLeagueViewModel
import com.example.prvanogometnaliga.model.TeamStanding

@Composable
fun StandingsScreen(navController: NavController, footballLeagueViewModel: FootballLeagueViewModel = viewModel()) {
    val standings by footballLeagueViewModel.standings.observeAsState(emptyList())

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Standings",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(16.dp))
            if (standings.isEmpty()) {
                Text(text = "No standings available", style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onBackground))
            } else {
                StandingsTable(standings)
            }
        }
    }
}

@Composable
fun StandingsTable(standings: List<TeamStanding>) {
    Column {
        StandingsTableHeader()
        Divider(color = MaterialTheme.colorScheme.onBackground)
        LazyColumn {
            items(standings) { teamStanding ->
                StandingsTableRow(teamStanding)
                Divider(color = MaterialTheme.colorScheme.onBackground)
            }
        }
    }
}

@Composable
fun StandingsTableHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary)
            .padding(12.dp)
    ) {
        Text(
            text = "Pos",
            style = MaterialTheme.typography.bodyLarge.copy(color = Color.White),
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center
        )
        Text(
            text = "",
            style = MaterialTheme.typography.bodyLarge.copy(color = Color.White),
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center
        )
        Text(
            text = "Team",
            style = MaterialTheme.typography.bodyLarge.copy(color = Color.White),
            modifier = Modifier.weight(5f),
            textAlign = TextAlign.Left
        )
        Text(
            text = "Points",
            style = MaterialTheme.typography.bodyLarge.copy(color = Color.White),
            modifier = Modifier.weight(2f),
            textAlign = TextAlign.Center
        )
        Text(
            text = "GD",
            style = MaterialTheme.typography.bodyLarge.copy(color = Color.White),
            modifier = Modifier.weight(2f),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun StandingsTableRow(teamStanding: TeamStanding) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
    ) {
        Text(
            text = "${teamStanding.rank}",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterVertically),
            textAlign = TextAlign.Center
        )
        Image(
            painter = rememberImagePainter(data = teamStanding.team.logo),
            contentDescription = null,
            modifier = Modifier
                .size(32.dp)
                .weight(1f)
                .align(Alignment.CenterVertically)
        )
        Text(
            text = teamStanding.team.name,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .weight(5f)
                .align(Alignment.CenterVertically),
            textAlign = TextAlign.Start
        )
        Text(
            text = "${teamStanding.points}",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .weight(2f)
                .align(Alignment.CenterVertically),
            textAlign = TextAlign.Center
        )
        Text(
            text = "${teamStanding.goalsDiff}",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .weight(2f)
                .align(Alignment.CenterVertically),
            textAlign = TextAlign.Center
        )
    }
}
