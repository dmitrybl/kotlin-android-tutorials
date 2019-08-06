package com.example.recyclertest

import android.database.Cursor
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclertest.mock.Mock
import com.example.recyclertest.mock.MockHolder

class ContactsAdapter : RecyclerView.Adapter<MockHolder>() {

    private var cursor: Cursor? = null
    private var listener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MockHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.list_item_mock, parent, false)
        return MockHolder(view)
    }

    override fun getItemCount(): Int {
        return cursor?.count ?: 0
    }

    override fun onBindViewHolder(holder: MockHolder, position: Int) {
        if(cursor?.moveToPosition(position)!!) {
            val name = cursor?.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)?.let { cursor?.getString(it) }
            val id = cursor?.getColumnIndex(ContactsContract.Contacts._ID)?.let { cursor?.getInt(it) }

            if(name != null && id != null) {
                holder.bind(Mock(name, id))
                holder.setListener(listener)
            }

        }

    }

    fun swapCursor(cursor: Cursor?) {
        if(cursor != null && cursor != this.cursor) {
            this.cursor?.close()
            this.cursor = cursor
            notifyDataSetChanged()
        }
    }

    fun setListener(listener: OnItemClickListener?) {
        this.listener = listener
    }


    interface OnItemClickListener {
        fun onItemClick(id: String)
    }



}