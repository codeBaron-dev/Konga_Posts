package com.codebaron.kongaposts

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.codebaron.kongaposts.adapter.UserPostsAdapter
import com.codebaron.kongaposts.databinding.MainActivityBinding
import com.codebaron.kongaposts.model.PostsModelItem
import com.codebaron.kongaposts.mvvm.UserPostsViewModel
import com.codebaron.kongaposts.remote.ResponseHandler
import com.codebaron.kongaposts.utils.getJsonDataFromAsset
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.koin.android.ext.android.inject

/**
 * @author Anyanwu Nicholas(codeBaron)
 * @since 16-11-2023
 */
class MainActivity : AppCompatActivity() {

    // ViewModel for handling user posts-related logic and data.
    private val userPostsViewModel: UserPostsViewModel by inject()

    // View binding instance for the activity.
    private lateinit var binding: MainActivityBinding

    // Adapter for displaying user posts in a RecyclerView.
    private lateinit var userPostsAdapter: UserPostsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Initialize view binding.
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        // Observe authentication status.
        authenticationObserver()
    }

    /**
     * Observes the authentication status and takes appropriate actions based on the response.
     */
    private fun authenticationObserver() {
        userPostsViewModel.authentication().observe(this) {
            when (it) {
                is ResponseHandler.Loading -> {
                    // Handle loading state (e.g., show loading indicator).
                }

                is ResponseHandler.Success -> {
                    // Proceed to observe posts after successful authentication.
                    postsObserver(it.data?.api_key)
                }

                is ResponseHandler.Error -> {
                    // Handle authentication error (e.g., display an error message).
                }

                is ResponseHandler.Exception -> {
                    // Handle exceptional case during authentication.
                }

                is ResponseHandler.ThrowableError -> {
                    // Handle throwable error during authentication.
                }
            }
        }
    }

    /**
     * Observes user posts and takes appropriate actions based on the response.
     *
     * @param apiKey The API key obtained from successful authentication.
     */
    private fun postsObserver(apiKey: String?) {
        userPostsViewModel.getAllPosts(apiKey.toString()).observe(this) {
            when (it) {
                is ResponseHandler.Loading -> {
                    // Handle loading state (e.g., show loading indicator).
                }

                is ResponseHandler.Success -> {
                    // Initialize the RecyclerView with the obtained user posts.
                    initRecyclerView(it.data ?: readDummyPostsFromJson())
                }

                is ResponseHandler.Error -> {
                    // Handle error in retrieving user posts (e.g., display an error message).
                }

                is ResponseHandler.Exception -> {
                    // Handle exceptional case during user posts retrieval.
                }

                is ResponseHandler.ThrowableError -> {
                    // Handle throwable error during user posts retrieval.
                }
            }
        }
    }

    /**
     * Initializes the RecyclerView with a list of user posts.
     *
     * @param postsModelItems The list of user posts to be displayed.
     */
    private fun initRecyclerView(postsModelItems: List<PostsModelItem>) {
        userPostsAdapter = UserPostsAdapter(this, postsModelItems)
        binding.postRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = userPostsAdapter
        }
    }

    /**
     * Reads dummy user posts from a JSON file in the assets directory.
     *
     * @return List of dummy user posts.
     */
    private fun readDummyPostsFromJson(): List<PostsModelItem> {
        val jsonFileString = getJsonDataFromAsset(this, "DummyPost.json")
        val gson = Gson()
        val postsType = object : TypeToken<List<PostsModelItem>>() {}.type
        return gson.fromJson(jsonFileString, postsType)
    }
}