package com.codebaron.kongaposts.remote

import com.codebaron.kongaposts.model.PostsModelItem
import com.codebaron.kongaposts.model.Token
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

/**
 * @author Anyanwu Nicholas(codeBaron)
 * @since 16-11-2023
 */

/**
 * Retrofit API interface defining endpoints for user authentication and posts retrieval.
 */
interface Endpoints {

    /**
     * Authentication endpoint to obtain a user token.
     * @return Call<Token> representing the Retrofit call for the authentication request.
     */
    @GET("login")
    fun authentication(): Call<Token>

    /**
     * Endpoint to retrieve all users' posts.
     * @param accessToken The user access token for authorization.
     * @return Call<List<PostsModel>> representing the Retrofit call for retrieving all users' posts.
     */
    @GET("users")
    fun getAllUsers(@Header("x-access-token") accessToken: String): Call<List<PostsModelItem>>
}