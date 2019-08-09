package com.example.roomsqlitedatabase.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(foreignKeys = arrayOf(
    ForeignKey(entity = Album::class, parentColumns = ["id"], childColumns = ["album_id"]),
    ForeignKey(entity = Song::class, parentColumns = ["id"], childColumns = ["song_id"])
    ))
data class AlbumSong(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var mId: Int,

    @ColumnInfo(name = "album_id")
    var mAlbumId: Int,

    @ColumnInfo(name = "song_id")
    var mSongId: Int

)