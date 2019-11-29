package com.mark.starwars.utils

import com.mark.starwars.activities.App
import com.mark.starwars.di.AppComponent

class Injector private constructor(){
    companion object{
        fun get() : AppComponent =
            App.get().component
    }
}