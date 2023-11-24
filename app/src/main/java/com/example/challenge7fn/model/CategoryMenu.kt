package com.example.challenge7fn.model


import com.google.gson.annotations.SerializedName

data class CategoryMenu(
    @SerializedName("data")
    val data: List<CategoryData>,
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: Boolean?
)