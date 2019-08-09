package com.example.roomsqlitedatabase.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(Album::class, Song::class, AlbumSong::class), version = 2)
abstract class MusicDatabase : RoomDatabase() {

    abstract fun getMusicDao(): MusicDao
}