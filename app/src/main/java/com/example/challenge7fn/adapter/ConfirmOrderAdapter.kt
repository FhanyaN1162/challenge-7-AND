package com.example.challenge7fn.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.challenge7fn.databinding.OrderItemBinding
import com.example.challenge7fn.items.CartItem

class ConfirmOrderAdapter :
    ListAdapter<CartItem, ConfirmOrderAdapter.ViewHolder>(CartItemDiffCallback()) {

    inner class ViewHolder(private val binding: OrderItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(cartItem: CartItem) {
            Glide.with(itemView.context)
                .load(cartItem.imageResourceId)
                .into(binding.imgCartItem)
            binding.txtCartItemName.text = cartItem.foodName
            binding.txtCartItemPrice.text = "Rp. ${cartItem.totalPrice}"
            binding.txtItemQuantity.text = cartItem.quantity.toString()
            binding.txtCartNote.text = "Catatan: ${cartItem.note}"
        }
    }
    fun calculateTotalPrice(): Int {
        var totalPrice = 0
        currentList.forEach { cartItem ->
            totalPrice += cartItem.totalPrice
        }

        return totalPrice
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = OrderItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cartItem = getItem(position)
        holder.bind(cartItem)
    }

    private class CartItemDiffCallback : DiffUtil.ItemCallback<CartItem>() {
        override fun areItemsTheSame(oldItem: CartItem, newItem: CartItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CartItem, newItem: CartItem): Boolean {
            return oldItem == newItem
        }
    }
}

