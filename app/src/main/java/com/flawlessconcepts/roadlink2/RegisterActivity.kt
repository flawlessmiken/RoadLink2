package com.flawlessconcepts.roadlink2

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val registerButton = findViewById<MaterialButton>(R.id.bt_register)
        registerButton.setOnClickListener { registerClicked() }
    }

    private fun registerClicked() {
        val startMenuActivity = Intent(this@RegisterActivity, MenuActivity::class.java)
        startActivity(startMenuActivity)
    }
}