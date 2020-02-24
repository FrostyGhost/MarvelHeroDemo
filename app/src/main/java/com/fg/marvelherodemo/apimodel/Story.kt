package com.fg.marvelherodemo.apimodel

data class Story(
    val id: Int,
    val title: String,
    val description: String,
    val thumbnail: Image?
)