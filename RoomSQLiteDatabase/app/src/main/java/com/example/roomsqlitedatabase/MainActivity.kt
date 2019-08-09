package com.example.roomsqlitedatabase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.roomsqlitedatabase.database.Album
import java.lang.StringBuilder

class MainActivity : AppCompatActivity() {

    private lateinit var mAddBtn: Button
    private lateinit var mGetBtn: Button
    private lateinit var mDeleteBtn: Button
    private lateinit var tvInfo: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val musicDao = (applicationContext as AppDelegate).getMusicDatabase().getMusicDao()

        mAddBtn = findViewById(R.id.add)
        mAddBtn.setOnClickListener {
            musicDao.insertAlbums(createAlbums())
        }

        mGetBtn = findViewById(R.id.get)
        mGetBtn.setOnClickListener {
            showToast(musicDao.getAlbums())
        }

        mDeleteBtn = findViewById(R.id.delete)
        mDeleteBtn.setOnClickListener {
            if(musicDao.getAlbums().isNotEmpty()) {
                musicDao.deleteAlbums(musicDao.getAlbums().last())
                showToast(musicDao.getAlbums())
            } else {
                Toast.makeText(this, "Nothing to delete!", Toast.LENGTH_SHORT).show()
            }
        }

        tvInfo = findViewById(R.id.tvInfo)

    }

    private fun showToast(albums: List<Album>) {
        val stringBuilder = StringBuilder()
        for(album in albums) {
            stringBuilder.append(album.mName, " ", album.mReleaseDate, "\n")
        }

        tvInfo.text = stringBuilder.toString()

    }

    private fun createAlbums(): List<Album> {
        val albums = ArrayList<Album>()
        for(i in 0..10) {
            albums.add(Album(i, "album $i", "release ${System.currentTimeMillis()}"))
        }
        return albums
    }
}
