package com.example.recyclertest

import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.Toast
import java.util.concurrent.Callable
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity(), ContactsAdapter.OnItemClickListener {

    private var executor = Executors.newFixedThreadPool(2)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, RecyclerFragment.newInstance())
                .commit()
        }
    }

    private fun getCursorWithResult(id: String): Callable<Cursor> {
        return Callable {
            contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                arrayOf(ContactsContract.CommonDataKinds.Phone.NUMBER),
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ? AND " +
                        ContactsContract.CommonDataKinds.Phone.TYPE + " = ?",
                arrayOf(id, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE.toString()),
                null)!!
        }
    }

    override fun onItemClick(id: String) {

        val future = executor.submit(getCursorWithResult(id))

        val cursor = future.get()

        if(cursor != null && cursor.moveToFirst()) {
            val number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
            cursor.close()
            Toast.makeText(this, number, Toast.LENGTH_SHORT).show()
        }
    }
}
