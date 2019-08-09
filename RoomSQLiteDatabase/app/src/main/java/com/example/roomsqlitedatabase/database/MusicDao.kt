package com.example.roomsqlitedatabase.database

import androidx.room.*

@Dao
interface MusicDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAlbums(albums: List<Album>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSongs(songs: List<Song>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun setLinksAlbumSongs(linksAlbumSongs: List<AlbumSong>)

    @Query("select * from album")
    fun getAlbums(): List<Album>

    @Query("select * from song")
    fun getSongs(): List<Song>

    @Delete
    fun deleteAlbums(album: Album)

    @Query("select * from song inner join albumsong on song.id = albumsong.song_id where album_id = :albumId")
    fun getSongsFromAlbum(albumId: Int): List<Song>

}