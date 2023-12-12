package com.example.myapplication.API.AroundME

import android.os.Parcel
import android.os.Parcelable
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
    @Json(name = "search_after") val searchAfterAlt: String // Assuming this is an Int value, adjust if it's different
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readByte() != 0.toByte(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.createTypedArrayList(WiFiNetwork.CREATOR) ?: ArrayList(),
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeByte(if (success) 1 else 0)
        parcel.writeInt(totalResults)
        parcel.writeInt(first)
        parcel.writeInt(last)
        parcel.writeInt(resultCount)
        parcel.writeTypedList(results)
        parcel.writeString(searchAfter)
        parcel.writeString(searchAfterAlt)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<NearbyFlipper> {
        override fun createFromParcel(parcel: Parcel): NearbyFlipper {
            return NearbyFlipper(parcel)
        }

        override fun newArray(size: Int): Array<NearbyFlipper?> {
            return arrayOfNulls(size)
        }
    }
}

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
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readString() ?: "",
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.createStringArrayList() ?: arrayListOf(),
        parcel.readByte() != 0.toByte(),
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeDouble(trilat)
        parcel.writeDouble(trilong)
        parcel.writeString(ssid)
        parcel.writeInt(qos)
        parcel.writeString(transid)
        parcel.writeString(firsttime)
        parcel.writeString(lasttime)
        parcel.writeString(lastupdt)
        parcel.writeString(netid)
        parcel.writeString(type)
        parcel.writeStringList(capabilities)
        parcel.writeByte(if (userfound) 1 else 0)
        parcel.writeInt(device)
        parcel.writeString(name)
        parcel.writeString(country)
        parcel.writeString(region)
        parcel.writeString(road)
        parcel.writeString(city)
        parcel.writeString(housenumber)
        parcel.writeString(postalcode)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<WiFiNetwork> {
        override fun createFromParcel(parcel: Parcel): WiFiNetwork {
            return WiFiNetwork(parcel)
        }

        override fun newArray(size: Int): Array<WiFiNetwork?> {
            return arrayOfNulls(size)
        }
    }
}
