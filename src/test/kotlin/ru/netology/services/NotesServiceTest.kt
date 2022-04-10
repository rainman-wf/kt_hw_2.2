package ru.netology.services

import org.junit.Test

import org.junit.Assert.*
import ru.netology.exceptions.*

class NotesServiceTest {

    private val service = NotesService

    @Test
    fun createComment() {

        val ownerId = 1
        val title = "title"
        val text = "text"

        val note = service.add(ownerId, title, text)

        val fromId = 2
        val noteId = note.id
        val message = "new Comment"

        val comment = service.createComment(fromId, noteId, ownerId, message)

        assertEquals(comment, service.getCommentById(noteId, ownerId, comment.id))
    }

    @Test(expected = InvalidUserIdException::class)
    fun createComment_invalid_userId() {

        val ownerId = 1
        val title = "title"
        val text = "text"

        val note = service.add(ownerId, title, text)

        val fromId = 2
        val noteId = note.id
        val message = "new Comment"

        val comment = service.createComment(fromId, noteId, 2, message)

        assertEquals(comment, service.getCommentById(noteId, ownerId, comment.id))
    }

    @Test
    fun getComments() {
        val ownerId = 1
        val title = "title"
        val text = "text"

        val note = service.add(ownerId, title, text)

        for (senderId in 1..10)
            service.createComment(senderId, note.id, ownerId, "comment # $senderId")

        val comments = service.getComments(note.id, ownerId, count = 3)

        val expect = listOf(
            service.getCommentById(note.id, ownerId, 0),
            service.getCommentById(note.id, ownerId, 1),
            service.getCommentById(note.id, ownerId, 2)
        )

        assertEquals(comments, expect)
    }

    @Test
    fun getComments_reversed() {

        val ownerId = 1
        val title = "title"
        val text = "text"

        val note = service.add(ownerId, title, text)

        for (senderId in 1..10)
            service.createComment(senderId, note.id, ownerId, "comment # $senderId")

        val comments = service.getComments(note.id, ownerId, sort = false, count = 3)

        val expect = listOf(
            service.getCommentById(note.id, ownerId, 2),
            service.getCommentById(note.id, ownerId, 1),
            service.getCommentById(note.id, ownerId, 0)
        )

        assertEquals(comments, expect)
    }

    @Test(expected = CommentNotFoundException::class)
    fun getComments_out_of_range_offset() {

        val ownerId = 1
        val title = "title"
        val text = "text"

        val note = service.add(ownerId, title, text)

        for (senderId in 1..10)
            service.createComment(senderId, note.id, ownerId, "comment # $senderId")

        val comments = service.getComments(note.id, ownerId, offset = 11, count = 3)

        val expect = listOf(
            service.getCommentById(note.id, ownerId, 0),
            service.getCommentById(note.id, ownerId, 1),
            service.getCommentById(note.id, ownerId, 2)
        )

        assertEquals(comments, expect)
    }

    @Test(expected = CommentNotFoundException::class)
    fun getCommentById_failed() {

        val ownerId = 1
        val title = "title"
        val text = "text"

        val note = service.add(ownerId, title, text)

        val fromId = 2
        val noteId = note.id
        val message = "new Comment"

        service.createComment(fromId, noteId, ownerId, message)
        service.getCommentById(note.id, ownerId, 150)
    }

    @Test
    fun editComment() {

        val ownerId = 1
        val title = "title"
        val text = "text"

        val note = service.add(ownerId, title, text)

        val fromId = 2
        val noteId = note.id
        val message = "new Comment"
        val newMessage = "edited Comment"

        val comment = service.createComment(fromId, noteId, ownerId, message)

        val result = service.editComment(noteId, ownerId, comment.id, fromId, newMessage)

        assertTrue(result)
    }

    @Test(expected = CommentNotFoundException::class)
    fun editComment_comment_not_found() {

        val ownerId = 1
        val title = "title"
        val text = "text"

        val note = service.add(ownerId, title, text)

        val fromId = 2
        val noteId = note.id
        val message = "new Comment"
        val newMessage = "edited Comment"

        service.createComment(fromId, noteId, ownerId, message)

        service.editComment(noteId, ownerId, 120, fromId, newMessage)

    }

    @Test(expected = AccessDeniedException::class)
    fun editComment_access_denied() {

        val ownerId = 1
        val title = "title"
        val text = "text"

        val note = service.add(ownerId, title, text)

        val fromId = 2
        val noteId = note.id
        val message = "new Comment"
        val newMessage = "edited Comment"

        val comment = service.createComment(fromId, noteId, ownerId, message)

        val result = service.editComment(noteId, ownerId, comment.id, 5, newMessage)

        assertTrue(result)

    }

    @Test
    fun deleteComment() {
        val ownerId = 1
        val title = "title"
        val text = "text"

        val note = service.add(ownerId, title, text)

        val fromId = 2
        val noteId = note.id
        val message = "new Comment"

        val comment = service.createComment(fromId, noteId, ownerId, message)

        val result = service.deleteComment(noteId, ownerId, comment.id, fromId)

        assertTrue(result)
    }

    @Test (expected = CommentAlreadyDeletedException::class)
    fun deleteComment_nothing_to_delete() {
        val ownerId = 1
        val title = "title"
        val text = "text"

        val note = service.add(ownerId, title, text)

        val fromId = 2
        val noteId = note.id
        val message = "new Comment"

        val comment = service.createComment(fromId, noteId, ownerId, message)

        service.deleteComment(noteId, ownerId, comment.id, fromId)
        service.deleteComment(noteId, ownerId, comment.id, fromId)
    }

    @Test (expected = CommentNotFoundException::class)
    fun deleteComment_not_found() {
        val ownerId = 1
        val title = "title"
        val text = "text"

        val note = service.add(ownerId, title, text)

        val fromId = 2
        val noteId = note.id

        service.deleteComment(noteId, ownerId, 55, fromId)
    }

    @Test (expected = AccessDeniedException::class)
    fun deleteComment_access_denied() {
        val ownerId = 1
        val title = "title"
        val text = "text"

        val note = service.add(ownerId, title, text)

        val fromId = 2
        val noteId = note.id
        val message = "new Comment"

        val comment = service.createComment(fromId, noteId, ownerId, message)

        service.deleteComment(noteId, ownerId, comment.id, 50)

    }

    @Test
    fun restoreComment() {
        val ownerId = 1
        val title = "title"
        val text = "text"

        val note = service.add(ownerId, title, text)

        val fromId = 2
        val noteId = note.id
        val message = "new Comment"

        val comment = service.createComment(fromId, noteId, ownerId, message)

        service.deleteComment(noteId, ownerId, comment.id, fromId)

        val result = service.restoreComment(noteId, ownerId, comment.id, fromId)

        assertTrue(result)
    }

    @Test (expected = NothingToRestoreException::class)
    fun restoreComment_nothing_to_delete() {
        val ownerId = 1
        val title = "title"
        val text = "text"

        val note = service.add(ownerId, title, text)

        val fromId = 2
        val noteId = note.id
        val message = "new Comment"

        val comment = service.createComment(fromId, noteId, ownerId, message)

        service.restoreComment(noteId, ownerId, comment.id, fromId)

    }

    @Test (expected = CommentNotFoundException::class)
    fun restoreComment_not_found() {
        val ownerId = 1
        val title = "title"
        val text = "text"

        val note = service.add(ownerId, title, text)

        val fromId = 2
        val noteId = note.id
        val message = "new Comment"

        service.createComment(fromId, noteId, ownerId, message)

        service.restoreComment(noteId, ownerId, 50, fromId)
    }

    @Test (expected = AccessDeniedException::class)
    fun restoreComment_access_denied() {
        val ownerId = 1
        val title = "title"
        val text = "text"

        val note = service.add(ownerId, title, text)

        val fromId = 2
        val noteId = note.id
        val message = "new Comment"

        val comment = service.createComment(fromId, noteId, ownerId, message)

        service.deleteComment(noteId, ownerId, comment.id, fromId)

        service.restoreComment(noteId, ownerId, comment.id, 50)

    }

    @Test
    fun add() {
        val ownerId = 1
        val title = "title"
        val text = "text"

        val note = service.add(ownerId, title, text)

        assertEquals(note, service.getById(note.id, ownerId))
    }

    @Test
    fun get() {
        val ownerId = 1

        for (index in 1..10)
            service.add(ownerId, "title #$index", "note #$index")

        val notes = service.get(ownerId, offset = 2, count = 3)

        val first = notes.first().id

        val expect = listOf(
            service.getById(first, ownerId),
            service.getById(first + 1, ownerId),
            service.getById(first + 2, ownerId)
        )

        assertEquals(notes, expect)
    }

    @Test
    fun get_reversed() {
        val ownerId = 1

        for (index in 1..10)
            service.add(ownerId, "title #$index", "note #$index")

        val notes = service.get(ownerId, sort = false, offset = 2, count = 3)

        val first = notes.first().id

        val expect = listOf(
            service.getById(first, ownerId),
            service.getById(first - 1, ownerId),
            service.getById(first - 2, ownerId)
        )

        assertEquals(notes, expect)
    }

    @Test(expected = InvalidUserIdException::class)
    fun get_invalid_userId() {
        service.get(4, offset = 2, count = 3)
    }

    @Test (expected = NoteNoteFoundException::class)
    fun get_offset_out_of_range() {
        val ownerId = 1

        for (index in 1..10)
            service.add(ownerId, "title #$index", "note #$index")

        service.get(ownerId, sort = false, offset = 12, count = 3)

    }

    @Test
    fun getById() {
        val ownerId = 1
        val title = "title"
        val text = "text"

        val note = service.add(ownerId, title, text)

        assertEquals(note, service.getById(note.id, ownerId))
    }

    @Test(expected = NoteNoteFoundException::class)
    fun getById_() {
        val ownerId = 1
        val title = "title"
        val text = "text"

        service.add(ownerId, title, text)
        service.getById(50, ownerId)

    }

    @Test
    fun edit() {
        val ownerId = 1
        val title = "title"
        val text = "text"

        val note = service.add(ownerId, title, text)

        val newTitle = "edited Title"
        val newText = "edited Text"

        val result = service.edit(ownerId, note.id, newTitle, newText)

        assertTrue(result)

    }

    @Test
    fun delete() {
        val ownerId = 1
        val title = "title"
        val text = "text"

        val note = service.add(ownerId, title, text)

        val result = service.delete(ownerId, note.id)

        assertTrue(result)
    }

    @Test(expected = NoteNoteFoundException::class)
    fun delete_note_found() {
        service.delete(1, 150)
    }

    @Test(expected = InvalidUserIdException::class)
    fun delete_note_found_() {
        service.delete(4, 0)
    }
}