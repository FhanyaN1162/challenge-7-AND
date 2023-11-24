package com.example.challenge7fn

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.challenge7fn.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val currentUser = auth.currentUser

        if (currentUser != null) {
            // Pengguna sudah login, arahkan ke halaman utama
            val navHostFragment =
                supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
            navController = navHostFragment.navController
            binding.bottomNavigationView.setupWithNavController(navController)
        } else {
            // Pengguna belum login, arahkan ke halaman login
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}
