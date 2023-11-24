package com.example.challenge7fn.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.challenge7fn.LoginActivity
import com.example.challenge7fn.User
import com.example.challenge7fn.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private val auth = FirebaseAuth.getInstance()
    private val database = FirebaseDatabase.getInstance()
    private val usersRef = database.getReference("users")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)

        // Mendapatkan ID pengguna yang saat ini login
        val userId = auth.currentUser?.uid

        if (userId != null) {
            // Dapatkan data pengguna dari Firebase Realtime Database
            usersRef.child(userId).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        val user = snapshot.getValue(User::class.java)
                        if (user != null) {
                            binding.usernameTextView.text = user.username
                            binding.emailTextView.text = user.email
                            binding.phoneTextView.text = user.phone
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    // Handle any errors here
                }
            })
        }

        binding.logoutButton.setOnClickListener {
            // Lakukan proses logout dan kembali ke halaman login
            auth.signOut()
            startActivity(Intent(requireContext(), LoginActivity::class.java))
            activity?.finish()
        }

        return binding.root
    }
}
