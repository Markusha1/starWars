package com.mark.starwars.net

import com.mark.starwars.model.ResponseList
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query


interface RetrofitService {

    @GET("api/people/")
    fun getCharacters(@Query("page") number : Int) : Observable<ResponseList>

    @GET("api/people/")
    fun getSearchResult(@Query("search") text : String) : Observable<ResponseList>

}