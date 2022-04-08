package ru.netology.wall

object WallService {

    private var posts = HashMap<Int?, Post?>()
    private var postId: Int = 0
    private var commentId: Int = 0

    fun createComment(comment: Comment): Comment {

        val post = getPostById(comment.postId)
        val comments = post.comments
        val newComment = comment.copy(id = commentId)

        comments[commentId++] = newComment

        val newPost = post.copy(comments = comments)

        posts[post.id] = newPost

        return newComment
    }

    fun add(post: Post): Post {
        val newPost = post.copy(id = postId)
        posts[postId++] = newPost
        return newPost
    }

    fun update(post: Post): Boolean {

        val newPost = posts[post.id]?.copy(text = post.text, attachments = post.attachments) ?: return false
        posts[post.id] = newPost
        return true

    }

    fun sendCommentReport(post: Post, report: ReportComment): Int {
        val comments = post.comments
        val reports = post.reports
        if (report.reason !in 0..8) throw InvalidReasonCodeException()
        if (!comments.containsKey(report.commentID)) throw CommentNotFoundException()
        reports.add(report)
        posts[post.id] = getPostById(post.id).copy(reports = reports)
        return 1
    }

    fun getPostById(id: Int?) = posts[id] ?: throw PostNotFoundException()

}

class PostNotFoundException : NullPointerException("Post not found")
class CommentNotFoundException : RuntimeException("Comment not found")
class InvalidReasonCodeException : RuntimeException("Invalid report reason")
