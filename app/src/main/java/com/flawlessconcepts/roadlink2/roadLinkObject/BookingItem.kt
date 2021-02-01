package com.flawlessconcepts.roadlink.roadlinkNetwork

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


data class BookingItem(
    var arrivalTime: String = "",
    var bookingDate: String = "",
    var bookingID: Int?  = 0,
    var bookingStatus: String?="",
    var calculatedCost: Double=0.0,
    var customerID: String="",
    var departureTime: String="",
    var destination: String="",
    var location: String="",
    var locationAddress: String="",
    var numbOfPassengers: Int=0,
    var paidAmount: Double=0.0,
    var paymentStatus: String="",
    var tripDistance: Double=0.0,
    var isHasReturn: Boolean = false,
    var vehicleID: String = ""
)

