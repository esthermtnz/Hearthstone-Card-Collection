package es.uam.eps.dadm.hearthstonecards.view

import retrofit2.Response
import retrofit2.http.Body


import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @GET("comments/{id}")
    suspend fun getComment(@Path("id") id: Int): Response<Comment>

    @POST("comments")
    suspend fun postComment(@Body comment: Comment):Response<Comment>
}

data class Comment(
    val postId: Int,
    val id: Int,
    val name: String,
    val email: String,
    val body: String
)