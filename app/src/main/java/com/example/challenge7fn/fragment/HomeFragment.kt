package com.example.challenge7fn.fragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.challenge7fn.R
import com.example.challenge7fn.adapter.CategoryAdapter
import com.example.challenge7fn.adapter.MenuAdapter
import com.example.challenge7fn.databinding.FragmentHomeBinding
import com.example.challenge7fn.viewmodel.HomeViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var menuAdapter: MenuAdapter
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var sharedPrefs: SharedPreferences
    private val prefName = "MyPrefs"
    private lateinit var layoutManagerGrid: GridLayoutManager
    private lateinit var layoutManagerLinear: LinearLayoutManager
    private lateinit var currentLayoutManager: RecyclerView.LayoutManager
    private val viewModel: HomeViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        sharedPrefs = requireActivity().getSharedPreferences(prefName, Context.MODE_PRIVATE)

        // Inisialisasi LayoutManager untuk Grid dan Linear
        layoutManagerGrid = GridLayoutManager(requireContext(), 2)
        layoutManagerLinear = LinearLayoutManager(requireContext())

        // Inisialisasi LayoutManager saat aplikasi pertama kali dibuka
        val savedLayout = sharedPrefs.getString("layout", "grid") // "grid" adalah nilai default jika tidak ada yang tersimpan

        if (savedLayout == "grid") {
            currentLayoutManager = layoutManagerGrid
            binding.changeLayout.setImageResource(R.drawable.categories)
        } else {
            currentLayoutManager = layoutManagerLinear
            binding.changeLayout.setImageResource(R.drawable.list)
        }

        binding.recyclerViewMenuGrid.layoutManager = currentLayoutManager


        binding.changeLayout.setOnClickListener {
            // Mengubah tata letak saat tombol diklik

            if (currentLayoutManager == layoutManagerGrid) {
                currentLayoutManager = layoutManagerLinear
                binding.changeLayout.setImageResource(
                    R.drawable.list
                )
                sharedPrefs.edit().putString("layout", "linear").apply()

            } else {
                currentLayoutManager = layoutManagerGrid
                binding.changeLayout.setImageResource(
                    R.drawable.categories
                )
                sharedPrefs.edit().putString("layout", "grid").apply()
            }
            binding.recyclerViewMenuGrid.layoutManager = currentLayoutManager

        }

        binding.rvCategories.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL, false)

        setupMenu()
        setupViewModel()
        return binding.root
    }

    private fun setupViewModel() {
        viewModel.getListMenu().observe(viewLifecycleOwner){
            if (it != null) {
                menuAdapter.setMenuItems(it)
            }
        }
        viewModel.getCategories().observe(viewLifecycleOwner){
            if(it != null){
                categoryAdapter.setCategoryItems(it)
            }
        }
        viewModel.setListMenu()
        viewModel.setCategories()
    }

    private fun setupMenu() {
        menuAdapter = MenuAdapter{ selectedItem ->
            val bundle = Bundle()
            bundle.putString("name", selectedItem.nama)
            bundle.putInt("price", selectedItem.harga!!)
            bundle.putString("description", selectedItem.detail)
            bundle.putString("imageRes", selectedItem.imageUrl)
            bundle.putString("restaurantAddress", "Alamat Restaurant")
            bundle.putString("googleMapsUrl", selectedItem.alamatResto)

            val navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
            navController.navigate(R.id.detailsActivity, bundle)
        }

        binding.recyclerViewMenuGrid.adapter = menuAdapter

        categoryAdapter = CategoryAdapter {
            Toast.makeText(requireContext(),it.nama,Toast.LENGTH_SHORT).show()
        }

        binding.rvCategories.adapter = categoryAdapter
    }

}
