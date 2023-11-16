package com.codebaron.kongaposts.mvvm

import androidx.lifecycle.MutableLiveData
import com.codebaron.kongaposts.model.PostsModelItem
import com.codebaron.kongaposts.model.Token
import com.codebaron.kongaposts.remote.Endpoints
import com.codebaron.kongaposts.remote.ErrorMapper
import com.codebaron.kongaposts.remote.ResponseHandler
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * @author Anyanwu Nicholas(codeBaron)
 * @since 16-11-2023
 */

/**
 * Repository class responsible for handling user posts-related network requests.
 * @param endpoints The API endpoints for user posts operations.
 */
class UserPostsRepository(private val endpoints: Endpoints) {

    // Error mapper instance to handle error responses.
    var errorMapper: ErrorMapper = ErrorMapper()

    /**
     * Performs authentication request to obtain a user token.
     * @return MutableLiveData containing ResponseHandler<Token?> to observe authentication responses.
     */
    fun authentication(): MutableLiveData<ResponseHandler<Token?>> {
        // LiveData to hold and observe authentication responses.
        val responseHandler: MutableLiveData<ResponseHandler<Token?>> = MutableLiveData()
        // Post loading state to notify observers that the authentication request is in progress.
        responseHandler.postValue(ResponseHandler.Loading)
        // Enqueue the authentication request callback.
        endpoints.authentication().enqueue(object : Callback<Token> {
            override fun onResponse(call: Call<Token>, response: Response<Token>) {
                if (response.isSuccessful) {
                    // Handle successful response.
                    responseHandler.postValue(ResponseHandler.Success(response.body()))
                } else {
                    // Handle error response and notify observers with the error message.
                    val error = errorMapper.parseErrorMessage(response.errorBody())
                    responseHandler.postValue(ResponseHandler.Error(error?.message))
                }
            }

            override fun onFailure(call: Call<Token>, t: Throwable) {
                // Handle network or other failures and notify observers with the error message.
                responseHandler.postValue(ResponseHandler.Error(t.message))
            }
        })

        // Return the LiveData to observe authentication responses.
        return responseHandler
    }

    /**
     * Retrieves all user posts using the provided token.
     * @param token The user token for authorization.
     * @return MutableLiveData containing ResponseHandler<List<PostsModelItem>?> to observe posts responses.
     */
    fun getAllPosts(token: String): MutableLiveData<ResponseHandler<List<PostsModelItem>?>> {
        // LiveData to hold and observe posts responses.
        val responseHandler: MutableLiveData<ResponseHandler<List<PostsModelItem>?>> = MutableLiveData()
        // Post loading state to notify observers that the posts request is in progress.
        responseHandler.postValue(ResponseHandler.Loading)
        // Enqueue the getAllUsers request callback.
       endpoints.getAllUsers(token).enqueue(object : Callback<List<PostsModelItem>> {
           override fun onResponse(
               call: Call<List<PostsModelItem>>,
               response: Response<List<PostsModelItem>>
           ) {
               if (response.isSuccessful) {
                   // Handle successful response.
                   responseHandler.postValue(ResponseHandler.Success(response.body()))
               } else {
                   // Handle error response and notify observers with the error message.
                   val error = errorMapper.parseErrorMessage(response.errorBody())
                   responseHandler.postValue(ResponseHandler.Error(error?.message))
               }
           }

           override fun onFailure(call: Call<List<PostsModelItem>>, t: Throwable) {
               // Handle network or other failures and notify observers with the error message.
               responseHandler.postValue(ResponseHandler.Error(t.message))
           }
       })

        // Return the LiveData to observe posts responses.
        return responseHandler
    }
}