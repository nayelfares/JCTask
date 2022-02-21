package com.social.jctask.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.social.jctask.R
import com.social.jctask.room.Post

class PostAdapter(
    val context:Context,
    var posts:List<Post>,
    private val itemClick: ItemClick
)  : RecyclerView.Adapter<PostAdapter.PostViewHolder>() {
    override fun getItemCount(): Int {
        return posts.size
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(posts[position])
        holder.itemView.setOnClickListener { itemClick.onItemClick(position,posts[position]) }

        holder.ivFav.setOnClickListener {
            itemClick.favoriteClick(holder.ivFav,position ,posts[position])
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PostViewHolder {
        val inflatedView =
            LayoutInflater.from(parent.context).inflate(R.layout.post_item, parent, false)
        return PostViewHolder(inflatedView)
    }

    inner class PostViewHolder(v: View) : RecyclerView.ViewHolder(v){
        private var titleTextView: TextView = v.findViewById(R.id.title)
        private var uidTextView: TextView = v.findViewById(R.id.uid)
        private var bodyTextView: TextView = v.findViewById(R.id.body)
        var ivFav: ImageView = v.findViewById(R.id.iv_fav)
        private var llSide: LinearLayout = v.findViewById(R.id.ll_side)

        @SuppressLint("SetTextI18n")
        fun bind(post: Post) {
            uidTextView.text   = "${context.getText(R.string.user_id)} ${post.userId}"
            titleTextView.text = "${context.getText(R.string.post_title)} ${post.title}"
            bodyTextView.text  = "${context.getText(R.string.post_body)} ${post.body}"

            llSide.background.setTint(ContextCompat.getColor(context, R.color.colorPrimary))

            if (post.isFavorite!!) {
                ivFav.setColorFilter(Color.RED)
            } else {
                ivFav.setColorFilter(Color.GRAY)
            }
        }
    }
}