package com.example.challenge7fn.di

import com.example.challenge7fn.database.AppDatabase
import com.example.challenge7fn.repository.CartRepository
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.Module
import org.koin.dsl.module
import com.example.challenge7fn.viewmodel.CartViewModel
import com.example.challenge7fn.viewmodel.HomeViewModel
object AppModules {

    private val localModule = module {
        single { AppDatabase.getDatabase(androidContext()) }
        single { get<AppDatabase>().cartItemDao() }
    }

    private val repositoryModule = module {
        single { CartRepository(get()) }
    }

    private val viewModelModule = module {
        viewModelOf(::CartViewModel)
        viewModelOf(::HomeViewModel)
    }

    val modules: List<Module> = listOf(
        localModule,
        repositoryModule,
        viewModelModule
    )
}
