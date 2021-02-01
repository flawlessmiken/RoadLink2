package com.flawlessconcepts.roadlink2

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

class MakeBookingActivity : AppCompatActivity() {

    private var spinner: Spinner? = null
    private var hasReturn: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_make_booking)

        val makebookingButton = findViewById<MaterialButton>(R.id.bt_book_now)
        makebookingButton.setOnClickListener { makeBooking() }

        hasReturnSpinner()

    }

    private fun makeBooking() {
        val makeBooking = Intent(this@MakeBookingActivity, ConfirmActivity::class.java)
        startActivity(makeBooking)
    }

    fun hasReturnSpinner() {
         spinner= findViewById<View>(R.id.spinner) as Spinner
        spinner!!.setOnItemSelectedListener(object : OnItemSelectedListener {
            override fun onItemSelected(
                adapterView: AdapterView<*>,
                view: View,
                i: Int,
                l: Long
            ) {
                hasReturn = adapterView.selectedItem.toString()
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {}
        })
    }
}