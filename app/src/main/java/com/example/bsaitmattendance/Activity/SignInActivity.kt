package com.example.bsaitmattendance.Activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.bsaitmattendance.R
import com.example.bsaitmattendance.databinding.ActivitySignInBinding
import com.google.firebase.auth.FirebaseAuth

class SignInActivity : AppCompatActivity() {
private val binding by lazy {
    ActivitySignInBinding.inflate(layoutInflater)
}
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        auth=FirebaseAuth.getInstance()

        binding.signUp.setOnClickListener {
            signIn()
        }

        binding.forgetPassword.setOnClickListener {
            showForgotPasswordDialog(this)
        }
    }

    private fun signIn() {
        val email=binding.email.text.toString()
        val password=binding.password.text.toString()

        if (email.isEmpty()){
            binding.email.setError("Enter email")
        }
        if (password.isEmpty()){
            binding.password.setError("Enter Password")
        }
        else{
            auth.signInWithEmailAndPassword(email,password).addOnSuccessListener {
                startActivity(Intent(this,HomePageActivity::class.java))
                finish()
                Toast.makeText(this, "SignIn Successful...", Toast.LENGTH_SHORT).show()
            }
                .addOnFailureListener {
                    Toast.makeText(this, "${it.message}", Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun showForgotPasswordDialog(context: Context) {
        val auth = FirebaseAuth.getInstance()

        // Create an EditText for user input
        val emailEditText = EditText(context)
        emailEditText.hint = "Enter your email"


        // Create AlertDialog
        val dialog = AlertDialog.Builder(context)
            .setTitle("Reset Password")
            .setMessage("Enter your email to receive a password reset link.")
            .setView(emailEditText)
            .setPositiveButton("Reset") { _, _ ->
                val email = emailEditText.text.toString().trim()

                if (email.isEmpty()) {
                    Toast.makeText(context, "Please enter your email", Toast.LENGTH_SHORT).show()
                    return@setPositiveButton
                }

                // Send password reset email
                auth.sendPasswordResetEmail(email)
                    .addOnSuccessListener {
                        Toast.makeText(context, "Reset link sent to your email", Toast.LENGTH_LONG).show()
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(context, "Failed: ${e.message}", Toast.LENGTH_LONG).show()
                    }
            }
            .setNegativeButton("Cancel", null)
            .create()

        dialog.show()
    }
    }
