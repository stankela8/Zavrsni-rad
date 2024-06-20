package com.example.prvanogometnaliga.model

import com.google.gson.annotations.SerializedName

data class FixturesResponse(
    @SerializedName("response")
    val response: List<FixtureResponse>
)

data class FixtureResponse(
    @SerializedName("fixture")
    val fixture: Fixture,
    @SerializedName("league")
    val league: League,
    @SerializedName("teams")
    val teams: Teams,
    @SerializedName("goals")
    val goals: Goals,
    @SerializedName("score")
    val score: Score
)

data class Fixture(
    @SerializedName("id")
    val id: Int,
    @SerializedName("referee")
    val referee: String?,
    @SerializedName("timezone")
    val timezone: String,
    @SerializedName("date")
    val date: String,
    @SerializedName("timestamp")
    val timestamp: Long,
    @SerializedName("periods")
    val periods: Periods,
    @SerializedName("venue")
    val venue: Venue,
    @SerializedName("status")
    val status: Status
)

data class Periods(
    @SerializedName("first")
    val first: Long?,
    @SerializedName("second")
    val second: Long?
)

data class Venue(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("city")
    val city: String?
)

data class Status(
    @SerializedName("long")
    val long: String,
    @SerializedName("short")
    val short: String,
    @SerializedName("elapsed")
    val elapsed: Int?
)

data class Teams(
    @SerializedName("home")
    val home: Team,
    @SerializedName("away")
    val away: Team
)

data class Goals(
    @SerializedName("home")
    val home: Int?,
    @SerializedName("away")
    val away: Int?
)

data class Score(
    @SerializedName("halftime")
    val halftime: ScoreDetail,
    @SerializedName("fulltime")
    val fulltime: ScoreDetail,
    @SerializedName("extratime")
    val extratime: ScoreDetail?,
    @SerializedName("penalty")
    val penalty: ScoreDetail?
)

data class ScoreDetail(
    @SerializedName("home")
    val home: Int?,
    @SerializedName("away")
    val away: Int?
)
