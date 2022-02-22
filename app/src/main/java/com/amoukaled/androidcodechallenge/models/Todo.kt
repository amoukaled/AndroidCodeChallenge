package com.amoukaled.androidcodechallenge.models

import com.google.gson.annotations.SerializedName


/**
 * The api tod'o resource
 */
data class Todo(
    @SerializedName("userId")
    val userId: Long,

    @SerializedName("id")
    val id: Long,

    @SerializedName("title")
    val title: String,

    @SerializedName("completed")
    val completed: Boolean
)