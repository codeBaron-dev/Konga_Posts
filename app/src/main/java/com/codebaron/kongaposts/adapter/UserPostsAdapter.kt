package com.codebaron.kongaposts.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.codebaron.kongaposts.R
import com.codebaron.kongaposts.model.PostsModelItem
import de.hdodenhof.circleimageview.CircleImageView

/**
 * @author Anyanwu Nicholas(codeBaron)
 * @since 16-11-2023
 */

/**
 * Adapter class for displaying user posts in a RecyclerView.
 * @param context The context of the application.
 * @param posts List of user posts to be displayed.
 */
class UserPostsAdapter(private val context: Context, private val posts: List<PostsModelItem>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    /**
     * Creates and returns a ViewHolder based on the viewType.
     * @param parent The ViewGroup into which the new View will be added.
     * @param viewType The type of the new View.
     * @return A new ViewHolder that holds a View of the given type.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return PostsViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.users_post_items, parent, false)
        )
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in the data set.
     */
    override fun getItemCount() = posts.size

    /**
     * Called by RecyclerView to display the data at the specified position.
     *
     * @param holder The ViewHolder which should be updated to represent the contents of the item at the given position.
     * @param position The position of the item within the adapter's data set.
     */
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is PostsViewHolder -> {
                holder.bind(posts[position])
            }
        }
    }

    /**
     * ViewHolder class representing an item view for user posts.
     *
     * @param itemView The inflated view for a user post item.
     */
    class PostsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        // Views within the item view.
        private val profilePicture: CircleImageView = itemView.findViewById(R.id.profile_picture)
        private val name: TextView = itemView.findViewById(R.id.name)
        private val title: TextView = itemView.findViewById(R.id.title)
        private val description: TextView = itemView.findViewById(R.id.description)

        fun bind(postsModel: PostsModelItem) {

            // Bind data to views.
            name.text = postsModel.name
            title.text = postsModel.username
            description.text = postsModel.company.catchPhrase
            // Load profile picture using Glide.
            Glide.with(profilePicture.context)
                .load(postsModel.avatar)
                .apply(RequestOptions.circleCropTransform())
                .into(profilePicture)
        }
    }
}