package com.fg.marvelherodemo.apimodel

data class MarvelList<out T>(
    val available: Int?,
    val returned: Int?,
    val collectionURI: String?,
    val items: List<T>?
)