package ru.netology.methods

import ru.netology.objects.Note

interface NotesImpl {

    fun add(ownerId: Int, title: String, text: String): Note

    fun get(userId: Int, offset: Int = 0, count: Int = 20, sort: Boolean = true): List<Note>

    fun getById(noteId: Int, ownerId: Int): Note

    fun edit(ownerId: Int, noteId: Int, title: String, text: String): Boolean

    fun delete(ownerId: Int, noteId: Int): Boolean

}