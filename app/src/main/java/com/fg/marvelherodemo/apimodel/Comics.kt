package com.fg.marvelherodemo.apimodel

data class Comics(
    val id: Int,
    val title: String,
    val description: String,
    val thumbnail: Image?
)