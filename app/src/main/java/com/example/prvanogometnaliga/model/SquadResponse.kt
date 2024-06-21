package com.example.prvanogometnaliga.network.models

import com.example.prvanogometnaliga.model.Team

data class SquadResponse(
    val response: List<SquadTeam>
)

data class SquadTeam(
    val team: Team,
    val players: List<PlayerInfo>
)

data class PlayerInfo(
    val id: Int,
    val name: String,
    val age: Int,
    val number: Int?,
    val position: String,
    val photo: String
)