package ru.netology.objects


data class Note(
    val id: Int,
    val ownerId: Int,
    val title: String,
    val text: String,
    val date: Long,
    val comments: HashMap<Int, Comment> = HashMap(),
    val deletedComments: HashMap<Int, Comment> = HashMap(),
    val commentsCount: Int = 0
    ) : Comparable<Note> {
    override fun compareTo(other: Note): Int = id - other.id
    // сортировка по айди будет соответствовать сортировке по датам.
}