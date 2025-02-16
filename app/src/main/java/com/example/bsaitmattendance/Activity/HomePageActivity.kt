package com.example.bsaitmattendance.Activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.bsaitmattendance.Constant
import com.example.bsaitmattendance.DataClass.Teacher
import com.example.bsaitmattendance.MainActivity
import com.example.bsaitmattendance.R
import com.example.bsaitmattendance.databinding.ActivityHomePageBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import de.hdodenhof.circleimageview.CircleImageView
import java.util.UUID

class HomePageActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityHomePageBinding.inflate(layoutInflater)
    }
    private var profileImageUri: Uri? = null
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
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
        fetchProfileInfo()

        binding.takeAttendance.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        binding.addnotice.setOnClickListener {
            startActivity(Intent(this, StudentNotification::class.java))
        }

        binding.leaveRequest.setOnClickListener {
            startActivity(Intent(this, LeaveRequestActivity::class.java))
        }

        binding.checkYourNotice.setOnClickListener {
            startActivity(Intent(this,ViewUpdateNoticeActivity::class.java))
        }
        binding.updateProfile.setOnClickListener { updateUserProfile(profileImageUri) }
    }

    private fun updateUserProfile(profileImageUri: Uri?) {
        val dialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.profile_edit_dialog, null)
        val saveBtn = view.findViewById<TextView>(R.id.editprofile)
        val cancel = view.findViewById<TextView>(R.id.cencel)
        val profileImage = view.findViewById<CircleImageView>(R.id.profile_image)
        var name: EditText = view.findViewById(R.id.name)
        val number: EditText = view.findViewById(R.id.number)

        val uid = auth.currentUser!!.uid
        val ref = db.collection("Teacher").document(uid)


        try {
            ref.get().addOnSuccessListener { document ->
                if (document.exists()) {

                    val data = document.toObject(Teacher::class.java)
                    if (data != null) {
                        name.setText(data.name.toString())
                        number.setText(data.number.toString())

                        if (profileImageUri == null) Glide.with(this)
                            .load(data.image)
                            .placeholder(R.drawable.user_).into(profileImage)
                        else Glide.with(this).load(profileImageUri).into(profileImage)
                    }
                }
            }.addOnFailureListener {
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
            }

        } catch (e: Exception) {
            Toast.makeText(this, "try trough an error", Toast.LENGTH_SHORT).show()
        }





        cancel.setOnClickListener {
            dialog.dismiss()
        }

        profileImage.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 100)

        }

        saveBtn.setOnClickListener {
            Constant.showDialog(this, "Wait saving your info....")

            val name = name.text.toString()
            val mobileNo = number.text.toString()


            if (name.isNotEmpty() || mobileNo.isNotEmpty()
            ) {
                uploadImageToFirebase(
                    name,
                    mobileNo,
                    dialog
                )
            } else {
                Toast.makeText(this, "fill all the fields", Toast.LENGTH_SHORT).show()
            }
        }




        dialog.setCancelable(false)
        dialog.setContentView(view)
        dialog.show()
    }

    private fun uploadImageToFirebase(name: String, mobileNo: String, dialog: BottomSheetDialog) {
        val imageFileName = UUID.randomUUID().toString() + ".jpg"
        val storage = FirebaseStorage.getInstance().reference
        val imagesRef = storage.child("profile_image/$imageFileName")

        try {
            imagesRef.putFile(profileImageUri!!).addOnSuccessListener {

                try {
                    imagesRef.downloadUrl.addOnSuccessListener { uri ->
                        saveDataToFirebase(
                            name,
                            mobileNo,
                            dialog,
                            uri
                        )
                    }
                } catch (e: Exception) {
                    Log.d("@@@", "error")
                }

            }
        } catch (e: Exception) {
            Log.d("@@@@@", "error")
        }

    }

    private fun saveDataToFirebase(
        name: String,
        mobileNo: String,
        dialog: BottomSheetDialog,
        uri: Uri?
    ) {

        val student = Teacher(
            name = name,
            number = mobileNo,
            image = uri.toString()
        )

        val studentMap = mutableMapOf<String, Any>().apply {
            student.name?.let { put("name", it) }
            student.number?.let { put("number", it) }

            student.image?.let { put("image", it) }
        }


        val uid = auth.currentUser!!.uid
        val ref = db.collection("Teacher").document(uid)

        try {
            ref.update(studentMap).addOnSuccessListener {
                Toast.makeText(this, "Successful", Toast.LENGTH_SHORT).show()
                Constant.hideDialog()
                dialog.dismiss()
            }
                .addOnFailureListener {
                    Toast.makeText(this, "error to save data ", Toast.LENGTH_SHORT).show()
                }
        } catch (e: Exception) {
            Log.d("@@@", "error")
        }

    }

    /// upload image on storage
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {
            profileImageUri = data.data
            updateUserProfile(profileImageUri)
        }
    }


    private fun fetchProfileInfo() {
        val uid = auth.currentUser!!.uid
        val ref = db.collection("Teacher").document(uid)


        try {
            ref.get().addOnSuccessListener { document ->
                if (document.exists()) {
                    val data = document.toObject(Teacher::class.java)
                    if (data != null) {

                        Glide.with(this).load(data.image).placeholder(R.drawable.user_).into(binding.userProfileImage)

                        binding.userName.text = data.name.toString()
                        binding.email.text = data.email.toString()
                    }
                }
            }.addOnFailureListener {
                Toast.makeText(this, "Error to Fetch data", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            Log.d("@@@@@", "Error to fetch data")
        }


    }
}

