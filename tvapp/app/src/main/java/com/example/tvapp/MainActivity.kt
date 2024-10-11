package com.example.tvapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tvapp.adapter.MenuAdapter
import com.example.tvapp.adapter.MenuItem
import com.example.tvapp.api.Repository
import com.example.tvapp.fragments.HomeFragment
import com.example.tvapp.fragments.SearchFragment
import kotlinx.coroutines.launch

class MainActivity : FragmentActivity() {
    lateinit var repository: Repository
    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recycler_view_menu)
        repository = (application as MyApplication).repository

        lifecycleScope.launch {
            val menuList = repository.getMenu()

            menuList?.let {
                val menuItems = mutableListOf<MenuItem>()

                menuItems.add(MenuItem("Поиск", "https://cdn-icons-png.flaticon.com/512/122/122932.png", ""))

                menuList.result.forEach { menuItem ->
                    menuItems.add(MenuItem(menuItem.title, menuItem.icon, menuItem.identificator))
                }

                recyclerView.layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
                recyclerView.adapter = MenuAdapter(menuItems, this@MainActivity) { selectedItem ->
                    if (selectedItem.title == "Поиск") {
                        changeFragment(SearchFragment())
                    } else {
                        val fragment = HomeFragment.newInstance(selectedItem.title, selectedItem.identificator)
                        changeFragment(fragment)
                    }
                }


                if (menuList.result.isNotEmpty()) {
                    val firstItem = menuList.result[0]
                    val firstFragment = HomeFragment.newInstance(firstItem.title, firstItem.identificator)
                    changeFragment(firstFragment)
                }
            }
        }
    }

    private fun changeFragment(fragment: Fragment) {
        repository.clearMovies()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.commit()
    }
}