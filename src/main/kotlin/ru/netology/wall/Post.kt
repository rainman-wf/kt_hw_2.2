package ru.netology.wall

import java.util.*
import kotlin.collections.ArrayList

data class Post(

    val ownerId: Int,
    val fromId: Int,
    val text: String? = null,

    val id: Int? = null,
    val date: Long = Date().time,
    val likes: Int = 0,
    val views: Int = 0,
    val attachments: ArrayList<Attachment> = ArrayList()
)

sealed class Attachment {
    data class Photo(
        val id: Int,
        val fileFormat: String,
        val thumbWidth: Int,
        val thumbHeight: Int,
        val width: Int,
        val height: Int
    ) : Attachment()

    data class Video(
        val id: Int,
        val title: String?,
        val firstFrameWidth: Int,
        val firstFrameHeight: Int,
        val width: Int,
        val height: Int,
        val duration: Int
    ) : Attachment()

    data class Audio(
        val id: Int,
        val artist: String? = null,
        val title: String? = null,
        val isHQ: Boolean = false,
        val duration: Int = 0
    ) : Attachment()

    data class Doc(
        val id: Int,
        val title: String,
        val size: Int,
        val ext: String,
        val type: Int
    ) : Attachment()

    data class Url(
        val url: String,
        val title: String? = null,
        val caption: String? = null,
        val description: String? = null,
        val photo: Photo? = null
    ) : Attachment()
}


