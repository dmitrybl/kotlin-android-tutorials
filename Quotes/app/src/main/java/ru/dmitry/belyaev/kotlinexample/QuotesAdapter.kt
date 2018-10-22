package ru.dmitry.belyaev.kotlinexample

import android.support.v7.widget.RecyclerView
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.source_item.view.*
import ru.dmitry.belyaev.kotlinexample.data.Quote
import ru.dmitry.belyaev.kotlinexample.data.SourceOfQuotes

/**
 * Created by dmitrybelyaev on 22.10.2018.
 */

class QuotesAdapter(list: MutableList<Quote>) : RecyclerView.Adapter<QuotesAdapter.ViewHolder>() {

    private val mItems: MutableList<Quote> = list

    override fun getItemCount(): Int {
        return mItems.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent!!.context)
        val view = layoutInflater.inflate(R.layout.source_item, parent, false)
        return ViewHolder(view).listen { position, type ->

        }
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        val item = mItems[position]
        holder!!.textview.text = Html.fromHtml(item.htmlText)
    }


    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val textview = view.text!!
    }

    fun <T: RecyclerView.ViewHolder> T.listen(event: (position: Int, type: Int) -> Unit): T {
        itemView.setOnClickListener {
            event.invoke(adapterPosition, getItemViewType())
        }
        return this
    }

    operator fun get(position: Int): Quote {
        return mItems[position]
    }

}