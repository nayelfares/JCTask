package com.social.jctask.adapter

import android.widget.ImageView
import com.social.jctask.room.Post

interface ItemClick {
    fun onItemClick(position:Int,post: Post)
    fun favoriteClick(imageView: ImageView, position:Int, post: Post)
}