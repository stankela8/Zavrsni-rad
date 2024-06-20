package com.example.prvanogometnaliga.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import com.example.prvanogometnaliga.model.TransferDetail
import com.example.prvanogometnaliga.viewmodel.FootballLeagueViewModel

@Composable
fun TransfersDetailsScreen(
    teamId: Int,
    footballLeagueViewModel: FootballLeagueViewModel = viewModel()
) {
    val transfers by footballLeagueViewModel.transfers.observeAsState(emptyList())

    LaunchedEffect(teamId) {
        footballLeagueViewModel.fetchTransfers(teamId)
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Transfers",
                style = MaterialTheme.typography.headlineSmall.copy(color = MaterialTheme.colorScheme.onBackground)
            )
            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn {
                items(transfers) { transfer ->
                    transfer.transfers.forEach { detail ->
                        TransferItem(transfer.player.name, detail)
                    }
                }
            }
        }
    }
}


@Composable
fun TransferItem(playerName: String?, transferDetail: TransferDetail) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clip(MaterialTheme.shapes.medium), // Rounded corners
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface) // Card background color
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = playerName ?: "Unknown Player",
                style = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.onSurface, fontSize = 20.sp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = rememberImagePainter(data = transferDetail.teams.outTeam.logo),
                    contentDescription = null,
                    modifier = Modifier.size(40.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "From: ${transferDetail.teams.outTeam.name}",
                    style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f))
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = rememberImagePainter(data = transferDetail.teams.inTeam.logo),
                    contentDescription = null,
                    modifier = Modifier.size(40.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "To: ${transferDetail.teams.inTeam.name}",
                    style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f))
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Date: ${transferDetail.date}",
                style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f))
            )
        }
    }
}
