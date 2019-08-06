package com.example.recyclertest.mock

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclertest.R

class MockAdapter: RecyclerView.Adapter<MockHolder>() {

    private var mockList: ArrayList<Mock> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MockHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.list_item_mock, parent, false)
        return MockHolder(view)
    }

    override fun getItemCount() = mockList.size

    override fun onBindViewHolder(holder: MockHolder, position: Int) {
        holder.bind(mockList[position])
    }


     fun addData(data: List<Mock>, refresh: Boolean) {
         if(refresh) {
             mockList.clear()
         }

         mockList.addAll(data)
         notifyDataSetChanged()
    }

}