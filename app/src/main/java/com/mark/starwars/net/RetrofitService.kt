package com.mark.starwars.net

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.mark.starwars.model.CharacterList
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


interface RetrofitService {
    @GET("api/people/")
    fun getCharacters(@Query("page") number : Int) : Deferred<Response<CharacterList>>

    @GET("api/people/")
    fun getSeatchResult(@Query("search") text : String) : Deferred<Response<CharacterList>>

    companion object Factory {
        fun create(): RetrofitService {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://swapi.co/")
                .addCallAdapterFactory(CoroutineCallAdapterFactory.invoke())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(RetrofitService::class.java)
        }
    }
}