package com.example.tvapp

import android.app.Application
import com.example.tvapp.api.ApiService
import com.example.tvapp.api.RetrofitHelper
import com.example.tvapp.api.TsKgRepo

class MyApplication : Application() {

    lateinit var tsKgRepo : TsKgRepo

    override fun onCreate() {
        super.onCreate()

        init()
    }

    private fun init(){
        val service = RetrofitHelper.create()
        tsKgRepo = TsKgRepo(service)
    }
}