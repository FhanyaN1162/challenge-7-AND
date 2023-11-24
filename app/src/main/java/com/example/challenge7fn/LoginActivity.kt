package com.example.challenge7fn

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.challenge7fn.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().getReference("users")

        binding.loginButton.setOnClickListener {
            val username = binding.usernameEditText.text.toString().trim()
            val password = binding.passwordEditText.text.toString()

            if (!Utils.isValidUsername(username) || password.isEmpty()) {
                Toast.makeText(this, "Input tidak valid", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            database.orderByChild("username").equalTo(username)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.exists()) {
                            for (userSnapshot in snapshot.children) {
                                val user = userSnapshot.getValue(User::class.java)
                                if (user != null) {
                                    // Masuk dengan email dan password
                                    auth.signInWithEmailAndPassword(user.email, password)
                                        .addOnCompleteListener { task ->
                                            if (task.isSuccessful) {
                                                Toast.makeText(applicationContext, "Login berhasil", Toast.LENGTH_SHORT).show()
                                                startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                                                finish()
                                            } else {
                                                Toast.makeText(applicationContext, "Login gagal: ${task.exception?.message}", Toast.LENGTH_SHORT
                                                ).show()
                                            }
                                        }
                                    return
                                }
                            }
                        }
                        Toast.makeText(applicationContext, "Username Tidak Ditemukan", Toast.LENGTH_SHORT).show()
                    }

                    override fun onCancelled(error: DatabaseError) {
                        // Handle errors
                    }
                })
        }

        binding.registerText.setOnClickListener {
            startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
            finish()
        }
    }
}
