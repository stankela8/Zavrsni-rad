package com.example.prvanogometnaliga.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.prvanogometnaliga.model.Comment
import com.example.prvanogometnaliga.viewmodel.AuthViewModel
import com.example.prvanogometnaliga.viewmodel.FootballLeagueViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommentsScreen(matchId: String, authViewModel: AuthViewModel = viewModel(), footballLeagueViewModel: FootballLeagueViewModel = viewModel(),navController: NavController) {
    val comments by footballLeagueViewModel.getComments(matchId).observeAsState(emptyList())
    var newComment by remember { mutableStateOf("") }

    val user = authViewModel.user.collectAsState().value

    Column(modifier = Modifier.padding(16.dp).fillMaxSize()) {
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(comments) { comment ->
                CommentItem(comment)
            }
        }

        OutlinedTextField(
            value = newComment,
            onValueChange = { newComment = it },
            label = { Text("Add a comment") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = {
            user?.let { user ->
                authViewModel.getUserProfile(user.uid) { name, age ->
                    val commentObj = Comment(
                        userId = "$name, $age",
                        text = newComment,
                        timestamp = System.currentTimeMillis()
                    )
                    footballLeagueViewModel.addComment(matchId, commentObj)
                    newComment = ""
                }
            }
        }) {
            Text(text = "Post Comment")
        }
    }
}

@Composable
fun CommentItem(comment: Comment) {
    val formatter = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
    val formattedDate = formatter.format(Date(comment.timestamp))

    Card(
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "User: ${comment.userId}", style = MaterialTheme.typography.bodyMedium)
            Text(text = comment.text, style = MaterialTheme.typography.bodyLarge)
            Text(text = "Posted on: $formattedDate", style = MaterialTheme.typography.bodySmall)
        }
    }
}
