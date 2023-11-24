package com.example.challenge7fn

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.challenge7fn.adapter.ConfirmOrderAdapter
import com.example.challenge7fn.databinding.ActivityConfirmOrderBinding
import com.example.challenge7fn.model.Order
import com.example.challenge7fn.model.OrderRequest
import com.example.challenge7fn.viewmodel.CartViewModel

import com.google.firebase.auth.FirebaseAuth
import org.koin.androidx.viewmodel.ext.android.viewModel

class ConfirmOrderActivity : AppCompatActivity() {

    private lateinit var binding: ActivityConfirmOrderBinding
    private val viewModel: CartViewModel by viewModel()
    private lateinit var confirmOrderAdapter: ConfirmOrderAdapter
    private lateinit var orderItems:ArrayList<Order>
    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConfirmOrderBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        confirmOrderAdapter = ConfirmOrderAdapter()

        setupRecyclerView()
        setupOrder()
        observeCartItems()

        binding.btnBack2.setOnClickListener{
            finish()
        }

        binding.btnPlaceOrder.setOnClickListener {
            if(!this::orderItems.isInitialized){
                return@setOnClickListener
            }
            val deliveryMethod = if (binding.btnPickUp.isChecked) {
                binding.btnPickUp.text.toString()
            } else if (binding.btnDelivery.isChecked){
                binding.btnDelivery.text.toString()
            } else {
                ""
            }

            val paymentMethod = if (binding.btnCash.isChecked) {
                binding.btnCash.text.toString()
            } else if (binding.btnDigital.isChecked){
                binding.btnDigital.text.toString()
            }else{
                ""
            }

            val totalPrice = confirmOrderAdapter.calculateTotalPrice()
            val orderSummary = "Pesanan seharga: Rp. $totalPrice\n" +
                    "Metode Pengiriman: $deliveryMethod\n" +
                    "Metode Pembayaran: $paymentMethod"

            if (deliveryMethod.isEmpty() || paymentMethod.isEmpty()){
                Toast.makeText(this@ConfirmOrderActivity, "Metode masih ada yang kosong!", Toast.LENGTH_SHORT).show()
            }else{
                val alertDialog = AlertDialog.Builder(this)
                    .setTitle("Order Summary")
                    .setMessage(orderSummary)
                    .setPositiveButton("OK") { _, _ ->
                        var total = 0
                        for(order in orderItems){
                            total+=order.qty!! * order.harga!!
                        }
                        viewModel.order(OrderRequest(orderItems, total,
                            auth.currentUser?.displayName
                        ))
                    }
                    .create()
                alertDialog.show()
            }
        }
    }

    private fun setupOrder() {
        viewModel.getOrderResult().observe(this){
            if(it.status == true){
                viewModel.deleteAllCartItems()
                // Navigate back to the home page
                startActivity(Intent(this, MainActivity::class.java))
                finishAffinity() // Finish activity ini agar tidak bisa kembali dengan back button
            }
        }
    }

    private fun setupRecyclerView() {

        binding.recyclerView.apply {
            adapter = confirmOrderAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }
    @SuppressLint("SetTextI18n")
    private fun observeCartItems() {
        viewModel.allCartItems.observe(this) { cartItems ->
            confirmOrderAdapter.submitList(cartItems)
            this.orderItems = arrayListOf()
            for (cart in cartItems) {
                orderItems.add(Order(cart.note, cart.price, cart.foodName, cart.quantity))
            }
            val totalPrice = confirmOrderAdapter.calculateTotalPrice()
            binding.txtTotalPayment.text = "Total Price: Rp. $totalPrice"
        }

    }
}