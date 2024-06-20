package com.example.prvanogometnaliga.model

import com.google.gson.annotations.SerializedName

data class TransfersResponse(
    @SerializedName("response")
    val response: List<Transfer>
)

data class Transfer(
    @SerializedName("player")
    val player: TransferPlayer,
    @SerializedName("transfers")
    val transfers: List<TransferDetail>
)

data class TransferPlayer(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String?,
    @SerializedName("photo")
    val photo: String?
)

data class TransferDetail(
    @SerializedName("date")
    val date: String?,
    @SerializedName("type")
    val type: String?,
    @SerializedName("teams")
    val teams: TransferTeams
)

data class TransferTeams(
    @SerializedName("in")
    val inTeam: TransferTeam,
    @SerializedName("out")
    val outTeam: TransferTeam
)

data class TransferTeam(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String?,
    @SerializedName("logo")
    val logo: String?
)
