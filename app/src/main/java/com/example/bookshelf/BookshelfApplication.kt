package com.example.bookshelf

import android.app.Application
import com.example.bookshelf.data.ApplicationContainer
import com.example.bookshelf.data.DefaultAppContainer

class BookshelfApplication: Application() {
    lateinit var applicationContainer: ApplicationContainer
    override fun onCreate() {
        super.onCreate()
        applicationContainer = DefaultAppContainer()
    }
}