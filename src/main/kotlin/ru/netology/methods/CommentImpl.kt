package ru.netology.methods

import ru.netology.objects.Comment

interface NoteCommentImpl {

    fun createComment(fromId: Int, noteId: Int, ownerId: Int, message: String): Comment

    fun getComments(noteId: Int, ownerId: Int, sort: Boolean = true, offset: Int = 0, count: Int = 20): List<Comment>

    fun editComment(commentId: Int, ownerId: Int, message: String): Boolean

    fun deleteComment(commentId: Int, ownerId: Int): Boolean

    fun restoreComment(commentId: Int, ownerId: Int): Boolean
}