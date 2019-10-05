package com.mark.starwars.net

import com.mark.starwars.model.CharacterList
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface RetrofitService {
    @GET("api/people/")
    fun getCharacters(@Query("page") number : Int) : Deferred<Response<CharacterList>>

    @GET("api/people/")
    fun getSeatchResult(@Query("search") text : String) : Deferred<Response<CharacterList>>

}