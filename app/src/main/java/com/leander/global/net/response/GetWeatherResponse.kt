package com.leander.global.net.response

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName


/* Created on 2022/9/8 */

@Keep
data class GetWeatherResponse(
    @SerializedName("records")
    val records: Records,
    @SerializedName("result")
    val result: Result,
    @SerializedName("success")
    val success: String
) {
    @Keep
    data class Records(
        @SerializedName("datasetDescription")
        val datasetDescription: String,
        @SerializedName("location")
        val location: List<Location>
    ) {
        @Keep
        data class Location(
            @SerializedName("locationName")
            val locationName: String,
            @SerializedName("weatherElement")
            val weatherElement: List<WeatherElement>
        ) {
            @Keep
            data class WeatherElement(
                @SerializedName("elementName")
                val elementName: String,
                @SerializedName("time")
                val time: List<Time>
            ) {
                @Keep
                data class Time(
                    @SerializedName("endTime")
                    val endTime: String,
                    @SerializedName("parameter")
                    val parameter: Parameter,
                    @SerializedName("startTime")
                    val startTime: String
                ) {
                    @Keep
                    data class Parameter(
                        @SerializedName("parameterName")
                        val parameterName: String,
                        @SerializedName("parameterUnit")
                        val parameterUnit: String
                    )
                }
            }
        }
    }

    @Keep
    data class Result(
        @SerializedName("fields")
        val fields: List<Field>,
        @SerializedName("resource_id")
        val resourceId: String
    ) {
        @Keep
        data class Field(
            @SerializedName("id")
            val id: String,
            @SerializedName("type")
            val type: String
        )
    }
}