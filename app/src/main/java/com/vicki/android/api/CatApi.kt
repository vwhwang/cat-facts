package com.vicki.android.api

import com.vicki.android.data.Cat
import retrofit2.Response
import retrofit2.http.GET

interface CatApi {
    @GET("/fact")
    suspend fun fetchBlog() : Response<Cat>
}