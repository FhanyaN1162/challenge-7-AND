package com.example.challenge7fn.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.challenge7fn.api.ApiClient
import com.example.challenge7fn.model.CategoryData
import com.example.challenge7fn.model.CategoryMenu
import com.example.challenge7fn.model.ListMenu
import com.example.challenge7fn.model.ListMenuData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {
    private val listMenu = MutableLiveData<List<ListMenuData>>()
    private val categories = MutableLiveData<List<CategoryData>>()
    fun setListMenu() {
        ApiClient.instance
            .getListMenu()
            .enqueue(object : Callback<ListMenu> {
                override fun onResponse(
                    call: Call<ListMenu>,
                    response: Response<ListMenu>
                ) {
                    if (response.isSuccessful){
                        listMenu.postValue(response.body()?.data)
                    }
                }
                override fun onFailure(call: Call<ListMenu>, t: Throwable) {
                    t.message?.let { Log.d("Failure", it) }
                }
            })

    }

    fun getListMenu(): LiveData<List<ListMenuData>> = listMenu

    fun setCategories() {
        ApiClient.instance
            .getCategoryMenu()
            .enqueue(object : Callback<CategoryMenu> {
                override fun onResponse(
                    call: Call<CategoryMenu>,
                    response: Response<CategoryMenu>
                ) {
                    if (response.isSuccessful){
                        categories.postValue(response.body()?.data)
                    }
                }
                override fun onFailure(call: Call<CategoryMenu>, t: Throwable) {
                    t.message?.let { Log.d("Failure", it) }
                }
            })

    }

    fun getCategories(): LiveData<List<CategoryData>> = categories
}