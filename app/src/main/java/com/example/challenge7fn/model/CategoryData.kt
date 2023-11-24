package com.example.challenge7fn.model


import com.google.gson.annotations.SerializedName

data class CategoryData(
    @SerializedName("createdAt")
    val createdAt: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("imageUrl")
    val imageUrl: String?,
    @SerializedName("nama")
    val nama: String?,
    @SerializedName("updatedAt")
    val updatedAt: String?
)