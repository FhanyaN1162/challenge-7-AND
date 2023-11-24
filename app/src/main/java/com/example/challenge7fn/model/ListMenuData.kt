package com.example.challenge7fn.model


import com.google.gson.annotations.SerializedName

data class ListMenuData(
    @SerializedName("alamatResto")
    val alamatResto: String?,
    @SerializedName("createdAt")
    val createdAt: String?,
    @SerializedName("detail")
    val detail: String?,
    @SerializedName("harga")
    val harga: Int?,
    @SerializedName("hargaFormat")
    val hargaFormat: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("imageUrl")
    val imageUrl: String?,
    @SerializedName("nama")
    val nama: String?,
    @SerializedName("updatedAt")
    val updatedAt: String?
)