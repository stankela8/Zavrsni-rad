package com.example.prvanogometnaliga.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.example.prvanogometnaliga.model.*
import com.example.prvanogometnaliga.network.models.SquadResponse
import com.example.prvanogometnaliga.repository.FirestoreRepository
import com.example.prvanogometnaliga.repository.FootballRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FootballLeagueViewModel(
    private val repository: FootballRepository,
    private val firestore: FirestoreRepository,
    private val authViewModel: AuthViewModel
) : ViewModel(){

    private val _standings = MutableLiveData<List<TeamStanding>>()
    val standings: LiveData<List<TeamStanding>> = _standings

    private val _fixtures = MutableLiveData<List<FixtureResponse>>()
    val fixtures: LiveData<List<FixtureResponse>> = _fixtures

    private val _groupedFixtures = MutableLiveData<Map<Int, List<FixtureResponse>>>()
    val groupedFixtures: LiveData<Map<Int, List<FixtureResponse>>> = _groupedFixtures

    private val _transfers = MutableLiveData<List<Transfer>>()
    val transfers: LiveData<List<Transfer>> = _transfers

    private val _topScorers = MutableLiveData<List<PlayerResponse>>()
    val topScorers: LiveData<List<PlayerResponse>> = _topScorers

    private val _topYellowCards = MutableLiveData<List<PlayerResponse>>()
    val topYellowCards: LiveData<List<PlayerResponse>> = _topYellowCards

    fun fetchStandings(leagueId: Int, season: Int) {
        viewModelScope.launch {
            try {
                val response = repository.getStandings(leagueId, season)
                if (response.isSuccessful && response.body() != null) {
                    _standings.postValue(response.body()!!.response.flatMap { it.league.standings.flatten() })
                }
            } catch (e: Exception) {
                Log.e("FootballLeagueViewModel", "Error fetching standings", e)
            }
        }
    }

    fun fetchFixtures(leagueId: Int, season: Int) {
        viewModelScope.launch {
            try {
                val response = repository.getFixtures(leagueId, season)
                if (response.isSuccessful && response.body() != null) {
                    _fixtures.postValue(response.body()!!.response)
                    groupFixturesByRound(response.body()!!.response)
                }
            } catch (e: Exception) {
                Log.e("FootballLeagueViewModel", "Error fetching fixtures", e)
            }
        }
    }

    private fun groupFixturesByRound(fixtures: List<FixtureResponse>) {
        val groupedFixtures = fixtures.chunked(6).mapIndexed { index, list -> index + 1 to list }.toMap()
        _groupedFixtures.postValue(groupedFixtures)
    }

    fun fetchTransfers(teamId: Int) {
        viewModelScope.launch {
            try {
                val response = repository.getTransfers(teamId)
                if (response.isSuccessful && response.body() != null) {
                    _transfers.postValue(response.body()!!.response)
                }
            } catch (e: Exception) {
                Log.e("FootballLeagueViewModel", "Error fetching transfers", e)
            }
        }
    }

    fun fetchTopScorers(leagueId: Int, season: Int) {
        viewModelScope.launch {
            try {
                val response = repository.getTopScorers(leagueId, season)
                if (response.isSuccessful && response.body() != null) {
                    _topScorers.postValue(response.body()!!.response)
                }
            } catch (e: Exception) {
                Log.e("FootballLeagueViewModel", "Error fetching top scorers", e)
            }
        }
    }

    fun fetchTopYellowCards(leagueId: Int, season: Int) {
        viewModelScope.launch {
            try {
                val response = repository.getTopYellowCards(leagueId, season)
                if (response.isSuccessful && response.body() != null) {
                    _topYellowCards.postValue(response.body()!!.response)
                }
            } catch (e: Exception) {
                Log.e("FootballLeagueViewModel", "Error fetching top yellow cards", e)
            }
        }
    }
    private val _squadResponse = MutableStateFlow<SquadResponse?>(null)
    val squadResponse: StateFlow<SquadResponse?> = _squadResponse

    fun fetchSquad(teamId: Int) {
        viewModelScope.launch {
            try {
                val response = repository.getSquad(teamId)
                _squadResponse.value = response
            } catch (e: Exception) {
                _squadResponse.value = null
            }
        }
    }

    fun getComments(matchId: String): LiveData<List<Comment>> {
        return firestore.getComments(matchId)
    }

    fun addComment(matchId: String, comment: Comment) {
        val user = authViewModel.user.value
        if (user != null) {
            firestore.addComment(matchId, comment)
        }
    }


}
