package com.fg.marvelherodemo.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.fg.marvelherodemo.apimodel.Character

@Dao
interface CharactersDao {

    @Query("SELECT * FROM character")
    fun getAll(): List<Character>

    @Insert
    fun insert(character: Character)
}