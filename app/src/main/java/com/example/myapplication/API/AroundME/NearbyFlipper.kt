package com.example.myapplication.API.AroundME

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NearbyFlipper(
    @Json(name = "success") val success: Boolean,
    @Json(name = "totalResults") val totalResults: Int,
    @Json(name = "first") val first: Int,
    @Json(name = "last") val last: Int,
    @Json(name = "resultCount") val resultCount: Int,
    @Json(name = "results") val results: List<WiFiNetwork>,
    @Json(name = "searchAfter") val searchAfter: String,
    @Json(name = "search_after") val searchAfterAlt: Int // Assuming this is an Int value, adjust if it's different
)

@JsonClass(generateAdapter = true)
data class WiFiNetwork(
    @Json(name = "trilat") val trilat: Double,
    @Json(name = "trilong") val trilong: Double,
    @Json(name = "ssid") val ssid: String,
    @Json(name = "qos") val qos: Int,
    @Json(name = "transid") val transid: String,
    @Json(name = "firsttime") val firsttime: String,
    @Json(name = "lasttime") val lasttime: String,
    @Json(name = "lastupdt") val lastupdt: String,
    @Json(name = "netid") val netid: String,
    @Json(name = "type") val type: String,
    @Json(name = "capabilities") val capabilities: List<String>,
    @Json(name = "userfound") val userfound: Boolean,
    @Json(name = "device") val device: Int,
    @Json(name = "name") val name: String,
    @Json(name = "country") val country: String,
    @Json(name = "region") val region: String,
    @Json(name = "road") val road: String?,
    @Json(name = "city") val city: String?,
    @Json(name = "housenumber") val housenumber: String?,
    @Json(name = "postalcode") val postalcode: String
)
