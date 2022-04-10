package ru.netology.methods

import ru.netology.objects.Note

interface NotesImpl {

    fun add(title: String, text: String, ): Int

    fun get(userId: Int, offset: Int = 0, count: Int = 20, sort: Boolean = true): List<Note>

    fun getById(noteId: Int, ownerId: Int): Note

    fun edit(noteId: Int, title: String, text: String): Boolean

    fun delete(noteId: Int): Boolean

}