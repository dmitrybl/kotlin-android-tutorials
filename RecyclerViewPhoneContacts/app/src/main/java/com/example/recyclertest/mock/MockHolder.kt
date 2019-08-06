package com.example.recyclertest.mock

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclertest.ContactsAdapter
import com.example.recyclertest.R

class MockHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private var tvName: TextView = itemView.findViewById(R.id.tv_name)
    private var tvValue: TextView = itemView.findViewById(R.id.tv_value)

    private var id: String = ""

    fun bind(mock: Mock) {
        tvName.text = mock.name
        tvValue.text = mock.getValue()

        id = mock.getValue()
    }

    fun setListener(listener: ContactsAdapter.OnItemClickListener?) {
        itemView.setOnClickListener {
           listener!!.onItemClick(id)
        }
    }


}