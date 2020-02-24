package com.fg.marvelherodemo.networking

import androidx.annotation.Nullable
import com.fg.marvelherodemo.apimodel.Character
import com.fg.marvelherodemo.apimodel.DataWrapper
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface CharacterApi {


    @GET("/v1/public/characters")
    fun getCharacters(@SuppressWarnings("SameParameterValue")
                             @Query("ts") ts: String,
                             @Query("apikey") apiKey: String,
                             @Query("hash") hash: String,
                             @Nullable @Query("offset") offset: Int,
                             @Query("limit") limit: Int): Observable<DataWrapper<Character>>

    @GET("v1/public/characters/{characterId}")
    fun getCharacter(@Path("characterId") characterId: Int,
                     @Query("ts") timestamp: String,
                     @Query("apikey") publicApiKey: String,
                     @Query("hash") keyHash: String): Observable<DataWrapper<Character>>

    companion object Factory {

        fun create(): CharacterApi {
            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl("https://gateway.marvel.com/")
                    .build()

            return retrofit.create( CharacterApi::class.java)
        }
    }
}