package com.leander.homework.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.leander.global.net.request.GetWeatherRequest
import com.leander.global.net.response.GetWeatherResponse
import com.leander.homework.model.Weather
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.*

/* Created on 2022/9/8 */

class WeatherViewModel : ViewModel() {
    val weatherLiveData: MutableLiveData<ArrayList<Weather>> = MutableLiveData()

    interface ApiService {
        @GET("v1/rest/datastore/F-C0032-001")
        fun getWeather(
            @Query("Authorization") authorization: String,
            @Query("limit") limit: String,
            @Query("offset") offset: String,
            @Query("format") format: String,
            @Query("locationName") locationName: String,
            @Query("elementName") elementName: String
        ): Call<GetWeatherResponse>
    }


    fun getWeather(request: GetWeatherRequest) {
        val apiService = AppClientManager.client.create(ApiService::class.java)
        apiService.getWeather(
            request.Authorization,
            request.limit,
            request.offset,
            request.format,
            request.locationName,
            request.elementName
        ).enqueue(
            object : retrofit2.Callback<GetWeatherResponse> {
                override fun onResponse(
                    call: Call<GetWeatherResponse>,
                    response: retrofit2.Response<GetWeatherResponse>
                ) {
                    val resStr = response.body()
                    val weathers = ArrayList<Weather>()

                    for (item in resStr?.records?.location!!) {
                        for (element in item.weatherElement) {
                            for (time in element.time) {
                                weathers.add(
                                    Weather(
                                        time.startTime,
                                        time.endTime,
                                        time.parameter.parameterName,
                                        time.parameter.parameterUnit
                                    )
                                )
                            }
                        }
                    }

                    weatherLiveData.postValue(weathers)
                }

                override fun onFailure(call: Call<GetWeatherResponse>, t: Throwable) {
                    val resStr = t.message
                }
            })
    }

    class AppClientManager private constructor() {
        private val retrofit: Retrofit
        private val okHttpClient = OkHttpClient()

        init {
            retrofit = Retrofit.Builder()
                .baseUrl("https://opendata.cwb.gov.tw/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
        }

        companion object {
            private val manager = AppClientManager()
            val client: Retrofit
                get() = manager.retrofit
        }
    }

}