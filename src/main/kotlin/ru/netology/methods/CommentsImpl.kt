package ru.netology.methods

import ru.netology.objects.Comment

interface CommentsImpl {

    fun createComment(fromId: Int, noteId: Int, ownerId: Int, message: String): Comment

    fun getComments(noteId: Int, ownerId: Int, sort: Boolean = true, offset: Int = 0, count: Int = 20): List<Comment>

    fun getCommentById(noteId: Int, ownerId: Int, commentId: Int): Comment

    fun editComment(noteId: Int, ownerId: Int, commentId: Int, fromId: Int, message: String): Boolean

    fun deleteComment(noteId: Int, ownerId: Int, commentId: Int, fromId: Int): Boolean

    fun restoreComment(noteId: Int, ownerId: Int, commentId: Int, fromId: Int): Boolean

}