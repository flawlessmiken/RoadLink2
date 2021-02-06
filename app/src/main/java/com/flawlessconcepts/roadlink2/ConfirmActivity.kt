package com.flawlessconcepts.roadlink2

import android.content.Intent
import android.os.Bundle
import android.view.View
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

class ConfirmActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirm)

        val booking = intent.getParcelableExtra<BookingItem>("BOOKING")
        val client = ServiceGenerator.createService(RetrofitService::class.java)

        val confirmButton = findViewById<MaterialButton>(R.id.confirm)
        confirmButton.setOnClickListener(){

            if (booking != null){
                addBookingToDatabase(client,booking)
            }

        }

        val cancelButton = findViewById<MaterialButton>(R.id.cancel)
        cancelButton.setOnClickListener(){

        }



    }


    
    fun addBookingToDatabase(client:RetrofitService, bk:BookingItem){

        val addBookingCall = client.addBooking(bk.tripDistance,bk.isHasReturn.toString(),bk.calculatedCost,
            bk.departureTime,bk.customerID,bk.destination,bk.numbOfPassengers,
            bk.locationAddress)
        addBookingCall.enqueue(object : Callback<BookingItem?> {
            override fun onResponse(
                call: Call<BookingItem?>,
                response: Response<BookingItem?>
            ) {
                val statusCode = response.code()
                Toast.makeText(applicationContext, response.code().toString() +
                        "/n"+response.body().toString() , Toast.LENGTH_LONG).show()



            }

            override fun onFailure(call: Call<BookingItem?>, t: Throwable) {
                // Log error here since request failed
                Toast.makeText(applicationContext, "failed"+ t.message, Toast.LENGTH_LONG).show()
            }
        })
    }



    fun confirmBookingClicked() {
        val viewALlBooking =
            Intent(this@ConfirmActivity, ViewAllBookingActivity::class.java)

        startActivity(viewALlBooking)
    }

    fun cancelBookingClicked(){
        Toast.makeText(this, "Canceled", Toast.LENGTH_SHORT).show()
    }
}