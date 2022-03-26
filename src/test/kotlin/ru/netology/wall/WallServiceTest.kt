package ru.netology.wall

import org.junit.Test

import org.junit.Assert.*

class WallServiceTest {

    private val service = WallService
    private val ownerId = 1

    @Test
    fun addNewPost_video_photo() {

        val fromId = 1
        val text = "Video + Photo"
        val video = Video(1, "cats", 400, 300, 1920, 1080, 244)
        val photo = Photo(2, "png", 400, 300, 1280, 720)

        val post = service.add(
            Post(
                ownerId = ownerId,
                fromId = fromId,
                text = text,
                attachments = arrayListOf(VideoAttachment(video), PhotoAttachment(photo))
            )
        )

        assertEquals(service.getPostById(post.id), post)
    }

    @Test
    fun update_successful_add_audio() {

        val fromId = 2
        val text = "Two audio"
        val existingPostId = 0

        val audio1 = Audio(1, "LP", "In the end", true, 180)
        val audio2 = Audio(2)

        service.add(Post(ownerId, fromId, "text"))

        val post = service.update(
            Post(
                ownerId = ownerId,
                fromId = fromId,
                text = text,
                id = existingPostId,
                attachments = arrayListOf(AudioAttachment(audio1), AudioAttachment(audio2))
            )
        )

        assertTrue(post)
    }

    @Test
    fun update_failed_doc_url() {

        val fromId = 3
        val text = "Doc + Url"
        val existingPostId = 15

        val doc = Doc(1, "Students list", 32500, "xls", 25)
        val url = Url(
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

        val post = service.update(
            Post(
                ownerId = ownerId,
                fromId = fromId,
                text = text,
                id = existingPostId,
                attachments = arrayListOf(DocAttachment(doc), UrlAttachment(url))
            )
        )

        assertFalse(post)
    }
}