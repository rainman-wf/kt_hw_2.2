package ru.netology.exceptions

class CommentNotFoundException : NullPointerException("Comment not found")
class NoteNoteFoundException : NullPointerException("Note not found")
class AccessDeniedException : RuntimeException("Access denied")
class CommentAlreadyDeletedException : RuntimeException("Comment already deleted")
class NothingToRestoreException : RuntimeException("Comment is not deleted")
class InvalidUserIdException: NullPointerException("Invalid user Id")