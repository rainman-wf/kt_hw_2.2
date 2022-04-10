package ru.netology.exceptions

class PostNotFoundException : NullPointerException("Post not found")
class CommentNotFoundException : RuntimeException("Comment not found")
class InvalidReasonCodeException : RuntimeException("Invalid report reason")
class NoteNoteFoundException: NullPointerException("Note not found")
