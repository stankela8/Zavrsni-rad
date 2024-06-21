package com.example.prvanogometnaliga.network

import com.example.prvanogometnaliga.model.ApiResponse
import com.example.prvanogometnaliga.model.FixturesResponse
import com.example.prvanogometnaliga.model.PlayerApiResponse
import com.example.prvanogometnaliga.model.TransfersResponse
import com.example.prvanogometnaliga.network.models.SquadResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ApiFootballService {
    @Headers(
        "x-rapidapi-key: ac3d1aca25msh93b53f0edb584a3p1d60eajsn11a7967b27d9",
        "x-rapidapi-host: api-football-v1.p.rapidapi.com"
    )
    @GET("v3/standings")
    suspend fun getStandings(
        @Query("league") leagueId: Int,
        @Query("season") season: Int
    ): Response<ApiResponse>

    @Headers(
        "x-rapidapi-key: ac3d1aca25msh93b53f0edb584a3p1d60eajsn11a7967b27d9",
        "x-rapidapi-host: api-football-v1.p.rapidapi.com"
    )
    @GET("v3/fixtures")
    suspend fun getFixtures(
        @Query("league") leagueId: Int,
        @Query("season") season: Int
    ): Response<FixturesResponse>

    @Headers(
        "x-rapidapi-key: ac3d1aca25msh93b53f0edb584a3p1d60eajsn11a7967b27d9",
        "x-rapidapi-host: api-football-v1.p.rapidapi.com"
    )
    @GET("v3/transfers")
    suspend fun getTransfers(
        @Query("team") teamId: Int
    ): Response<TransfersResponse>

    @Headers(
        "x-rapidapi-key: ac3d1aca25msh93b53f0edb584a3p1d60eajsn11a7967b27d9",
        "x-rapidapi-host: api-football-v1.p.rapidapi.com"
    )
    @GET("v3/players/topscorers")
    suspend fun getTopScorers(
        @Query("league") leagueId: Int,
        @Query("season") season: Int
    ): Response<PlayerApiResponse>

    @Headers(
        "x-rapidapi-key: ac3d1aca25msh93b53f0edb584a3p1d60eajsn11a7967b27d9",
        "x-rapidapi-host: api-football-v1.p.rapidapi.com"
    )
    @GET("v3/players/topyellowcards")
    suspend fun getTopYellowCards(
        @Query("league") leagueId: Int,
        @Query("season") season: Int
    ): Response<PlayerApiResponse>

    @Headers(
        "x-rapidapi-key: ac3d1aca25msh93b53f0edb584a3p1d60eajsn11a7967b27d9",
        "x-rapidapi-host: api-football-v1.p.rapidapi.com"
    )
    @GET("v3/players/squads")
    suspend fun getSquad(
        @Query("team") teamId: Int
    ): SquadResponse
}
