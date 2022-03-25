package ru.netology.wall

object WallService {
    private var posts = HashMap<Int?, Post?>()
    private var postId: Int = 0

    fun add(post: Post): Post {
        val newPost = post.copy(id = postId)
        posts[postId] = newPost
        postId++
        return newPost
    }

    fun update(post: Post): Boolean {

        if (posts.containsKey(post.id)) {
            val newPost = posts[post.id]?.copy(text = post.text)
            posts[post.id] = newPost
            return true
        }
        return false
    }

    fun getPostById (id: Int?) = posts[id]
}