package com.example.prvanogometnaliga

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.prvanogometnaliga.network.RetrofitInstance
import com.example.prvanogometnaliga.repository.FirestoreRepository
import com.example.prvanogometnaliga.repository.FootballRepository
import com.example.prvanogometnaliga.screens.*
import com.example.prvanogometnaliga.ui.theme.MyAppTheme
import com.example.prvanogometnaliga.viewmodel.*

class MainActivity : ComponentActivity() {
    private lateinit var footballLeagueViewModel: FootballLeagueViewModel
    private lateinit var authViewModel: AuthViewModel
    private lateinit var firestoreViewModel: FirestoreViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val repository = FootballRepository(RetrofitInstance.api)
        val firestoreRepository = FirestoreRepository()
        val viewModelProviderFactory = FootballLeagueViewModelFactory(repository)
        footballLeagueViewModel = ViewModelProvider(this, viewModelProviderFactory).get(FootballLeagueViewModel::class.java)
        authViewModel = ViewModelProvider(this).get(AuthViewModel::class.java)
        val firestoreViewModelFactory = FirestoreViewModelFactory(firestoreRepository)
        firestoreViewModel = ViewModelProvider(this, firestoreViewModelFactory).get(FirestoreViewModel::class.java)


        val leagueId = 211
        val season = 2023
        footballLeagueViewModel.fetchStandings(leagueId, season)
        footballLeagueViewModel.fetchFixtures(leagueId, season)

        setContent {
            MyAppTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "auth") {
                        composable("auth") {
                            AuthScreen(authViewModel, footballLeagueViewModel, firestoreViewModel, navController)
                        }
                        composable("home") {
                            HomeScreen(navController, authViewModel)
                        }
                        composable("standings") {
                            StandingsScreen(navController, footballLeagueViewModel)
                        }
                        composable("matches") {
                            MatchesScreen(footballLeagueViewModel)
                        }
                        composable("statistics") {
                            StatisticsScreen(footballLeagueViewModel)
                        }
                        composable("news") {
                            // Implement NewsScreen here
                        }
                        composable("transfers") {
                            TransfersScreen(navController, footballLeagueViewModel)
                        }
                        composable(
                            route = "transfersDetails/{teamId}",
                            arguments = listOf(navArgument("teamId") { type = NavType.IntType })
                        ) { backStackEntry ->
                            val teamId = backStackEntry.arguments?.getInt("teamId") ?: 0
                            TransfersDetailsScreen(teamId, footballLeagueViewModel)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AuthScreen(
    authViewModel: AuthViewModel,
    footballLeagueViewModel: FootballLeagueViewModel,
    firestoreViewModel: FirestoreViewModel,
    navController: androidx.navigation.NavController
) {
    val user by authViewModel.user.collectAsState(initial = null)

    if (user != null) {
        LaunchedEffect(Unit) {
            navController.navigate("home") {
                popUpTo("auth") { inclusive = true }
            }
        }
    } else {
        SignInSignUpScreen(authViewModel)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignInSignUpScreen(authViewModel: AuthViewModel) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isSignUp by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .wrapContentSize()
    ) {
        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = {
                if (isSignUp) {
                    authViewModel.signUp(email, password)
                } else {
                    authViewModel.signIn(email, password)
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (isSignUp) "Sign Up" else "Sign In")
        }
        TextButton(onClick = { isSignUp = !isSignUp }) {
            Text(if (isSignUp) "Already have an account? Sign In" else "Don't have an account? Sign Up")
        }
    }
}

@Composable
fun HomeScreen(navController: androidx.navigation.NavController, authViewModel: AuthViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = { navController.navigate("standings") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text("Standings")
        }
        Button(
            onClick = { navController.navigate("matches") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text("Matches")
        }
        Button(
            onClick = { navController.navigate("news") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text("News")
        }
        Button(
            onClick = { navController.navigate("highlights") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text("Highlights")
        }
        Button(
            onClick = { navController.navigate("statistics") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text("Statistics")
        }
        Button(
            onClick = { navController.navigate("transfers") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text("Transfers")
        }
        Button(
            onClick = {
                authViewModel.signOut()
                navController.navigate("auth") {
                    popUpTo("home") { inclusive = true }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text("Log Out")
        }
    }
}
