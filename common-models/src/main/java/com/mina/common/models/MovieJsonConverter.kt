package com.mina.common.models

import com.google.gson.Gson

public object MovieJsonConverter {
    fun toJson(movie: Movie): String = Gson().toJson(movie)
    fun fromJson(json: String): Movie = Gson().fromJson(json, Movie::class.java)
}