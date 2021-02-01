package com.flawlessconcepts.roadlink2

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class ViewAllBookingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_all_booking)
    }

    fun doneBookingClicked(view: View?) {
        val goHome = Intent(this@ViewAllBookingActivity, MainActivity::class.java)
        startActivity(goHome)
        finish()
    }
}