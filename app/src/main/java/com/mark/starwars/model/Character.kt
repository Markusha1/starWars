package com.mark.starwars.model


import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Character(
        var birth_year: String,
        var gender: String,
        var height: String,
        var mass: String,
        var name: String,
        var isFavourite : Boolean = false,
        @PrimaryKey(autoGenerate = true)var id: Int
    ) : Serializable


data class ResponseList(val results: List<Character>)