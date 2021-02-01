package com.flawlessconcepts.roadlink2

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.flawlessconcepts.roadlink.roadlinkNetwork.CustomerItem
import com.google.android.material.button.MaterialButton

class MenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        val customer = intent.getParcelableExtra<CustomerItem>("CUSTOMER")


        Toast.makeText(applicationContext, customer?.customerPhone, Toast.LENGTH_LONG).show()


        val viewBookingButton = findViewById<MaterialButton>(R.id.bt_view_booking)
        viewBookingButton.setOnClickListener { viewBookingClicked() }

        val makebookingButton = findViewById<MaterialButton>(R.id.bt_make_booking)
        makebookingButton.setOnClickListener { makeBookingClicked() }
    }

    private fun viewBookingClicked() {
        val startViewBooking = Intent(this@MenuActivity, ViewBookingActivity::class.java)
        startActivity(startViewBooking)
    }

    private fun makeBookingClicked() {
        val startMakeBooking = Intent(this@MenuActivity, MakeBookingActivity::class.java)
        startActivity(startMakeBooking)
    }
}