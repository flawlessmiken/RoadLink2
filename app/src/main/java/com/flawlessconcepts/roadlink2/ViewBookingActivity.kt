package com.flawlessconcepts.roadlink2

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class ViewBookingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view)
    }

    fun viewBookingClicked(view: View?) {
        val viewAllBooking =
            Intent(this@ViewBookingActivity, ViewAllBookingActivity::class.java)
        startActivity(viewAllBooking)
    }
}