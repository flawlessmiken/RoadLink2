package com.flawlessconcepts.roadlink2

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import android.widget.AdapterView.OnItemSelectedListener
import androidx.appcompat.app.AppCompatActivity
import com.flawlessconcepts.roadlink.roadlink.RetrofitService
import com.flawlessconcepts.roadlink.roadlink.ServiceGenerator
import com.flawlessconcepts.roadlink.roadlinkNetwork.BookingItem
import com.flawlessconcepts.roadlink.roadlinkNetwork.CustomerItem
import com.flawlessconcepts.roadlink.roadlinkNetwork.LocationMatrix
import com.google.android.material.button.MaterialButton
import kotlinx.android.synthetic.main.activity_make_booking.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MakeBookingActivity : AppCompatActivity() {

    private var spinner: Spinner? = null
    private var hasReturn: String? = "false"
    private var destination: String? = null
    private var location: String? = null
    private var departureTime: String? = null
    private var numPassengers: String? = null
    private var locationAddress: String? = ""

    lateinit var progressBarLayOut: LinearLayout
    lateinit var makebookingButton: MaterialButton


    var radioGroupTime: RadioGroup? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_make_booking)

        val customer = intent.getParcelableExtra<CustomerItem>("CUSTOMER")
        progressBarLayOut = findViewById(R.id.layoutProgressBar) as LinearLayout



        DestinationSpinner()
        hasReturnSpinner()
        LocationSpinner()


        makebookingButton = findViewById<MaterialButton>(R.id.bt_book_now)
        makebookingButton.setOnClickListener {

            val numPassengerstex: TextView = findViewById(R.id.numPassenger)
            numPassengers =numPassengerstex.text.toString()

            setUptime()
            if (customer != null) {

                if( numPassengers !=null){
                    makebookingButton.isEnabled = false
                    progressBarLayOut.visibility = View.VISIBLE

                    val client = ServiceGenerator.createService(RetrofitService::class.java)
                    getDistanceMatrix(customer,client)
                }else{
                    Toast.makeText(
                        applicationContext,
                        "failed: " + "Please Ensure You Enter every Value correctly", Toast.LENGTH_LONG
                    ).show()
                }

            }else{
                progressBarLayOut.visibility = View.INVISIBLE
                val gotoLogin = Intent(this@MakeBookingActivity, MainActivity::class.java)
                startActivity(gotoLogin)
            }
            // makeBooking()
        }


    }

//    private fun makeBooking() {
//        val makeBooking = Intent(this@MakeBookingActivity, ConfirmActivity::class.java)
//        startActivity(makeBooking)
//    }

    fun hasReturnSpinner() {
        spinner = findViewById<View>(R.id.spinner) as Spinner
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


    fun DestinationSpinner() {

        val array: Array<String> = resources.getStringArray(R.array.destination)
        array.sort()
        spinner = findViewById<View>(R.id.spinnerDes) as Spinner
        spinner!!.adapter =
            ArrayAdapter<String>(applicationContext, android.R.layout.simple_list_item_1, array)

        spinner!!.setOnItemSelectedListener(object : OnItemSelectedListener {
            override fun onItemSelected(
                adapterView: AdapterView<*>,
                view: View,
                i: Int,
                l: Long
            ) {
                destination = adapterView.selectedItem.toString()
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {}
        })
    }

    fun LocationSpinner() {

        val array: Array<String> = resources.getStringArray(R.array.Location)
        array.sort()
        spinner = findViewById<View>(R.id.spinnerLoc) as Spinner
        spinner!!.adapter =
            ArrayAdapter<String>(applicationContext, android.R.layout.simple_list_item_1, array)

        spinner!!.setOnItemSelectedListener(object : OnItemSelectedListener {
            override fun onItemSelected(
                adapterView: AdapterView<*>,
                view: View,
                i: Int,
                l: Long
            ) {
                location = adapterView.selectedItem.toString()
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {}
        })
    }


    fun setUptime() {
        radioGroupTime = findViewById(R.id.radioGender)
        val selectButton: RadioButton = findViewById(radioGroupTime!!.checkedRadioButtonId)
        val tvTime: TextView = findViewById(R.id.departureTime)
        if (!tvTime.text.isEmpty()) {
            departureTime = tvTime.text.toString() + " " + selectButton.text.toString()
        }
        // radioGroupTime = findViewById(intSelectButton)
    }



    fun getDistanceMatrix(customer: CustomerItem, client: RetrofitService) {



        if (numPassengers.toString().toInt() > 12 || numPassengers.toString().toInt() < 1) {
            progressBarLayOut.visibility = View.INVISIBLE
            makebookingButton.isEnabled = true
            Toast.makeText(
                applicationContext,
                "Number of Passengers can only be between 1 and 12",
                Toast.LENGTH_LONG
            ).show()
        } else {
            if (location != null && destination != null && departureTime != null && numPassengers != null) {


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


            } else {
                progressBarLayOut.visibility = View.INVISIBLE
                makebookingButton.isEnabled = true
                Toast.makeText(
                    applicationContext,
                    "failed: " + "Please Ensure You Enter every Value correctly", Toast.LENGTH_LONG
                ).show()

            }
        }


    }


    fun makeABooking(customer: CustomerItem, locationMatrix: LocationMatrix) {
        val tvNumPass = findViewById<TextView>(R.id.numPassenger)
        numPassengers = tvNumPass.text.toString()
        val locAddress = findViewById<TextView>(R.id.locationAddress)
        locationAddress = locAddress.text.toString()

        val bookingItem = BookingItem()
        var bk = bookingItem.copy(
            location = location!!,
            destination = destination!!,
            isHasReturn = hasReturn,
            calculatedCost = getCalculatedCost(locationMatrix),
            customerID = customer.customerPhone,
            departureTime = departureTime!!,
            locationAddress = locationAddress!!,
            numbOfPassengers = numPassengers!!.toInt(),
            tripDistance =getTripDistance(locationMatrix)
        )

        Toast.makeText(applicationContext, "successs" + bk.calculatedCost + bk.departureTime+ " " + bk.tripDistance, Toast.LENGTH_LONG)
            .show()

        progressBarLayOut.visibility = View.INVISIBLE

        val makeBooking = Intent(this@MakeBookingActivity, ConfirmActivity::class.java)
        makeBooking.putExtra("BOOKING",bk)
        startActivity(makeBooking)
    }


    fun getTripDistance(locationMatrix: LocationMatrix): Double {
        var distance: Double = 0.0
        if (locationMatrix.toLocation.equals("Enugu-North", true)) {
            if (hasReturn.equals("True", true)) {
                return 30.0
            } else {
                return 20.0
            }
        } else {
            distance += 9
            if (hasReturn.equals("true", true)) {
                distance += locationMatrix.distance.toDouble() * 2.0
                return distance
            } else {
                distance += locationMatrix.distance.toString().toDouble()
                return distance
            }
        }
    }


    fun getCalculatedCost(locationMatrix: LocationMatrix): Double {
        var totalTime: Double =0.0
        var cost: Double =0.0

        if (locationMatrix.toLocation.equals("Enugu-North", true)) {
            if (hasReturn.equals("true", true)) {
                totalTime = 40.0
                cost += 12.5 * totalTime * capacity(numPassengers!!.toString().toInt())
                return cost
            } else {
                totalTime = 25.0

                cost+= 15.0 * totalTime *capacity(numPassengers!!.toString().toInt())
                return cost

            }
        } else {
            totalTime += 11
            if (hasReturn.equals("true", true)) {
                totalTime += locationMatrix.time.toDouble() * 2
                cost+= 12.5 * totalTime * capacity(numPassengers!!.toString().toInt())
                return cost
            } else {
                totalTime += locationMatrix.time.toDouble()
                cost += 12.5 * totalTime * capacity(numPassengers!!.toString().toInt())
                return  cost
            }
        }
    }


    fun capacity(numberPassengers: Int): Double {
        when (numberPassengers) {
            in 1..3 -> return 3.0
            in 4..7 -> return 7.0
            in 8..12 -> return 12.0
            else -> return 0.0
        }
    }


}