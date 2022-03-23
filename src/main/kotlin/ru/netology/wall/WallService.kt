package ru.netology.wall

object WallService {
    private var posts = emptyArray<Post>()


    fun add(post: Post): Post {
        val newPost = post.copy(
            id = if (posts.isEmpty()) 1 else {
                posts.last().id + 1
            }
        )
        posts += newPost
        println("add " + newPost.id)
        return posts.last()
    }

    fun update(post: Post): Boolean {
        if (findPost(post) != null) {
            println("iterate " + post.id)
            val newPost = post.copy(text = post.text)
            posts[posts.indexOf(post)] = newPost
            return true
        }
        return false
    }

    private fun findPost(post: Post): Post? {
        for (p in posts) {
            if (p.id == post.id) {
                return post
            }
        }
        return null
    }
}