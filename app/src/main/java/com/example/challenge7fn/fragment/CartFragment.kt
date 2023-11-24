package com.example.challenge7fn.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.challenge7fn.adapter.CartAdapter
import com.example.challenge7fn.databinding.FragmentCartBinding
import com.example.challenge7fn.viewmodel.CartViewModel

import org.koin.androidx.viewmodel.ext.android.viewModel

class CartFragment : Fragment() {

    private lateinit var binding: FragmentCartBinding
    private val viewModel: CartViewModel by viewModel()
    private lateinit var cartAdapter: CartAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCartBinding.inflate(inflater, container, false)

        setupRecyclerView()
        observeCartItems()

        binding.btnPlaceOrder.setOnClickListener {
            val navController = findNavController()
            val action = CartFragmentDirections.actionCartFragmentToConfirmOrderActivity()
            navController.navigate(action)
        }

        return binding.root
    }
    private fun setupRecyclerView() {
        cartAdapter = CartAdapter(viewModel) { cartItem ->

            viewModel.deleteCartItem(cartItem)

        }

        binding.recyclerView.apply {
            adapter = cartAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun observeCartItems() {
        viewModel.allCartItems.observe(viewLifecycleOwner) { cartItems ->
            cartAdapter.submitList(cartItems)
            val totalPrice = cartAdapter.calculateTotalPrice()
            binding.txtTotalPrice.text = "Total Price: Rp. $totalPrice"
        }
    }


}