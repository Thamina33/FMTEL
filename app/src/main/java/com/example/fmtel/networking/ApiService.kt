package com.example.fmtel.networking

import com.example.fmtel.SharedPrefManager
import com.example.fmtel.model.*
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    companion object {
        val ROOT_URL: String = "https://fmtel.spinnertechltd.com/api/"   //"http://192.168.0.138:8000/api/v1/"
//        val IMAGE_URL: String =
//            " "
    }
    @FormUrlEncoded
    @POST("login")
    fun user_login(
        @Field("name") name: String,
        @Field("password") pass: String,
    ): Call<login_response>

    @GET("categories")
    fun getCategories(
        @Header("Authorization")  token : String = SharedPrefManager.getToken()

    ): Call<CategoryListResponse>

    @GET("brands/by-category")
    fun getBrands(
        @Header("Authorization") token: String = SharedPrefManager.getToken(),
        @Query("category_id") category_id : String
    ): Call<BrandListResponse>

    @GET("packages/by-brand")
    fun getPackageByBrand(
        @Header("Authorization") token: String = SharedPrefManager.getToken(),
        @Query("brand_id") brandID : String
    ): Call<PackageListResponse>

    @GET("products/by-package")
    fun getProductByPackage(
        @Header("Authorization") token: String = SharedPrefManager.getToken(),
        @Query("package_id") packageID : String
    ): Call<ProductListResponse>

    @FormUrlEncoded
    @POST("sales/add")
    fun salesAdd(
        @Field("product_id") product_id: String,
        @Field("price") price: String,
        @Field("quantity") quantity: String,
    ): Call<salesAdd>
}