package com.example.prvanogometnaliga.model

data class Comment(
    val userId: String = "",
    val text: String = "",
    val timestamp: Long = 0L
) {

    constructor() : this("", "", 0L)
}