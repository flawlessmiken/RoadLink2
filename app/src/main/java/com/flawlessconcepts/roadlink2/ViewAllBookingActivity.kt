package com.flawlessconcepts.roadlink2

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.flawlessconcepts.roadlink.roadlink.RetrofitService
import com.flawlessconcepts.roadlink.roadlink.ServiceGenerator
import com.flawlessconcepts.roadlink.roadlinkNetwork.BookingItem

class ViewAllBookingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_all_booking)

        val booking = intent.getParcelableExtra<BookingItem>("BOOKING")
        val client = ServiceGenerator.createService(RetrofitService::class.java)

        if (booking != null){
            setUpViewWithData(booking)
        }
    }

    fun doneBookingClicked(view: View?) {
        val goHome = Intent(this@ViewAllBookingActivity, MainActivity::class.java)
        startActivity(goHome)
        finish()
    }


    @SuppressLint("SetTextI18n")
    fun setUpViewWithData(booking: BookingItem){

        val bookingID = findViewById<TextView>(R.id.booking_id)
        val vehicleID= findViewById<TextView>(R.id.vehicleID)
        val customerID = findViewById<TextView>(R.id.customerID)
        val HasReturn = findViewById<TextView>(R.id.HasReturn)
        val tripDistance = findViewById<TextView>(R.id.tripDistance)
        val calculatedCost = findViewById<TextView>(R.id.calculatedCost)
        val bookingStatus = findViewById<TextView>(R.id.bookingStatus)
        val paymentStatus = findViewById<TextView>(R.id.paymentStatus)
        val paidAmount =  findViewById<TextView>(R.id.paidAmount)
        val departureTime = findViewById<TextView>(R.id.departureTime)
        val bookingDate = findViewById<TextView>(R.id.bookingDate)
        val arrivalTime = findViewById<TextView>(R.id.arrivalTime)

        val numPassenger = findViewById<TextView>(R.id.numPassenger)
        val location = findViewById<TextView>(R.id.location)
        val destination = findViewById<TextView>(R.id.destination)
        val address = findViewById<TextView>(R.id.address)

        customerID.text= booking.customerID
        TripDistance.text= booking.tripDistance.toString() + " KM"
        cost.text = "# "+ booking.calculatedCost.toString()
        departureTime.text = booking.departureTime
        location.text = booking.location
        destination.text = booking.destination
        address.text = booking.locationAddress

    }
}