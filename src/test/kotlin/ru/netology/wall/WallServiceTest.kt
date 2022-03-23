package ru.netology.wall

import org.junit.Test

import org.junit.Assert.*

class WallServiceTest {

    @Test
    fun add() {

        val service = WallServiceClass()

        val post = service.add(Post(1, 2, "Post #1"))

        assertEquals(post.id != 0, true)
    }

    @Test
    fun updateExisting_successful() {

        val service = WallServiceClass()

        service.add(Post(1, 2, "Post #1"))
        service.add(Post(1, 3, "Post #2"))
        service.add(Post(1, 5, "Post #3"))

        val post = service.update(Post(1, 4, "new Post #3", 2))

        assertTrue(post)
    }

    @Test
    fun updateExisting_failed() {

        val service = WallServiceClass()

        service.add(Post(1, 2, "Post #1"))
        service.add(Post(1, 3, "Post #2"))
        service.add(Post(1, 5, "Post #3"))

        val post = service.update(Post(1, 4, "new Post #3", 15))

        assertFalse(post)
    }


}