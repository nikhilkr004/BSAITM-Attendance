package com.example.bsaitmattendance.Activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.bsaitmattendance.Constant
import com.example.bsaitmattendance.DataClass.NoticeData
import com.example.bsaitmattendance.R
import com.example.bsaitmattendance.databinding.ActivityStudentNotificationBinding
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.sql.Time

class StudentNotification : AppCompatActivity() {
    private val binding by lazy {
        ActivityStudentNotificationBinding.inflate(layoutInflater)
    }
    private lateinit var db: FirebaseFirestore
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

        db = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()


        binding.save.setOnClickListener {
            val title=binding.editText.text.toString()
            val disc=binding.dice.text.toString()
            if (title.isNotEmpty() && disc.isNotEmpty()){
                UploadNotification(title,disc)
                Constant.showDialog(this,"Uploading....")
            }
            else{
                Toast.makeText(this, "please enter notice title & disc..", Toast.LENGTH_SHORT).show()
            }

        }


    }

    private fun UploadNotification(title: String, disc: String) {
        val userId = auth.currentUser!!.uid

            val uniqueId = generateFirestoreId()

            if (uniqueId!=null){

                val ref=db.collection("notice").document(uniqueId)
                val data=NoticeData(title=title,disc=disc, Timestamp.now(),uniqueId, userId)

                try {
                    ref.set(data).addOnCompleteListener {
                        if (it.isSuccessful){
                            Toast.makeText(this, "Notice Uploaded...", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this,HomePageActivity::class.java))
                            Constant.hideDialog()
                            finish()
                        }
                    }.addOnFailureListener {
                        Constant.hideDialog()
                        Toast.makeText(this, "Error to upload notice", Toast.LENGTH_SHORT).show()
                    }
                }catch (e:Exception){
                    Log.d("@@","error")
                }


            }


    }
    fun generateFirestoreId(): String {
        return FirebaseFirestore.getInstance().collection("dummy").document().id
    }
}