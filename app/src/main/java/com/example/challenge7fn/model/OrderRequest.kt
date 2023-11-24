package com.example.challenge7fn.model

data class OrderRequest(
    val orders: List<Order>,
    val total: Int?,
    val username: String?
)
