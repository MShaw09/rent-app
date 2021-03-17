package com.android.example.housingconnect

import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface HousingService {
    @GET("housing/all")
    fun getAll(): Call<List<Post>>

    @POST("housing")
    fun create(@Body post: Post): Call<Message>

    @Multipart
    @POST("images/upload")
    fun uploadImage(@Part image: MultipartBody.Part): Call<ImageUploadResponse>
    // NOTE: Don't need to define "/images/{id}" endpoint, Glide will handle that for us
}
