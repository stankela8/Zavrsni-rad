package com.example.prvanogometnaliga

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.NavType
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

        // Inicijalizacija AuthViewModel-a
        authViewModel = ViewModelProvider(this).get(AuthViewModel::class.java)

        // Inicijalizacija FootballLeagueViewModel-a
        val footballLeagueViewModelFactory = FootballLeagueViewModelFactory(repository, firestoreRepository, authViewModel)
        footballLeagueViewModel = ViewModelProvider(this, footballLeagueViewModelFactory).get(FootballLeagueViewModel::class.java)

        // Inicijalizacija FirestoreViewModel-a
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
                    NavHost(navController = navController, startDestination = "login") {
                        composable("login") {
                            LoginScreen(navController, authViewModel)
                        }
                        composable("home") {
                            HomeScreen(navController, authViewModel)
                        }
                        composable("standings") {
                            StandingsScreen(navController, footballLeagueViewModel)
                        }
                        composable("matches") {
                            MatchesScreen(navController, footballLeagueViewModel)
                        }
                        composable("statistics") {
                            StatisticsScreen(footballLeagueViewModel)
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
                        composable("squads") {
                            SquadsScreen(navController, footballLeagueViewModel)
                        }
                        composable(
                            route = "squadDetails/{teamId}",
                            arguments = listOf(navArgument("teamId") { type = NavType.IntType })
                        ) { backStackEntry ->
                            val teamId = backStackEntry.arguments?.getInt("teamId") ?: 0
                            SquadDetailsScreen(teamId, footballLeagueViewModel)
                        }
                        composable("highlights"){
                            HighlightsScreen()
                        }
                        composable("profile")
                        {
                            ProfileScreen()
                        }
                        composable("comments/{matchId}") { backStackEntry ->
                            val matchId = backStackEntry.arguments?.getString("matchId")
                            if (matchId != null) {
                                CommentsScreen(matchId = matchId, navController = navController, footballLeagueViewModel = footballLeagueViewModel, authViewModel = authViewModel)
                            }
                        }
                    }
                }
            }
        }
    }
}
