package com.flawlessconcepts.roadlink2

import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.flawlessconcepts.roadlink.roadlink.RetrofitService
import com.flawlessconcepts.roadlink.roadlink.ServiceGenerator
import com.flawlessconcepts.roadlink.roadlinkNetwork.CustomerItem
import com.google.android.material.button.MaterialButton
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    var RegisterButton: MaterialButton? = null

    lateinit var progressBar: ProgressBar
    lateinit var textPhoneNum: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val registerButton = findViewById<MaterialButton>(R.id.register)
        val loginButton = findViewById<MaterialButton>(R.id.login)


       progressBar = findViewById(R.id.progressBar) as ProgressBar
       textPhoneNum = findViewById(R.id.editTextPhoneNum) as EditText



        val client = ServiceGenerator.createService(RetrofitService::class.java)
        registerButton.setOnClickListener { openRegister() }

        loginButton.setOnClickListener { openMenu(client) }


    }


    private fun openRegister() {
        val startRegister = Intent(this@MainActivity, RegisterActivity::class.java)
        startActivity(startRegister)
    }

    private fun openMenu(client: RetrofitService) {

        val phone = editTextPhoneNum.text.toString()
        if (!phone.isEmpty()){
        progressBar.visibility = View.VISIBLE

        val call = client.getCustomer(phone)
        call.enqueue(object : Callback<CustomerItem?> {
            override fun onResponse(
                call: Call<CustomerItem?>,
                response: Response<CustomerItem?>
            ) {
                progressBar.visibility = View.INVISIBLE
                val statusCode = response.code()
                try {
                    val customer = response.body() as CustomerItem
                   // Toast.makeText(applicationContext, customer.customerFirstName, Toast.LENGTH_LONG).show()
                    ///Start Up MenuActivity while passing Customer object as Parameter

                    val startMenu = Intent(this@MainActivity, MenuActivity::class.java)
                    startMenu.putExtra("CUSTOMER",customer)
                    startActivity(startMenu)
                }catch (e:Exception){ }
            }

            override fun onFailure(call: Call<CustomerItem?>, t: Throwable) {
                progressBar.visibility = View.INVISIBLE
                // Log error here since request failed\
                Toast.makeText(applicationContext, "failed: "+ "Please Check Your Network Connection and try again", Toast.LENGTH_LONG).show()

            }
        })
        }else Toast.makeText(applicationContext, "Phone Number Can not be Empty", Toast.LENGTH_LONG).show()


    }

    fun Context.gotoClass(targetType: Class<*>) =
        ComponentName(this, targetType)

    fun Context.startActivity(f: Intent.() -> Unit): Unit =
        Intent().apply(f).run(this::startActivity)

    inline fun <reified T : Activity> Context.start(
        noinline createIntent: Intent.() -> Unit = {}
    ) = startActivity {
        component = gotoClass(T::class.java)
        createIntent(this)
    }


}