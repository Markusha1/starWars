package com.mark.starwars.ui.activity

import android.app.Application
import com.mark.starwars.di.AppComponent
import com.mark.starwars.di.AppModule
import com.mark.starwars.di.DaggerAppComponent
import com.mark.starwars.di.RoomModule

class App : Application() {

    lateinit var component: AppComponent

    override fun onCreate() {
        super.onCreate()
        instance = this
        component = DaggerAppComponent.builder().appModule(AppModule(this)).roomModule(RoomModule()).build()
    }

    companion object{
        private var instance : App? = null

        @JvmStatic
        fun get() : App = instance!!
    }
}