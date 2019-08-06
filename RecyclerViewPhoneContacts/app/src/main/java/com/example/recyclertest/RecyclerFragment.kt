package com.example.recyclertest

import android.content.Context
import android.database.Cursor
import android.os.Bundle
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import java.util.*

class RecyclerFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener, LoaderManager.LoaderCallbacks<Cursor> {

    private lateinit var recycler: RecyclerView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private val contactsAdapter = ContactsAdapter()
    private lateinit var errorView: View

    private var listener: ContactsAdapter.OnItemClickListener? = null


    companion object {
        fun newInstance(): RecyclerFragment {
            return RecyclerFragment()
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if(context is ContactsAdapter.OnItemClickListener) {
            listener = context
        }
    }

    override fun onDetach() {
        listener = null
        super.onDetach()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fr_recycler, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recycler = view.findViewById(R.id.recycler)
        swipeRefreshLayout = view.findViewById(R.id.refresher)
        swipeRefreshLayout.setOnRefreshListener(this)
        errorView = view.findViewById(R.id.error_view)

        contactsAdapter.setListener(listener)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        recycler.layoutManager = LinearLayoutManager(activity)
        recycler.adapter = contactsAdapter

        loaderManager.restartLoader(0, null, this)
    }

    override fun onRefresh() {


        loaderManager.restartLoader(0, null, this)
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
        return CursorLoader(context!!,
            ContactsContract.Contacts.CONTENT_URI,
            arrayOf(ContactsContract.Contacts._ID, ContactsContract.Contacts.DISPLAY_NAME),
            null, null, ContactsContract.Contacts._ID)
    }

    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
        contactsAdapter.swapCursor(data)

        if(swipeRefreshLayout.isRefreshing) {
            swipeRefreshLayout.isRefreshing = false
        }

    }

    override fun onLoaderReset(loader: Loader<Cursor>) {}

}