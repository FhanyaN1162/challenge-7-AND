package com.example.challenge7fn.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.challenge7fn.items.CartItem

@Dao
interface CartItemDao {

    @Query("SELECT * FROM cart_items")
    fun getAllCartItems(): LiveData<List<CartItem>>

    @Query("SELECT * FROM cart_items WHERE foodName = :foodName LIMIT 1")
    fun getCartItemByFoodName(foodName: String): CartItem?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertCartItem(cartItem: CartItem)

    @Update
    fun updateCartItem(cartItem: CartItem)

    @Delete
    fun deleteCartItem(cartItem: CartItem)

    @Query("DELETE FROM cart_items")
    fun deleteAllCartItems()


}
