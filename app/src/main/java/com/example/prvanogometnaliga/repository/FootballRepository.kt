package com.example.prvanogometnaliga.repository

import com.example.prvanogometnaliga.model.*
import com.example.prvanogometnaliga.network.ApiFootballService
import retrofit2.Response

class FootballRepository(private val api: ApiFootballService) {

    suspend fun getStandings(leagueId: Int, season: Int): Response<ApiResponse> {
        return api.getStandings(leagueId, season)
    }

    suspend fun getFixtures(leagueId: Int, season: Int): Response<FixturesResponse> {
        return api.getFixtures(leagueId, season)
    }

    suspend fun getTransfers(teamId: Int): Response<TransfersResponse> {
        return api.getTransfers(teamId)
    }

    suspend fun getTopScorers(leagueId: Int, season: Int): Response<PlayerApiResponse> {
        return api.getTopScorers(leagueId, season)
    }

    suspend fun getTopYellowCards(leagueId: Int, season: Int): Response<PlayerApiResponse> {
        return api.getTopYellowCards(leagueId, season)
    }
}
