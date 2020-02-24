package com.fg.marvelherodemo.apimodel

import androidx.room.*
import com.fg.marvelherodemo.Utils
import java.util.*

@Entity
data class Character(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    val name: String?,
    val description: String?,
    @TypeConverters(Utils.ConverterDate::class)
    val modified: Date? ,
    @TypeConverters(Utils.ImagesConverters::class)
    val thumbnail: Image?,
    val resourceURI: String?,
    @TypeConverters(Utils.ComicSummaryConverter::class)
    val comics: MarvelList<ComicSummary>?,
    @TypeConverters(Utils.URLConverter::class)
    val urls: List<Url>?,
    @TypeConverters(Utils.StorySummaryConverter::class)
    val stories: MarvelList<StorySummary>?,
    @TypeConverters(Utils.EventSummaryConverter::class)
    val events: MarvelList<EventSummary>?,
    @TypeConverters(Utils.SeriesSummaryConverter::class)
    val series: MarvelList<SeriesSummary>?
)
