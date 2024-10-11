package com.example.tvapp.adapter


import android.animation.ObjectAnimator
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tvapp.R

data class MenuItem(val title: String, val iconUrl: String, val identificator: String)

class MenuAdapter(
    private val items: List<MenuItem>,
    private val context: Context,
    private val onItemClick: (MenuItem) -> Unit
) : RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {

    inner class MenuViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val icon: ImageView = itemView.findViewById(R.id.menu_icon)
        val title: TextView = itemView.findViewById(R.id.menu_title)

        init {
            itemView.setOnClickListener {
                onItemClick(items[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_menu, parent, false)
        return MenuViewHolder(view)
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        val menuItem = items[position]

        Glide.with(context)
            .load(menuItem.iconUrl)
            .into(holder.icon)

        holder.title.text = menuItem.title


        holder.itemView.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                expandMenu(holder.title)
            } else {
                collapseMenu(holder.title)
            }
        }
    }

    override fun getItemCount(): Int = items.size

    private fun expandMenu(textView: TextView) {
        textView.layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT
        textView.requestLayout()
        ObjectAnimator.ofFloat(textView, "alpha", 1f, 1f).apply {
            duration = 200
            start()
        }
    }

    private fun collapseMenu(textView: TextView) {
        textView.layoutParams.width = 0
        textView.requestLayout() 
        ObjectAnimator.ofFloat(textView, "alpha", 1f, 1f).apply {
            duration = 200
            start()
        }
    }
}