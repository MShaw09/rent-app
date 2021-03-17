package com.android.example.housingconnect

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Post(
    var id: Int,
    var email: String,
    var type: String,
    var bed: Int,
    var bath: Int,
    var price: Int,
    var covidTested: String,
    var moveIn: String,
    var location: String,
    var desc: String,
    var date: String,
    var image: String
) : Parcelable
