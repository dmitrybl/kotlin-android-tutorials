package com.example.roomsqlitedatabase

import android.app.Application
import androidx.room.Room
import com.example.roomsqlitedatabase.database.MusicDatabase

class AppDelegate : Application() {

    private lateinit var mMusicDatabase: MusicDatabase

    override fun onCreate() {
        super.onCreate()

        mMusicDatabase = Room.databaseBuilder(applicationContext, MusicDatabase::class.java, "music_database")
            .allowMainThreadQueries()
            .build()
    }

    fun getMusicDatabase() = mMusicDatabase
}