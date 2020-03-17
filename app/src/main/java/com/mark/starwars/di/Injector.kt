package com.mark.starwars.di

import com.mark.starwars.ui.activity.App

class Injector private constructor(){
    companion object{
        fun get() : AppComponent =
            App.get().component
    }
}