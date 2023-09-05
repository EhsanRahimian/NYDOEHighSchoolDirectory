package com.example.nydoehighschooldirectory.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class School(
    @SerializedName("dbn")val dbn: String,
    @SerializedName("school_name")val schoolName: String,
    @SerializedName("city")val city: String,
    @SerializedName("zip")val zip: String,
): Parcelable

