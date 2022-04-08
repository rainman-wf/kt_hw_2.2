package ru.netology.wall

import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

data class Post(

    val ownerId: Int,
    val fromId: Int,
    val text: String? = null,

    val id: Int? = null,
    val date: Long = Date().time,
    val likes: Int = 0,
    val views: Int = 0,
    val attachments: ArrayList<Attachment> = ArrayList(),
    val comments: HashMap<Int, Comment> = HashMap(),
    val reports: ArrayList<ReportComment> = ArrayList()
)

data class Comment(
    val id: Int = 0,
    val postId: Int,
    val fromId: Int,
    val text: String?,
    val date: Long = Date().time,

)

data class ReportComment(
    val ownerId: Int?,
    val commentID: Int?,
    val reason: Int
)

interface Attachment {
    val type: Any?
}

data class VideoAttachment(
    override val type: Video
) : Attachment

data class PhotoAttachment(
    override val type: Photo
) : Attachment

data class AudioAttachment(
    override val type: Audio
) : Attachment

data class DocAttachment(
    override val type: Doc
) : Attachment

data class UrlAttachment(
    override val type: Url
) : Attachment

data class Photo(
    val id: Int,
    val fileFormat: String,
    val thumbWidth: Int,
    val thumbHeight: Int,
    val width: Int,
    val height: Int
)

data class Video(
    val id: Int,
    val title: String?,
    val firstFrameWidth: Int,
    val firstFrameHeight: Int,
    val width: Int,
    val height: Int,
    val duration: Int
)

data class Audio(
    val id: Int,
    val artist: String? = null,
    val title: String? = null,
    val isHQ: Boolean = false,
    val duration: Int = 0
)

data class Doc(
    val id: Int,
    val title: String,
    val size: Int,
    val ext: String,
    val type: Int
)

data class Url(
    val url: String,
    val title: String? = null,
    val caption: String? = null,
    val description: String? = null,
    val photo: Photo? = null
)