package com.daftech.eplclubs.model

data class Club(
    val id: Int,
    val logo: Int,
    val name: String,
    val fullname: String,
    val nickname: String,
    val color: String,
    val founded: String,
    val ground: String,
    val capacity: String,
    val description: String,
    var isFavorite: Boolean? = false
)