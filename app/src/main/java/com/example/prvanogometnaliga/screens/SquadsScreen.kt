package com.example.prvanogometnaliga.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.prvanogometnaliga.model.TeamStanding
import com.example.prvanogometnaliga.viewmodel.FootballLeagueViewModel
import kotlinx.coroutines.delay

@Composable
fun SquadsScreen(navController: NavController, footballLeagueViewModel: FootballLeagueViewModel = viewModel()) {
    var showSplash by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        delay(6000) // 5 sekundi čekanja
        showSplash = false
    }

    // Prikazivanje stvarnog sadržaja odmah, čak i dok se prikazuje splash
    Box(modifier = Modifier.fillMaxSize()) {
        val standings by footballLeagueViewModel.standings.observeAsState(emptyList())

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Pick a team: ",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.height(16.dp))
                LazyColumn {
                    items(standings) { teamStanding ->
                        SquadTeamItem(teamStanding) { teamId ->
                            navController.navigate("squadDetails/$teamId")
                        }
                    }
                }
            }
        }

        if (showSplash) {
            SplashMessage(
                message = "ENG: While developing this app, mostly data from the 2023/24 football season was used and therefore may differ from the current data.\n\n" +
                        "CRO: Prilikom izrade ove aplikacije korišteni su većinom podaci iz nogometne sezone 2023./24. te se stoga mogu razlikovati od trenutnih podataka.",
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}

@Composable
fun SplashMessage(message: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .size(280.dp)
            .clip(MaterialTheme.shapes.medium)
            .background(MaterialTheme.colorScheme.surface)
            .border(
                width = 2.dp,
                color = Color.Red,
                shape = RoundedCornerShape(4.dp)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = message,
            style = MaterialTheme.typography.bodyLarge.copy(fontSize = 18.sp),
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun SquadTeamItem(teamStanding: TeamStanding, onClick: (Int) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onClick(teamStanding.team.id) },
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = rememberImagePainter(data = teamStanding.team.logo),
                contentDescription = null,
                modifier = Modifier.size(40.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(
                    text = teamStanding.team.name,
                    style = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.onSurface)
                )
            }
        }
    }
}
