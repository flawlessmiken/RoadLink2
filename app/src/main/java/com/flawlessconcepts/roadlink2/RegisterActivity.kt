package com.flawlessconcepts.roadlink2

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
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


class RegisterActivity : AppCompatActivity() {

    private var firstName: String? = null
    private var lastName: String? = null
    private var phone: String? = null

    lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)



        progressBar = findViewById(R.id.progressBar) as ProgressBar
        val registerButton = findViewById<MaterialButton>(R.id.bt_register)
        registerButton.setOnClickListener { registerClicked() }

    }



    private fun registerClicked() {
        val fstName: TextView = findViewById(R.id.tv_firstname)
        val lsName: TextView = findViewById(R.id.tv_lastname)
        val phoneNum: TextView = findViewById(R.id.tv_Phone)
        firstName = fstName.text.toString()
        lastName = lsName.text.toString()
        phone = phoneNum.text.toString()

        if ( !firstName.isNullOrEmpty() && !lastName.isNullOrEmpty() && !phone.isNullOrEmpty() ){
            val newCustomerItem =CustomerItem(firstName!!, lastName!!,phone!!)
            val client = ServiceGenerator.createService(RetrofitService::class.java)
            NetWorkCalls(client,newCustomerItem)
        }
    }


    fun NetWorkCalls(client: RetrofitService, customer: CustomerItem){

        progressBar.visibility = View.VISIBLE
        val registerCall = client.registerCustomer(customer.customerFirstName,customer.customerLastName,customer.customerPhone)
        registerCall.enqueue(object : Callback<CustomerItem?> {
            override fun onResponse(
                call: Call<CustomerItem?>, response: Response<CustomerItem?>
            ) {
                val statusCode = response.code()
                val startMenu = Intent(applicationContext, MenuActivity::class.java)
                startMenu.putExtra("CUSTOMER",customer)
                startActivity(startMenu)
                finish()
                try {


                } catch (e: Exception) {
                    progressBar.visibility = View.INVISIBLE
                    Toast.makeText(
                        applicationContext, e.toString(), Toast.LENGTH_LONG
                    ).show()
                }


            }

            override fun onFailure(call: Call<CustomerItem?>, t: Throwable) {
                progressBar.visibility = View.INVISIBLE
                Toast.makeText(applicationContext, "failed" + t.message, Toast.LENGTH_LONG)
                    .show()
            }
        })
    }
}