package com.example.challenge7fn.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.challenge7fn.databinding.MenuItemBinding
import com.example.challenge7fn.model.ListMenuData

class MenuAdapter(
    private val onItemClick: (ListMenuData) -> Unit // Menambahkan parameter onClick
) : RecyclerView.Adapter<MenuAdapter.ViewHolder>() {
    private val menuItems: ArrayList<ListMenuData> = arrayListOf()

    fun setMenuItems(menuItems: List<ListMenuData>){
        this.menuItems.clear()
        this.menuItems.addAll(menuItems)
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: MenuItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(menuItem: ListMenuData) {
            // Isi komponen-komponen tampilan dengan data dari objek MenuItem

            Glide.with(itemView.context)
                .load(menuItem.imageUrl)
                .into(binding.imgFood)

            binding.txtFoodName.text = menuItem.nama
            binding.txtFoodPrice.text = menuItem.hargaFormat

            // Menambahkan onClickListener untuk item
            itemView.setOnClickListener {
                onItemClick(menuItem)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = MenuItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(menuItems[position])
    }

    override fun getItemCount(): Int {
        return menuItems.size
    }
}
