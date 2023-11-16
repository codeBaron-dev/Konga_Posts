package com.codebaron.kongaposts.model

data class PostsModelItem(
    val address: Address,
    val avatar: String,
    val company: Company,
    val email: String,
    val id: Int,
    val name: String,
    val phone: String,
    val username: String,
    val website: String
)