package com.example.bsaitmattendance.Activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.bsaitmattendance.Constant
import com.example.bsaitmattendance.DataClass.Teacher
import com.example.bsaitmattendance.R
import com.example.bsaitmattendance.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Date
import java.util.Locale

class SignUpActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivitySignUpBinding.inflate(layoutInflater)
    }
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()


        binding.signInTxt.setOnClickListener {
            startActivity(Intent(this,SignInActivity::class.java))
        }

        binding.signUp.setOnClickListener {
            signUpTeacher()
        }
    }


    private fun signUpTeacher() {
        Constant.showDialog(this,"signing up....")
        val name = binding.name.text.toString()
        val email = binding.email.text.toString()
        val password = binding.password.text.toString()

        if (name.isEmpty()) {
            binding.name.setError("Enter Name")
            Constant.hideDialog()
        }
        if (email.isEmpty()) {
            binding.email.setError("Enter Email")
            Constant.hideDialog()
        }
        if (password.isEmpty()) {
            binding.password.setError("Enter Password")
            Constant.hideDialog()
        } else {
            auth.createUserWithEmailAndPassword(email, password).addOnSuccessListener {

                val currentUser = auth.currentUser!!.uid
                val date = currentDate()
                val teacher = Teacher(name, email, currentUser, date)

               db.collection("Teacher").document(currentUser).set(teacher)
                    .addOnCompleteListener() {
                        if (it.isSuccessful) {
                            Constant.hideDialog()
                          startActivity(Intent(this,HomePageActivity::class.java))
                        }
                    }
                   .addOnFailureListener {
                       Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                       Constant.hideDialog()
                   }
            }

        }


    }

    private fun currentDate(): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val currentDate = sdf.format(Date())
        return currentDate
    }

}