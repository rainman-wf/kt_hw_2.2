package ru.netology.wall

object WallService {
    private var posts = HashMap<Int?, Post?>()
    private var postId: Int = 0

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

    fun getPostById(id: Int?) = posts[id]
}