package com.flawlessconcepts.roadlink.roadlinkNetwork

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class CustomerItem(
    val customerFirstName: String,
    val customerLastName: String,
    val customerPhone: String
): Parcelable

