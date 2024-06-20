package com.example.prvanogometnaliga.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import com.example.prvanogometnaliga.viewmodel.FootballLeagueViewModel
import com.example.prvanogometnaliga.model.FixtureResponse

@Composable
fun MatchesScreen(footballLeagueViewModel: FootballLeagueViewModel = viewModel()) {
    val groupedFixtures by footballLeagueViewModel.groupedFixtures.observeAsState(emptyMap())
    var selectedRound by remember { mutableStateOf(1) }
    var expanded by remember { mutableStateOf(false) }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Fixtures",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(16.dp))

            if (groupedFixtures.isEmpty()) {
                Text(text = "No fixtures available", style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onBackground))
            } else {
                RoundDropdown(
                    rounds = groupedFixtures.keys.toList(),
                    selectedRound = selectedRound,
                    onRoundSelected = { selectedRound = it },
                    expanded = expanded,
                    onExpandedChange = { expanded = it }
                )
                LazyColumn {
                    groupedFixtures[selectedRound]?.let { fixtures ->
                        items(fixtures) { fixture ->
                            FixtureItem(fixture)
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoundDropdown(
    rounds: List<Int>,
    selectedRound: Int,
    onRoundSelected: (Int) -> Unit,
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit
) {
    var selectedRoundText by remember { mutableStateOf("Round $selectedRound") }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { onExpandedChange(!expanded) }
    ) {
        TextField(
            value = selectedRoundText,
            onValueChange = {},
            readOnly = true,
            label = { Text("Select Round") },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = expanded
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor()
                .clickable { onExpandedChange(true) }
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { onExpandedChange(false) }
        ) {
            rounds.forEach { round ->
                DropdownMenuItem(
                    text = { Text("Round $round") },
                    onClick = {
                        selectedRoundText = "Round $round"
                        onRoundSelected(round)
                        onExpandedChange(false)
                    }
                )
            }
        }
    }
}

@Composable
fun FixtureItem(fixture: FixtureResponse) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = rememberImagePainter(data = fixture.teams.home.logo),
                    contentDescription = null,
                    modifier = Modifier.size(40.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = fixture.teams.home.name,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = "${fixture.goals.home ?: 0} - ${fixture.goals.away ?: 0}",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.weight(1f),
                    color = Color.Gray
                )
                Text(
                    text = fixture.teams.away.name,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Image(
                    painter = rememberImagePainter(data = fixture.teams.away.logo),
                    contentDescription = null,
                    modifier = Modifier.size(40.dp)
                )
            }
            Text(
                text = "Date: ${fixture.fixture.date.split("T")[0]}",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 32.dp, top = 8.dp)
            )
            Text(
                text = "Venue: ${fixture.fixture.venue.name}",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 32.dp, top = 4.dp)
            )
        }
    }
}
