package es.uam.eps.dadm.hearthstonecards.view

import retrofit2.Response
import retrofit2.http.Body


import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

/**
 * Retrofit service interface for interacting with the comments API
 */
interface ApiService {

    /**
     * Gets a comment by its ID
     *
     * @param id ID of the comment to fetch
     * @return A Response containing the Comment object
     */
    @GET("comments/{id}")
    suspend fun getComment(@Path("id") id: Int): Response<Comment>

    /**
     * Posts a new comment to the server
     *
     * @param comment The Comment object to be sent
     * @return A Response containing the created Comment
     */
    @POST("comments")
    suspend fun postComment(@Body comment: Comment):Response<Comment>
}

/**
 * Data class representing a comment
 *
 * @property postId ID of the post the comment is associated with
 * @property id ID of the comment
 * @property name The name of the commenter
 * @property email The email of the commenter
 * @property body The content of the comment
 */
data class Comment(
    val postId: Int,
    val id: Int,
    val name: String,
    val email: String,
    val body: String
)