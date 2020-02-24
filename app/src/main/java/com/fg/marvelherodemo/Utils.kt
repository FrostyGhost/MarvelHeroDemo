package com.fg.marvelherodemo

import android.content.Context
import com.fg.marvelherodemo.networking.CharacterApi
import com.fg.marvelherodemo.networking.CharactersRepository
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import androidx.room.TypeConverter
import com.fg.marvelherodemo.apimodel.*
import com.google.gson.reflect.TypeToken
import com.google.gson.Gson
import java.util.*
import android.util.Log
import android.net.ConnectivityManager


object Utils {

    fun md5(stringToHash: String): String {
        val md5 = "MD5"

        try {
            val digest = MessageDigest.getInstance(md5)
            digest.update(stringToHash.toByteArray())
            val messageDigest = digest.digest()

            val hexString = StringBuilder()
            for (aMessageDigest in messageDigest) {
                var h = Integer.toHexString(0xFF and aMessageDigest.toInt())
                while (h.length < 2) {
                    h = "0$h"
                }
                hexString.append(h)
            }
            return hexString.toString()

        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }

        return ""
    }

    object CharactersRepositoryProvider {
        fun provideSCharactersRepository(): CharactersRepository {
            return CharactersRepository(CharacterApi.create())
        }
    }



//    class ComicSummaryConverter {
//        @TypeConverter
//        fun stringFromObject(list:  List<ComicSummary>): String {
//            val gson = Gson()
//            return gson.toJson(list)
//        }
//        @TypeConverter
//        fun getObjectFromString(jsonString: String):  List<ComicSummary> {
//            val listType = object : TypeToken< List<ComicSummary>>() {
//            }.type
//            return Gson().fromJson(jsonString, listType)
//
//        }
//    }

//    class StorySummaryConverter {
//        @TypeConverter
//        fun stringFromObject(list: List<StorySummary>): String {
//            val gson = Gson()
//            return gson.toJson(list)
//        }
//        @TypeConverter
//        fun getObjectFromString(jsonString: String): List<StorySummary> {
//            val listType = object : TypeToken<List<StorySummary>>() {
//            }.type
//            return Gson().fromJson(jsonString, listType)
//
//        }
//    }
//
//    class EventSummaryConverter {
//        @TypeConverter
//        fun stringFromObject(list: List<EventSummary>): String {
//            val gson = Gson()
//            return gson.toJson(list)
//        }
//        @TypeConverter
//        fun getObjectFromString(jsonString: String): List<EventSummary> {
//            val listType = object : TypeToken<List<EventSummary>>() {
//            }.type
//            return Gson().fromJson(jsonString, listType)
//
//        }
//    }
//
//    class SeriesSummaryConverter {
//        @TypeConverter
//        fun stringFromObject(list: List<SeriesSummary>): String {
//            val gson = Gson()
//            return gson.toJson(list)
//        }
//        @TypeConverter
//        fun getObjectFromString(jsonString: String): List<SeriesSummary> {
//            val listType = object : TypeToken<List<SeriesSummary>>() {
//            }.type
//            return Gson().fromJson(jsonString, listType)
//
//        }
//    }
//
//    class URLConverter {
//        @TypeConverter
//        fun stringFromObject(list: List<Url>): String {
//            val gson = Gson()
//            return gson.toJson(list)
//        }
//        @TypeConverter
//        fun getObjectFromString(jsonString: String): List<Url> {
//            val listType = object : TypeToken<List<Url>>() {
//            }.type
//            return Gson().fromJson(jsonString, listType)
//
//        }
//    }
//
//    class ConverterDate {
//        @TypeConverter
//        fun toDate(timestamp: Long?): Date? {
//            return when (timestamp) {
//                null -> null
//                else -> Date(timestamp)
//            }
//        }
//
//        @TypeConverter
//        fun toTimestamp(date: Date?): Long? {
//            return date?.time
//        }
//    }
//
//    class ImagesConverters {
//
//        /**
//         * Convert a a list of Images to a Json
//         */
//        @TypeConverter
//        fun fromImagesJson(stat: Image): String {
//            return Gson().toJson(stat)
//        }
//
//        /**
//         * Convert a json to a list of Images
//         */
//        @TypeConverter
//        fun toImagesList(jsonImages: String): Image {
//            val notesType = object : TypeToken<Image>() {}.type
//            return Gson().fromJson<Image>(jsonImages, notesType)
//        }
//    }
    class ComicSummaryConverter {
    @TypeConverter
    fun stringFromObject(list:  MarvelList<ComicSummary>): String {
        val gson = Gson()
        return gson.toJson(list)
    }
    @TypeConverter
    fun getObjectFromString(jsonString: String):  MarvelList<ComicSummary> {
        val listType = object : TypeToken< MarvelList<ComicSummary>>() {
        }.type
        return Gson().fromJson(jsonString, listType)

    }
}

    class StorySummaryConverter {
        @TypeConverter
        fun stringFromObject(list: MarvelList<StorySummary>): String {
            val gson = Gson()
            return gson.toJson(list)
        }
        @TypeConverter
        fun getObjectFromString(jsonString: String): MarvelList<StorySummary> {
            val listType = object : TypeToken<MarvelList<StorySummary>>() {
            }.type
            return Gson().fromJson(jsonString, listType)

        }
    }

    class EventSummaryConverter {
        @TypeConverter
        fun stringFromObject(list: MarvelList<EventSummary>): String {
            val gson = Gson()
            return gson.toJson(list)
        }
        @TypeConverter
        fun getObjectFromString(jsonString: String): MarvelList<EventSummary> {
            val listType = object : TypeToken<MarvelList<EventSummary>>() {
            }.type
            return Gson().fromJson(jsonString, listType)

        }
    }

    class SeriesSummaryConverter {
        @TypeConverter
        fun stringFromObject(list: MarvelList<SeriesSummary>): String {
            val gson = Gson()
            return gson.toJson(list)
        }
        @TypeConverter
        fun getObjectFromString(jsonString: String): MarvelList<SeriesSummary> {
            val listType = object : TypeToken<MarvelList<SeriesSummary>>() {
            }.type
            return Gson().fromJson(jsonString, listType)

        }
    }

    class URLConverter {
        @TypeConverter
        fun stringFromObject(list: List<Url>): String {
            val gson = Gson()
            return gson.toJson(list)
        }
        @TypeConverter
        fun getObjectFromString(jsonString: String): List<Url> {
            val listType = object : TypeToken<List<Url>>() {
            }.type
            return Gson().fromJson(jsonString, listType)

        }
    }

    class ConverterDate {
        @TypeConverter
        fun fromTimestamp(value: Long?): Date? {
            return if (value == null) null else Date(value)
        }

        @TypeConverter
        fun dateToTimestamp(date: Date?): Long? {
            return date?.time!!.toLong()
        }
    }

    class ImagesConverters {

        /**
         * Convert a a list of Images to a Json
         */
        @TypeConverter
        fun fromImagesJson(stat: Image): String {
            return Gson().toJson(stat)
        }

        /**
         * Convert a json to a list of Images
         */
        @TypeConverter
        fun toImagesList(jsonImages: String): Image {
            val notesType = object : TypeToken<Image>() {}.type
            return Gson().fromJson<Image>(jsonImages, notesType)
        }
    }

    fun isConnected(ctx : Context): Boolean {
        var connected = false
        try {
            val cm = ctx.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val nInfo = cm.activeNetworkInfo
            connected = nInfo != null && nInfo.isAvailable && nInfo.isConnected
            return connected
        } catch (e: Exception) {
            Log.e("Connectivity Exception", e.message)
        }

        return connected
    }

}