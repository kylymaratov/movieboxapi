package com.example.tvapp

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.exmaple.tvapp.DataModel
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

class MainActivity : Fragment() {

    lateinit var txtTitle: TextView
    lateinit var txtCountry: TextView
    lateinit var txtDescription: TextView

    lateinit var imgBanner: ImageView
    lateinit var listFragment: ListFragment

      fun init( ) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imgBanner = findViewById(R.id.img_banner)
        txtTitle = findViewById(R.id.title)
        txtCountry = findViewById(R.id.country)
        txtDescription = findViewById(R.id.desciption)


        listFragment = ListFragment()

        var transaction = supportFragmentManager.beginTransaction()

        transaction.add(R.id.list_fragment, listFragment);

        transaction.commit()

        val gson = Gson()

        val i: InputStream = this.assets.open("movies.json")
        val br = BufferedReader(InputStreamReader(i))
        val dataList: DataModel = gson.fromJson(br, DataModel::class.java)

        listFragment.bindData(dataList)

        listFragment.setOnContentSelectedListener {
            updateBanner(it)
        }
    }

    fun updateBanner(dataList:  DataModel.Result.Detail) {
        txtTitle.text = dataList.title
        txtCountry.text = "Страна : " + dataList.country

        val url = dataList.poster_url

        Glide.with(this).load(url).into(imgBanner)
    }
    }
