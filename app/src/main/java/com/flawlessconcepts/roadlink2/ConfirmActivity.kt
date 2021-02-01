package com.flawlessconcepts.roadlink2

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ConfirmActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirm)
    }

    fun cancelBookingClicked(view: View?) {
        Toast.makeText(this, "Canceled", Toast.LENGTH_SHORT).show()
    }

    fun confirmBookingClicked(view: View?) {
        val viewALlBooking =
            Intent(this@ConfirmActivity, ViewAllBookingActivity::class.java)
        startActivity(viewALlBooking)
    }
}