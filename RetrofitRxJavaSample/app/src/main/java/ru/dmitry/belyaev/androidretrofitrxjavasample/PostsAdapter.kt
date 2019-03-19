package ru.dmitry.belyaev.androidretrofitrxjavasample

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class PostsAdapter(var context: Context, var postsList: List<Post>) : RecyclerView.Adapter<PostsAdapter.PostHolder>() {

    inner class PostHolder(view: View) : RecyclerView.ViewHolder(view) {
        var txt_title: TextView = view.findViewById(R.id.txt_title)
        var txt_content: TextView = view.findViewById(R.id.txt_content)
        var txt_author: TextView = view.findViewById(R.id.txt_author)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.post_layout,
            parent, false)
        return PostHolder(view)
    }

    override fun getItemCount(): Int {
        return postsList.size
    }

    override fun onBindViewHolder(holder: PostHolder, position: Int) {
        holder.txt_title.text = postsList[position].title
        holder.txt_content.text = postsList[position].content.substring(0, 50)
        holder.txt_author.text = postsList[position].uid.toString()
    }


}