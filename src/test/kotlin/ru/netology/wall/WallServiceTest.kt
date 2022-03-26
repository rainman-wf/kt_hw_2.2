package ru.netology.wall

import org.junit.Test

import org.junit.Assert.*

class WallServiceTest {

    @Test
    fun add() {

        val service = WallService

        val post = service.add(Post(1, 2, "Post #1"))

        assertEquals(service.getPostById(post.id), post)
    }

    @Test
    fun updateExisting_successful() {

        val service = WallService

        service.add(Post(1, 2, "Post #1"))
        service.add(Post(1, 3, "Post #2"))
        service.add(Post(1, 5, "Post #3"))

        val post = service.update(Post(1, 4, "new Post #2", 2))

        assertTrue(post)
    }

    @Test
    fun updateExisting_failed() {

        val service = WallService

        service.add(Post(1, 2, "Post #1"))
        service.add(Post(1, 3, "Post #2"))
        service.add(Post(1, 5, "Post #3"))

        val post = service.update(Post(1, 4, "new Post #3", 15))

        assertFalse(post)
    }

    @Test
    fun updateExisting_null() {

        val service = WallService

        service.add(Post(1, 2, "Post #1"))
        service.add(Post(1, 3, "Post #2"))
        service.add(Post(1, 5, "Post #3"))

        val post = service.update(Post(1, 4, "new Post #3", 15))

        assertFalse(post)
    }

    @Test
    fun addNewAttachment() {
        val service = WallService

        val post = service.add(
            Post(
                1,
                2,
                "One Video",
                attachments = arrayListOf(
                    VideoAttachment(
                        Video(
                            1,
                            "cats",
                            400,
                            300,
                            1920,
                            1080,
                            244
                        )
                    )
                )
            )
        )

        assertEquals(service.getPostById(post.id), post)
    }

    @Test
    fun editAttachment() {
        val service = WallService

        val post = service.update(
            Post(
                1,
                2,
                "Two photo",
                4,
                attachments = arrayListOf(
                    PhotoAttachment(
                        Photo(1, "png", 400, 300, 1920, 1080)
                    ),
                    PhotoAttachment(
                        Photo(2, "png", 400, 300, 1280, 720)
                    )
                )
            )
        )

        assertTrue(post)
    }

    @Test
    fun addNewAudio() {
        val service = WallService

        val post = service.add(
            Post(
                1,
                2,
                "Two audio",
                attachments = arrayListOf(
                    AudioAttachment(Audio(1, "LP", "In the end", true, 180)),
                    AudioAttachment(Audio(2))
                )
            )
        )

        assertEquals(service.getPostById(post.id)?.attachments?.get(0)?.type, post.attachments[0].type)
    }

    @Test
    fun editDoc() {
        val service = WallService

        val post = service.update(
            Post(
                1,
                2,
                "Two photo",
                4,
                attachments = arrayListOf(
                    DocAttachment(Doc(1, "Students list", 32500, "xls", 25))
                )
            )
        )

        assertFalse(post)
    }

    @Test
    fun addUrl() {
        val service = WallService

        val post = service.add(
            Post(
                1,
                2,
                "Two photo",
                4,
                attachments = arrayListOf(
                    UrlAttachment(
                        Url(
                            "https://netology.ru/",
                            "Netology",
                            photo = Photo(
                                1,
                                "png",
                                400,
                                300,
                                1920,
                                1080
                            )
                        )
                    )
                )
            )
        )

        assertEquals(service.getPostById(post.id)?.attachments, post.attachments)
    }
}