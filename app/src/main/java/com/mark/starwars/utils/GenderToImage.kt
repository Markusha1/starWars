package com.mark.starwars.utils

import com.mark.starwars.R

object GenderToImage {
    fun convert(gender : String) : Int? {
        when(gender){
            "female" -> return R.drawable.ic_female_gender
            "n/a" -> return R.drawable.ic_na_gender
            "hermaphrodite" -> return R.drawable.ic_hermophrod_gender
            "male" -> return R.drawable.ic_male_gender
        }
        return null
    }
}