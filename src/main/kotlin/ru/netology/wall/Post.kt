package ru.netology.wall

import java.util.*

data class Post(

    val ownerId: Int,
    val fromId: Int,
    val text: String,

    val id: Int? = null,
    val date: Long = Date().time,
    val likes: Int = 0,
    val views: Int = 0,
)



