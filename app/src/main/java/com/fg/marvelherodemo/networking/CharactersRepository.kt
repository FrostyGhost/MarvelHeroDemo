package com.fg.marvelherodemo.networking


import androidx.lifecycle.LiveData
import com.fg.marvelherodemo.R
import com.fg.marvelherodemo.Utils
import com.fg.marvelherodemo.apimodel.Character
import java.util.*

import com.fg.marvelherodemo.apimodel.DataWrapper
import io.reactivex.Observable


class CharactersRepository(private val apiService: CharacterApi) {

        private val defaultLimit = 25

    var offset = 0

    private val timestamp = Date().time

    //You can get the keys here
    //https://developer.marvel.com/account

    private var privateKey = "19c725832751df3f3752235b9c462a593ab8f555"
    private var publicKey = "9e85c2254a034ab9af74082c61ab47eb"
//    private var publicKey = "Insert your publicKey here"
//    private var privateKey = "Insert your privateKey here"

    private val hash = Utils.md5(timestamp.toString() + privateKey + publicKey)


    fun getCharacters2(): Observable<DataWrapper<Character>> {
        return apiService.getCharacters(timestamp.toString(),publicKey,hash,offset,defaultLimit)
    }

    fun getCharactersFromId(id: Int): Observable<DataWrapper<Character>> {
        return apiService.getCharacter(id,timestamp.toString(),publicKey,hash)
    }
}