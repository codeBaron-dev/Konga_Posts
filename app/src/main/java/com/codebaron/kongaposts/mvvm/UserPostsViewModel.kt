package com.codebaron.kongaposts.mvvm

import androidx.lifecycle.ViewModel

/**
 * @author Anyanwu Nicholas(codeBaron)
 * @since 16-11-2023
 */

/**
 * ViewModel class for handling user posts-related UI logic and interactions.
 * @param userPostsRepository The repository responsible for user posts-related data operations.
 */
class UserPostsViewModel(private val userPostsRepository: UserPostsRepository): ViewModel() {

    /**
     * Initiates the authentication process to obtain a user token.
     * @return MutableLiveData containing ResponseHandler<Token?> to observe authentication responses.
     */
    fun authentication() = userPostsRepository.authentication()

    /**
     * Retrieves all user posts using the provided token.
     * @param token The user token for authorization.
     * @return MutableLiveData containing ResponseHandler<List<PostsModel>?> to observe posts responses.
     */
    fun getAllPosts(token: String) = userPostsRepository.getAllPosts(token)
}