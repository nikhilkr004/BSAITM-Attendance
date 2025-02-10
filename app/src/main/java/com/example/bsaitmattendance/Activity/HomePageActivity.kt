package com.example.bsaitmattendance.Activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.bsaitmattendance.MainActivity
import com.example.bsaitmattendance.R
import com.example.bsaitmattendance.databinding.ActivityHomePageBinding

class HomePageActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityHomePageBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.takeAttendance.setOnClickListener {
            startActivity(Intent(this,MainActivity::class.java))
        }

        binding.addnotice.setOnClickListener {
            startActivity(Intent(this,StudentNotification::class.java))
        }

    }
}