package com.example.bsaitmattendance.Activity

import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bsaitmattendance.Adapter.Color_Piker
import com.example.bsaitmattendance.Constant
import com.example.bsaitmattendance.R
import com.example.bsaitmattendance.databinding.ActivityStudentNotificationBinding

class StudentNotification : AppCompatActivity() {
    private val binding by lazy {
        ActivityStudentNotificationBinding.inflate(layoutInflater)
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


        binding.save.setOnClickListener {
            UploadNotification()
        }




    }

    private fun UploadNotification() {
        val title=binding.editText.text.toString()
        val disc=binding.dice.text.toString()

//        Constant.database().setValue()
    }

}