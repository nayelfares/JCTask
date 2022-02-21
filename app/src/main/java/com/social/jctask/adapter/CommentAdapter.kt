package com.social.jctask.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.social.jctask.R
import com.social.jctask.room.Comment

class CommentAdapter (
            val context           : Context,
            val commentList       : List<Comment>
        ) : RecyclerView.Adapter<CommentAdapter.CommentsViewHolder>()  {
    inner class CommentsViewHolder(v: View) : RecyclerView.ViewHolder(v) {

        private var postId    : TextView     = v.findViewById(R.id.postId)
        private var commentId : TextView     = v.findViewById(R.id.commentId)
        private var name      : TextView     = v.findViewById(R.id.name)
        private var email     : TextView     = v.findViewById(R.id.email)
        private var body      : TextView     = v.findViewById(R.id.body)
        private var llSide    : LinearLayout = v.findViewById(R.id.ll_side)

        @SuppressLint("SetTextI18n")
        fun bind(comment: Comment){
            postId.text    = "${context.getText(R.string.post_id)}  ${comment.postId}"
            commentId.text = "${context.getText(R.string.comment_id)}  ${comment.id}"
            name.text      = "${context.getText(R.string.name)}  ${comment.name}"
            email.text     = "${context.getText(R.string.email)}  ${comment.email}"
            body.text      = "${context.getText(R.string.body)}  ${comment.body}"

            llSide.background.setTint(context.resources.getColor(R.color.blue))

        }
    }

    override fun getItemCount(): Int {
        return commentList.size
    }

    override fun onBindViewHolder(holder: CommentsViewHolder, position: Int) {
        holder.bind(commentList[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentsViewHolder {
        val inflatedView = LayoutInflater.from(parent.context).inflate(R.layout.comments_item, parent, false)
        return CommentsViewHolder(inflatedView)
    }
}