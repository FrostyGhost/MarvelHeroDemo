package com.fg.marvelherodemo.db

import androidx.room.RoomDatabase
import androidx.room.Database
import androidx.room.TypeConverters
import com.fg.marvelherodemo.Utils
import com.fg.marvelherodemo.apimodel.Character


@Database(entities = [Character::class], version = 1, exportSchema = false)
@TypeConverters(Utils.ConverterDate::class, Utils.ComicSummaryConverter::class, Utils.SeriesSummaryConverter::class,
        Utils.URLConverter::class,Utils.EventSummaryConverter::class,Utils.StorySummaryConverter::class, Utils.ImagesConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun employeeDao(): CharactersDao
}