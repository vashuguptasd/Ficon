package com.example.ficon

import android.app.Application
import com.google.firebase.database.FirebaseDatabase

class FiconActivity : Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseDatabase.getInstance().setPersistenceEnabled(true)
    }
}