package com.example.challenge7fn.adapter

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.challenge7fn.databinding.CartItemBinding
import com.example.challenge7fn.items.CartItem
import com.example.challenge7fn.viewmodel.CartViewModel

class CartAdapter(private val viewModel: CartViewModel,
                  private val onItemClick: (CartItem) -> Unit) :
    ListAdapter<CartItem, CartAdapter.CartViewHolder>(DIFF_CALLBACK)
{

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = CartItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(binding)
    }


    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val currentItem = getItem(position)

        holder.bind(currentItem)

        holder.binding.btnIncrease3.setOnClickListener {
            // Menambah jumlah item
            currentItem.quantity++
            updateCartItem(currentItem)
            currentItem.totalPrice = currentItem.calculateTotalPrice()
            notifyItemChanged(holder.bindingAdapterPosition)
        }

        holder.binding.btnDecrease3.setOnClickListener {
            // Mengurangi jumlah item
            currentItem.quantity--
            currentItem.totalPrice = currentItem.calculateTotalPrice()
            if (currentItem.quantity < 1) {

                showDeleteConfirmationDialog(holder)
            }
            updateCartItem(currentItem)
            notifyItemChanged(holder.bindingAdapterPosition)
        }

        holder.binding.btnDelete.setOnClickListener {
            showDeleteConfirmationDialog(holder)
        }

        holder.binding.etNotesItem.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                holder.binding.etNotesItem.clearFocus()
                currentItem.note = holder.binding.etNotesItem.text.toString().trim()
                updateCartItem(currentItem)
                Log.d("TAG", "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX")
                true
            }
            false
        }
    }
    fun calculateTotalPrice(): Int {
        var totalPrice = 0
        currentList.forEach { cartItem ->
            totalPrice += cartItem.totalPrice
        }

        return totalPrice
    }

    private fun updateCartItem(cartItem: CartItem) {
        viewModel.updateCartItem(cartItem)
    }


    inner class CartViewHolder(val binding: CartItemBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(cartItem: CartItem) {
            Glide.with(itemView.context)
                .load(cartItem.imageResourceId)
                .into(binding.imgCartItem)
            binding.txtCartItemName.text = cartItem.foodName
            binding.txtCartItemPrice.text = "Rp. ${cartItem.totalPrice}"
            binding.txtItemQuantity.text = cartItem.quantity.toString()
            binding.etNotesItem.setText(cartItem.note)

        }
    }
    private fun showDeleteConfirmationDialog(holder: CartViewHolder) {
        val position = holder.bindingAdapterPosition
        if (position != RecyclerView.NO_POSITION) {
            val cartItem = getItem(position)

            AlertDialog.Builder(holder.itemView.context)
                .setTitle("Delete Item")
                .setMessage("Anda yakin ingin menghapus Item ini?")
                .setPositiveButton("Delete") { _, _ ->
                    onItemClick(cartItem)
                }
                .setNegativeButton("Cancel", null)
                .show()
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<CartItem>() {
            override fun areItemsTheSame(oldItem: CartItem, newItem: CartItem): Boolean {
                return oldItem.foodName == newItem.foodName
            }

            override fun areContentsTheSame(oldItem: CartItem, newItem: CartItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}
