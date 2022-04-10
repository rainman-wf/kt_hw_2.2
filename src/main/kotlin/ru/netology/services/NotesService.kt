package ru.netology.services

import ru.netology.exceptions.*
import ru.netology.methods.*
import ru.netology.objects.*
import java.util.*

import kotlin.collections.HashMap
import kotlin.math.min

object NotesService : NotesImpl, CommentsImpl {

    private var noteId = 0

    private var userNotes = HashMap<Int, HashMap<Int, Note>>()

    override fun createComment(fromId: Int, noteId: Int, ownerId: Int, message: String): Comment {
        val note = getById(noteId, ownerId)
        var commentId = note.commentsCount
        val comments = note.comments
        // тут по идее мы должны сделать http POST запрос и получить от сервера объект комментария
        // но для учебной практики сделаем проще
        val newComment = Comment(commentId, fromId, noteId, ownerId, Date().time, message)
        // следующие три строки должны выполниться на стороне сервера
        // также для простоты я опустил уровни доступа к комментариям и заметкам, для этого
        // необходимо создать объек User и хранить в нем список друзей
        comments[commentId] = newComment
        val newNote = note.copy(comments = comments, commentsCount = ++commentId)
        isValidUser(ownerId)[note.id] = newNote
        return newComment
    }

    override fun getComments(noteId: Int, ownerId: Int, sort: Boolean, offset: Int, count: Int): List<Comment> {
        val note = getById(noteId, ownerId)
        val commentList = note.comments.values.sorted()
        val size = commentList.size
        val range = offset + count
        if (offset >= size) throw CommentNotFoundException()
        val endPos = min(range - 1, size - 1)
        val resultList = mutableListOf<Comment>()
        for (index in offset..endPos) resultList.add(commentList[index])
        return if (sort) resultList else resultList.sortedDescending()
    }

    override fun getCommentById(noteId: Int, ownerId: Int, commentId: Int): Comment {
        val note = getById(noteId, ownerId)
        val comments = note.comments
        return comments[commentId] ?: throw CommentNotFoundException()
    }

    override fun editComment(noteId: Int, ownerId: Int, commentId: Int, fromId: Int, message: String): Boolean {
        val note = getById(noteId, ownerId)
        val comments = note.comments
        val comment = comments[commentId] ?: throw CommentNotFoundException()
        if (comment.fromId != fromId) throw AccessDeniedException()
        val editedComment = comment.copy(message = message)
        comments[commentId] = editedComment
        val newNote = note.copy(comments = comments)
        isValidUser(ownerId)[noteId] = newNote
        return true
    }

    override fun deleteComment(noteId: Int, ownerId: Int, commentId: Int, fromId: Int): Boolean {
        val note = getById(noteId, ownerId)
        val comments = note.comments
        val deleted = note.deletedComments
        if (deleted.containsKey(commentId)) throw CommentAlreadyDeletedException()
        val comment = comments[commentId] ?: throw CommentNotFoundException()
        if (comment.fromId != fromId) throw AccessDeniedException()
        deleted[commentId] = comment
        comments.remove(commentId)
        val newNote = note.copy(comments = comments, deletedComments = deleted)
        isValidUser(ownerId)[noteId] = newNote
        return true
    }

    override fun restoreComment(noteId: Int, ownerId: Int, commentId: Int, fromId: Int): Boolean {
        val note = getById(noteId, ownerId)
        val comments = note.comments
        val deleted = note.deletedComments
        if (comments.containsKey(commentId)) throw NothingToRestoreException()
        val comment = deleted[commentId] ?: throw CommentNotFoundException()
        if (comment.fromId != fromId) throw AccessDeniedException()
        comments[commentId] = comment
        deleted.remove(commentId)
        val newNote = note.copy(comments = comments, deletedComments = deleted)
        isValidUser(ownerId)[noteId] = newNote
        return true
    }

    override fun add(ownerId: Int, title: String, text: String): Note {

        if (!userNotes.containsKey(ownerId)) userNotes[ownerId] = HashMap()

        val note = Note(
            noteId,
            ownerId,
            title,
            text,
            Date().time
        )

        isValidUser(ownerId)[noteId++] = note
        return note
    }

    override fun get(userId: Int, offset: Int, count: Int, sort: Boolean): List<Note> {
        val values = isValidUser(userId).values
        val notes = values.sorted()
        val size = notes.size
        val range = offset + count
        if (offset >= size) throw NoteNoteFoundException()
        val endPos = min(range - 1, size - 1)
        val resultList = mutableListOf<Note>()
        for (index in offset..endPos) resultList.add(notes[index])
        return if (sort) resultList else resultList.sortedDescending()
    }

    override fun getById(noteId: Int, ownerId: Int) = isValidUser(ownerId)[noteId] ?: throw NoteNoteFoundException()

    override fun edit(ownerId: Int, noteId: Int, title: String, text: String): Boolean {
        val note = getById(noteId, ownerId)
        val newNote = note.copy(title = title, text = text)
        isValidUser(ownerId)[noteId] = newNote
        return true
    }

    override fun delete(ownerId: Int, noteId: Int): Boolean {
        getById(noteId, ownerId)
        isValidUser(ownerId).remove(noteId)
        return true
    }

    private fun isValidUser(ownerId: Int) = userNotes[ownerId] ?: throw InvalidUserIdException()
}


