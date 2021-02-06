package com.flawlessconcepts.roadlink2

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.flawlessconcepts.roadlink.roadlinkNetwork.CustomerItem

class ViewBookingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view)

        val customer = intent.getParcelableExtra<CustomerItem>("CUSTOMER")
    }

    fun viewBookingClicked(customer: CustomerItem) {
        val viewAllBooking =
            Intent(this@ViewBookingActivity, ViewAllBookingActivity::class.java)
        viewAllBooking.putExtra("CUSTOMER",customer)
        startActivity(viewAllBooking)
    }
}