package com.example.challenge7fn.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.challenge7fn.api.ApiClient
import com.example.challenge7fn.items.CartItem
import com.example.challenge7fn.model.OrderRequest
import com.example.challenge7fn.model.OrderResponse
import com.example.challenge7fn.repository.CartRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CartViewModel(private val repository: CartRepository) : ViewModel() {

    val allCartItems: LiveData<List<CartItem>> = repository.allCartItems

    private val _quantity = MutableLiveData<Int>()
    val quantity: LiveData<Int> get() = _quantity

    private val _totalPrice = MutableLiveData<Int>()
    val totalPrice: LiveData<Int> get() = _totalPrice

    private val _pricePerItem = MutableLiveData<Int>()
    val pricePerItem: LiveData<Int> get() = _pricePerItem

    private val orderResult = MutableLiveData<OrderResponse>()
    fun getOrderResult(): LiveData<OrderResponse> = orderResult

    fun increaseQuantity() {
        _quantity.value = (_quantity.value ?: 0) + 1
        updateTotalPrice(_pricePerItem.value ?: 0)
    }

    fun decreaseQuantity() {
        val currentQuantity = _quantity.value ?: 0
        if (currentQuantity > 1) {
            _quantity.value = currentQuantity - 1
            updateTotalPrice(_pricePerItem.value ?: 0)
        }
    }

    fun setPricePerItem(price: Int) {
        _pricePerItem.value = price
    }

    fun updateTotalPrice(pricePerItem: Int) {
        _totalPrice.value = (_quantity.value ?: 0) * pricePerItem
    }

    fun insertCartItem(cartItem: CartItem) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val cart = repository.getCartByFoodName(cartItem.foodName)
                if (cart == null) {
                    repository.insertCartItem(cartItem)
                } else {
                    cart.quantity += cartItem.quantity
                    repository.updateCartItem(cart)
                }
            }
        }
    }

    fun updateCartItem(cartItem: CartItem) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateCartItem(cartItem)
        }
    }

    fun deleteCartItem(cartItem: CartItem) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repository.deleteCartItem(cartItem)
            }
        }
    }

    fun deleteAllCartItems() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repository.deleteAllCartItems()
            }
        }
    }

    fun order(orderRequest: OrderRequest) {
        ApiClient.instance
            .order(orderRequest)
            .enqueue(object : Callback<OrderResponse> {
                override fun onResponse(
                    call: Call<OrderResponse>,
                    response: Response<OrderResponse>
                ) {
                    if (response.isSuccessful) {
                        orderResult.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<OrderResponse>, t: Throwable) {
                    t.message?.let {
                        Log.d("Failure", it)
                    }
                }
            })
    }
}