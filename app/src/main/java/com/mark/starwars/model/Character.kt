package com.mark.starwars.model


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity
data class Character(
        @SerializedName("birth_year")
        var birth_year: String,
        @SerializedName("gender")
        var gender: String,
        @SerializedName("height")
        var height: String,
        @SerializedName("mass")
        var mass: String,
        @SerializedName("name")
        var name: String,
        var isFavourite : Boolean = false,
        @PrimaryKey(autoGenerate = true)var id: Int
    ) : Serializable