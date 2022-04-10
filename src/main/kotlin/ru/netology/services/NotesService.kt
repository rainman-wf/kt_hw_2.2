package ru.netology.services

import ru.netology.exceptions.NoteNoteFoundException
import ru.netology.methods.*
import ru.netology.objects.*
import java.util.*

import kotlin.collections.HashMap

object NotesService : NotesImpl, NoteCommentImpl {

    private var noteId = 0;

    private var userNotes = HashMap<Int, HashMap<Int, Note>>()

    override fun createComment(fromId: Int, noteId: Int, ownerId: Int, message: String): Comment {
        val note = getById(noteId, ownerId)
        var commentId = note.commentsCount
        val comments = note.comments
        val newComment = Comment (commentId, fromId, noteId, ownerId, Date().time, message)
        comments[commentId] = newComment
        val newNote = note.copy(comments = comments, commentsCount = ++commentId)
        userNotes[ownerId]?.put(note.id, newNote)
        return newComment
    }

    override fun getComments(noteId: Int, ownerId: Int, sort: Boolean, offset: Int, count: Int): List<Comment> {
        TODO("Not yet implemented")
    }

    override fun editComment(commentId: Int, ownerId: Int, message: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun deleteComment(commentId: Int, ownerId: Int): Boolean {
        TODO("Not yet implemented")
    }

    override fun restoreComment(commentId: Int, ownerId: Int): Boolean {
        TODO("Not yet implemented")
    }

    override fun add(title: String, text: String): Int {
        TODO("Not yet implemented")
    }

    override fun get(userId: Int, offset: Int, count: Int, sort: Boolean): List<Note> {
        TODO("Not yet implemented")
    }

    override fun getById(noteId: Int, ownerId: Int) = userNotes[ownerId]?.get(noteId) ?: throw NoteNoteFoundException()

    override fun edit(noteId: Int, title: String, text: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun delete(noteId: Int): Boolean {
        TODO("Not yet implemented")
    }


}


