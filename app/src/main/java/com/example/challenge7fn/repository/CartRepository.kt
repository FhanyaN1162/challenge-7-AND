package com.example.challenge7fn.repository

import androidx.lifecycle.LiveData
import com.example.challenge7fn.database.CartItemDao
import com.example.challenge7fn.items.CartItem

class CartRepository(private val cartItemDao: CartItemDao) {

    val allCartItems: LiveData<List<CartItem>> = cartItemDao.getAllCartItems()

    fun insertCartItem(cartItem: CartItem) {
        cartItemDao.insertCartItem(cartItem)
    }

    fun deleteCartItem(cartItem: CartItem) {
        cartItemDao.deleteCartItem(cartItem)
    }
    fun updateCartItem(cartItem: CartItem) {
        cartItemDao.updateCartItem(cartItem)
    }
    fun deleteAllCartItems() {
        cartItemDao.deleteAllCartItems()
    }
    fun getCartByFoodName(foodName:String):CartItem?{
        return cartItemDao.getCartItemByFoodName(foodName)
    }

}
