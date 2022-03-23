package ru.netology.wall

class WallServiceClass {
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
        val index = findPost(post)
        if (index == -1) return false

        println("iterate " + post.id)
        println("find index $index")
        val newPost = post.copy(text = post.text)
        posts[index] = newPost
        return true

    }

    private fun findPost(post: Post): Int {
        for (p in posts) {
            if (p.id == post.id) {
                return posts.indexOf(p)
            }
        }
        return -1
    }
}