package com.android.example.housingconnect

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class ImageUploadResponse(
    val message: String,
    val path: String
)