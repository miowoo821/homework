package com.leander.global.net.request


/* Created on 2022/9/8 */

data class GetWeatherRequest(
    var Authorization: String,
    var limit: String,
    var offset: String,
    var format: String,
    var locationName: String,
    var elementName: String
)