package com.mina.common.models

import android.os.Parcelable
import com.google.gson.Gson
import kotlinx.parcelize.Parcelize

@Parcelize
public data class Movie constructor(
    val title: String,
    val year: String,
    val description: String,
    val rating: String,
    val posterUrl: String?
) : Parcelable {

    override fun equals(other: Any?): Boolean {
        if (other is Movie?) {
            return this.title == other?.title
                    && this.year == other?.year
        }
        return false
    }
}