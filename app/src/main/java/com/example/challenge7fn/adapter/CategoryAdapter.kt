package com.example.challenge7fn.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.challenge7fn.databinding.CategoryItemBinding
import com.example.challenge7fn.model.CategoryData

class CategoryAdapter(
    private val onItemClick: (CategoryData) -> Unit // Menambahkan parameter onClick
) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {
    private val categoryItems: ArrayList<CategoryData> = arrayListOf()

    fun setCategoryItems(menuItems: List<CategoryData>){
        this.categoryItems.clear()
        this.categoryItems.addAll(menuItems)
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: CategoryItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(categoryItem: CategoryData) {
            // Isi komponen-komponen tampilan dengan data dari objek Item
                Glide.with(itemView.context)
                    .load(categoryItem.imageUrl)
                    .into(binding.ivCategoryIcon)

            binding.tvCategoryName.text = categoryItem.nama

            // Menambahkan onClickListener untuk item
            itemView.setOnClickListener {
                onItemClick(categoryItem)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CategoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(categoryItems[position])
    }

    override fun getItemCount(): Int {
        return categoryItems.size
    }
}
