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

    var locationMatrixOne= LocationMatrix()
    var locationMatrixTwo= LocationMatrix()




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
        val address: TextView = findViewById(R.id.locationAddress)
        locationAddress = address.text.toString()
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

                NetWorkCalls(client, customer)

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


    fun NetWorkCalls(client: RetrofitService,customer: CustomerItem){

        val costCall = client.calculateCostService(location,destination, hasReturn,
            departureTime,locationAddress,customer.customerPhone,numPassengers)
        costCall.enqueue(object : Callback<BookingItem?> {
            override fun onResponse(
                call: Call<BookingItem?>, response: Response<BookingItem?>
            ) {
                val statusCode = response.code()
                try {
                    val booking = response.body() as BookingItem
                    val startMenu = Intent(applicationContext, ConfirmActivity::class.java)
                    startMenu.putExtra("BOOKING",booking)
                    startActivity(startMenu)
                    finish()

                    } catch (e: Exception) {
                        progressBarLayOut.visibility = View.INVISIBLE
                        makebookingButton.isEnabled = true
                        Toast.makeText(
                            applicationContext, e.toString(), Toast.LENGTH_LONG
                        ).show()
                    }


            }

            override fun onFailure(call: Call<BookingItem?>, t: Throwable) {
                // Log error here since request failed
                progressBarLayOut.visibility = View.INVISIBLE
                makebookingButton.isEnabled = true
                Toast.makeText(applicationContext, "failed" + t.message, Toast.LENGTH_LONG)
                    .show()
            }
        })
    }


    override fun onBackPressed() {
        super.onBackPressed()

    }
}