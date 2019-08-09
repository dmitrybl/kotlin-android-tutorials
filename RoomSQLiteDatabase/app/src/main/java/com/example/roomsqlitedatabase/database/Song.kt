package com.example.roomsqlitedatabase.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Song(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var mId: Int,

    @ColumnInfo(name = "name")
    var mName: String,

    @ColumnInfo(name = "duration")
    var mDuration: String
)