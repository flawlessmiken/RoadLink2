package com.flawlessconcepts.roadlink2

import android.content.Context
import android.view.View
import android.widget.Toast
import com.flawlessconcepts.roadlink.roadlink.RetrofitService
import com.flawlessconcepts.roadlink.roadlinkNetwork.LocationMatrix
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response




    fun distanceMatrix(
        client: RetrofitService,
        locationx: String,
        destinationx: String,
        context: Context
    ): LocationMatrix? {
        var result :String=""
        var result2 =LocationMatrix()
        val LmatrixCall = client.getDistanceMatrix(locationx, destinationx)
        LmatrixCall.enqueue(object : Callback<LocationMatrix?> {
            override fun onResponse(
                call: Call<LocationMatrix?>, response: Response<LocationMatrix?>
            ) {

                result = response.toString()
                try {
                    val distaceMatrix = response.body() as LocationMatrix
//                    result.time = distaceMatrix.time
//                    result.distance = distaceMatrix.distance
//                    result.fromLocation = distaceMatrix.fromLocation
//                    result.toLocation = distaceMatrix.toLocation
                    // return distaceMatrix
                } catch (e: Exception) {

                }


            }


            override fun onFailure(call: Call<LocationMatrix?>, t: Throwable) {

            }
        })

        Toast.makeText(
            context,
            " "+ result,
            Toast.LENGTH_LONG
        ).show()
        return result2

    }



/*
val LmatrixCall = client.getDistanceMatrix(location, destination)
                LmatrixCall.enqueue(object : Callback<LocationMatrix?> {
                    override fun onResponse(
                        call: Call<LocationMatrix?>, response: Response<LocationMatrix?>
                    ) {
                        val statusCode = response.code()

                        Toast.makeText(
                            applicationContext, response.code().toString() +
                                    "/n" + response.body().toString() +"      " +hasReturn, Toast.LENGTH_LONG
                        ).show()
                        try {
                            val distaceMatrix = response.body() as LocationMatrix
                            makeABooking(customer, distaceMatrix)


                        } catch (e: Exception) {
                            progressBarLayOut.visibility = View.INVISIBLE
                            makebookingButton.isEnabled = true
                            Toast.makeText(
                                applicationContext, e.toString(), Toast.LENGTH_LONG
                            ).show()
                        }

                    }

                    override fun onFailure(call: Call<LocationMatrix?>, t: Throwable) {
                        // Log error here since request failed
                        progressBarLayOut.visibility = View.INVISIBLE
                        makebookingButton.isEnabled = true
                        Toast.makeText(applicationContext, "failed" + t.message, Toast.LENGTH_LONG)
                            .show()
                    }
                })
 */