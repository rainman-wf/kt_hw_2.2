package ru.netology.objects

data class Comment(
    val id: Int,
    val fromId: Int,
    val noteId: Int,
    val ownerId: Int,
    val date: Long,
    val message: String
) : Comparable<Comment> {
    override fun compareTo(other: Comment): Int = id - other.id
}