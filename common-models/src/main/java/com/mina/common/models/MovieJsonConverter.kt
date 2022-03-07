package com.mina.common.models

import com.google.gson.Gson
import javax.inject.Inject

//https://issuetracker.google.com/issues/148523779?pli=1
public class MovieJsonConverter @Inject constructor(){
    fun toJson(movie: Movie): String = Gson().toJson(movie)
    fun fromJson(json: String): Movie = Gson().fromJson(json, Movie::class.java)
}