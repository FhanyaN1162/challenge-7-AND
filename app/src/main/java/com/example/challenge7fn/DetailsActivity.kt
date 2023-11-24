package com.example.challenge7fn

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.challenge7fn.databinding.ActivityDetailsBinding
import com.example.challenge7fn.items.CartItem
import com.example.challenge7fn.viewmodel.CartViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsBinding
    private lateinit var googleMapsUrl: String
    private var quantity = 0
    private var pricePerItem = 0
    private var totalPrice = 0
    private val viewModel: CartViewModel by viewModel()

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val bundle = intent.extras
        val name = bundle?.getString("name")
        val price = bundle?.getInt("price")
        val description = bundle?.getString("description")
        val imageRes = bundle?.getString("imageRes")
        val restaurantAddress = bundle?.getString("restaurantAddress")
        googleMapsUrl = bundle!!.getString("googleMapsUrl", "")

        if (binding.btnAddToCart2.text == "Tambah Ke Keranjang - Rp.0") {
            binding.btnAddToCart2.text = "Tambah Ke Keranjang - Rp. $price"
        }

        pricePerItem = price!!

        imageRes?.let { updateUI(name, price.toString(), description, it, restaurantAddress) }

        binding.btnBack.setOnClickListener {
            finish()
        }

        // Set the pricePerItem in the ViewModel
        viewModel.setPricePerItem(pricePerItem)

        // Observe LiveData for pricePerItem
        viewModel.pricePerItem.observe(this) { updatedPricePerItem ->
            pricePerItem = updatedPricePerItem
        }

        // Observe LiveData for quantity and total price
        viewModel.quantity.observe(this) { updatedQuantity ->
            updateQuantity(updatedQuantity)
        }

        viewModel.totalPrice.observe(this) { updatedTotalPrice ->
            totalPrice = updatedTotalPrice
            binding.btnAddToCart2.text = "Tambah Ke Keranjang - Rp. $totalPrice"
        }

        //  tombol tambah
        binding.btnIncrease2.setOnClickListener {
            viewModel.increaseQuantity()
        }

        // tombol kurang
        binding.btnDecrease2.setOnClickListener {
            viewModel.decreaseQuantity()
        }

        //  tombol "Tambah Ke Keranjang"
        binding.btnAddToCart2.setOnClickListener {
            // Ambil data makanan untuk disimpan ke dalam keranjang
            val cartItem = CartItem(
                foodName = name.toString(),
                totalPrice = totalPrice,
                price = price,
                quantity = quantity,
                imageResourceId = imageRes!!,
            )
            if (quantity > 0) {
                // Simpan item ke dalam keranjang
                viewModel.insertCartItem(cartItem)

                // Tampilkan pesan berhasil menambahkan ke keranjang
                Toast.makeText(this@DetailsActivity, "Item ditambahkan ke keranjang", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this@DetailsActivity, "Jumlah item tidak boleh 0!", Toast.LENGTH_SHORT).show()
            }
        }

        binding.txtGoogleMaps.setOnClickListener {
            // Memeriksa apakah URL Google Maps tidak kosong
            if (googleMapsUrl.isNotEmpty()) {
                // Membuka tautan Google Maps di browser
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(googleMapsUrl))
                startActivity(intent)
            }
        }
    }

    private fun updateQuantity(updatedQuantity: Int) {
        quantity = updatedQuantity
        binding.txtItemQuantity2.text = quantity.toString()
        totalPrice = quantity * pricePerItem
    }

    @SuppressLint("SetTextI18n")
    private fun updateUI(name: String?, price: String?, description: String?, imageRes: String, restaurantAddress: String?) {
        binding.apply {
            Glide.with(this@DetailsActivity)
                .load(imageRes)
                .into(imgFood)
            txtFoodName.text = name
            txtFoodPrice.text = "Rp. $price"
            txtFoodDescription.text = description
            txtLocation.text = restaurantAddress
            txtGoogleMaps.text = googleMapsUrl
        }
    }
}
