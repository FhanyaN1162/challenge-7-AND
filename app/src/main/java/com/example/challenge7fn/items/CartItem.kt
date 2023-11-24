package com.example.challenge7fn.items

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart_items")
data class CartItem(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    var foodName: String,
    var totalPrice: Int,
    var price: Int,
    var quantity: Int,
    var imageResourceId: String,
    var note: String? = ""
){
    fun calculateTotalPrice(): Int {
        return quantity * price
    }
}

