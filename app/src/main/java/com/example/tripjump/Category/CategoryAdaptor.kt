package com.example.tripjump.Category

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tripjump.R

class CategoryAdaptor(val list: List<IGetViewCategory>, val callback: ((IGetViewCategory) -> Unit)): RecyclerView.Adapter<ViewHolderList>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolderList {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.category_item, parent, false)
        return ViewHolderList(itemView, callback)
    }

    override fun onBindViewHolder(holder: ViewHolderList, position: Int) {
        holder.characterList.text = list[position].GetCaption()
        holder.AddInstance(list[position])
    }

    override fun getItemCount(): Int = list.size
}