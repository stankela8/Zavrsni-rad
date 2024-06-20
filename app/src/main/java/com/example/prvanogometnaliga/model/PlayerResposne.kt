package com.example.prvanogometnaliga.model

import com.google.gson.annotations.SerializedName

data class PlayerApiResponse(
    @SerializedName("response")
    val response: List<PlayerResponse>
)

data class PlayerResponse(
    @SerializedName("player")
    val player: FootballPlayer,
    @SerializedName("statistics")
    val statistics: List<PlayerStatistics>
)

data class FootballPlayer(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("firstname")
    val firstname: String,
    @SerializedName("lastname")
    val lastname: String,
    @SerializedName("age")
    val age: Int,
    @SerializedName("birth")
    val birth: PlayerBirth,
    @SerializedName("nationality")
    val nationality: String,
    @SerializedName("height")
    val height: String?,
    @SerializedName("weight")
    val weight: String?,
    @SerializedName("injured")
    val injured: Boolean,
    @SerializedName("photo")
    val photo: String
)

data class PlayerBirth(
    @SerializedName("date")
    val date: String,
    @SerializedName("place")
    val place: String,
    @SerializedName("country")
    val country: String
)

data class PlayerStatistics(
    @SerializedName("team")
    val team: PlayerTeam,
    @SerializedName("league")
    val league: PlayerLeague,
    @SerializedName("games")
    val games: PlayerGames,
    @SerializedName("substitutes")
    val substitutes: PlayerSubstitutes,
    @SerializedName("shots")
    val shots: PlayerShots,
    @SerializedName("goals")
    val goals: PlayerGoals,
    @SerializedName("passes")
    val passes: PlayerPasses,
    @SerializedName("tackles")
    val tackles: PlayerTackles,
    @SerializedName("duels")
    val duels: PlayerDuels,
    @SerializedName("dribbles")
    val dribbles: PlayerDribbles,
    @SerializedName("fouls")
    val fouls: PlayerFouls,
    @SerializedName("cards")
    val cards: PlayerCards,
    @SerializedName("penalty")
    val penalty: PlayerPenalty
)

data class PlayerTeam(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("logo")
    val logo: String
)

data class PlayerLeague(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("country")
    val country: String,
    @SerializedName("logo")
    val logo: String,
    @SerializedName("flag")
    val flag: String,
    @SerializedName("season")
    val season: Int
)

data class PlayerGames(
    @SerializedName("appearences")
    val appearances: Int,
    @SerializedName("lineups")
    val lineups: Int,
    @SerializedName("minutes")
    val minutes: Int,
    @SerializedName("number")
    val number: Int?,
    @SerializedName("position")
    val position: String,
    @SerializedName("rating")
    val rating: String?,
    @SerializedName("captain")
    val captain: Boolean
)

data class PlayerSubstitutes(
    @SerializedName("in")
    val inSubstitute: Int,
    @SerializedName("out")
    val outSubstitute: Int,
    @SerializedName("bench")
    val bench: Int
)

data class PlayerShots(
    @SerializedName("total")
    val total: Int?,
    @SerializedName("on")
    val on: Int?
)

data class PlayerGoals(
    @SerializedName("total")
    val total: Int,
    @SerializedName("conceded")
    val conceded: Int?,
    @SerializedName("assists")
    val assists: Int?,
    @SerializedName("saves")
    val saves: Int?
)

data class PlayerPasses(
    @SerializedName("total")
    val total: Int?,
    @SerializedName("key")
    val key: Int?,
    @SerializedName("accuracy")
    val accuracy: String?
)

data class PlayerTackles(
    @SerializedName("total")
    val total: Int?,
    @SerializedName("blocks")
    val blocks: Int?,
    @SerializedName("interceptions")
    val interceptions: Int?
)

data class PlayerDuels(
    @SerializedName("total")
    val total: Int?,
    @SerializedName("won")
    val won: Int?
)

data class PlayerDribbles(
    @SerializedName("attempts")
    val attempts: Int?,
    @SerializedName("success")
    val success: Int?,
    @SerializedName("past")
    val past: Int?
)

data class PlayerFouls(
    @SerializedName("drawn")
    val drawn: Int?,
    @SerializedName("committed")
    val committed: Int?
)

data class PlayerCards(
    @SerializedName("yellow")
    val yellow: Int,
    @SerializedName("yellowred")
    val yellowRed: Int,
    @SerializedName("red")
    val red: Int
)

data class PlayerPenalty(
    @SerializedName("won")
    val won: Int?,
    @SerializedName("commited")
    val committed: Int?,
    @SerializedName("scored")
    val scored: Int?,
    @SerializedName("missed")
    val missed: Int?,
    @SerializedName("saved")
    val saved: Int?
)
