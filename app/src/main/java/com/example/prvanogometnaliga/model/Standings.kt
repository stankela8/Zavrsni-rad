package com.example.prvanogometnaliga.model

import com.google.gson.annotations.SerializedName

data class ApiResponse(
    @SerializedName("response")
    val response: List<StandingsResponse>
)

data class StandingsResponse(
    @SerializedName("league")
    val league: League
)

data class League(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("country")
    val country: String,
    @SerializedName("logo")
    val logo: String,
    @SerializedName("flag")
    val flag: String?,
    @SerializedName("season")
    val season: Int,
    @SerializedName("standings")
    val standings: List<List<TeamStanding>>
)

data class TeamStanding(
    @SerializedName("rank")
    val rank: Int,
    @SerializedName("team")
    val team: Team,
    @SerializedName("points")
    val points: Int,
    @SerializedName("goalsDiff")
    val goalsDiff: Int,
    @SerializedName("group")
    val group: String,
    @SerializedName("form")
    val form: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("description")
    val description: String?,
    @SerializedName("all")
    val all: AllStats // Dodajemo ovo polje za dodatne statistike
)
data class AllStats(
    @SerializedName("played")
    val played: Int,
    @SerializedName("win")
    val win: Int,
    @SerializedName("draw")
    val draw: Int,
    @SerializedName("lose")
    val lose: Int,
    @SerializedName("goals")
    val goals: TeamGoals
)

data class Team(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("logo")
    val logo: String
)

data class TeamGoals(
    @SerializedName("for")
    val goalsFor: Int,
    @SerializedName("against")
    val goalsAgainst: Int
)
