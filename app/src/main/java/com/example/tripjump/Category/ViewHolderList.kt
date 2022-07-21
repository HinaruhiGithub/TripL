package com.example.tripjump.Category

import android.util.Log
import android.view.View
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.tripjump.R

class ViewHolderList (item: View, callback: ((IGetViewCategory) -> Unit)) : RecyclerView.ViewHolder(item) {
    private val TAG = ViewHolderList::class.simpleName
    val characterList: Button = item.findViewById(R.id.category_button)
    private var categoryInstance: IGetViewCategory? = null

    init {
        characterList.setOnClickListener {
            Log.d(TAG, "押されたで")
            if(categoryInstance != null) {
                callback.invoke(categoryInstance!!)
            } else {
                throw NullPointerException("まだボタンnullだよ")
            }
        }
    }

    fun AddInstance(instance: IGetViewCategory) {
        categoryInstance = instance
    }
}