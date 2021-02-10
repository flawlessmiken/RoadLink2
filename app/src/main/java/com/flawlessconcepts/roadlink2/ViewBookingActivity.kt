package com.flawlessconcepts.roadlink2

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.flawlessconcepts.roadlink.roadlink.RetrofitService
import com.flawlessconcepts.roadlink.roadlink.ServiceGenerator
import com.flawlessconcepts.roadlink.roadlinkNetwork.BookingItem
import com.flawlessconcepts.roadlink.roadlinkNetwork.CustomerItem
import com.google.android.material.button.MaterialButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ViewBookingActivity : AppCompatActivity() {

    lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view)

        progressBar = findViewById(R.id.progressBar) as ProgressBar

        val edBookiking = findViewById<EditText>(R.id.ed_booking)
        val viewAllBookingButton = findViewById<MaterialButton>(R.id.viewbooking)

        val client = ServiceGenerator.createService(RetrofitService::class.java)
        viewAllBookingButton.setOnClickListener {

           if(!edBookiking.text.toString().isEmpty()){
           NetWorkCalls(client,edBookiking.text.toString()) }
        }

    }

    fun viewBookingClicked(booking: BookingItem) {
        val viewAllBooking =
            Intent(this@ViewBookingActivity, ViewAllBookingActivity::class.java)
        viewAllBooking.putExtra("BOOKING",booking)
        startActivity(viewAllBooking)
        finish()
    }

    fun NetWorkCalls(client: RetrofitService, bookingID: String){
        progressBar.visibility = View.VISIBLE
        val costCall = client.getBooking(bookingID)
        costCall.enqueue(object : Callback<BookingItem?> {
            override fun onResponse(
                call: Call<BookingItem?>, response: Response<BookingItem?>
            ) {
                val statusCode = response.code()
                try {
                    val booking = response.body() as BookingItem
                   viewBookingClicked(booking)

                } catch (e: Exception) {
                    progressBar.visibility = View.INVISIBLE
                    Toast.makeText(
                        applicationContext, e.toString(), Toast.LENGTH_LONG
                    ).show()
                }


            }

            override fun onFailure(call: Call<BookingItem?>, t: Throwable) {
                // Log error here since request failed
                progressBar.visibility = View.INVISIBLE
                Toast.makeText(applicationContext, "failed" + t.message, Toast.LENGTH_LONG)
                    .show()
            }
        })
    }

    fun backClicked(view: View) {
        finish()
    }
}