package com.example.bsaitmattendance.Activity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bsaitmattendance.Adapter.NoticeAdapter
import com.example.bsaitmattendance.DataClass.NoticeData
import com.example.bsaitmattendance.R
import com.example.bsaitmattendance.databinding.ActivityViewUpdateNoticeBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ViewUpdateNoticeActivity : AppCompatActivity() {
    val binding by lazy {
        ActivityViewUpdateNoticeBinding.inflate(layoutInflater)
    }
  private lateinit var db:FirebaseFirestore
  private lateinit var auth: FirebaseAuth
  private lateinit var noticeAdapter: NoticeAdapter
  private lateinit var noticeData:ArrayList<NoticeData>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        db=FirebaseFirestore.getInstance()
        auth=FirebaseAuth.getInstance()


        noticeData= ArrayList()

        /// back button

        binding.imageView4.setOnClickListener { finish() }

        val recyclerView = binding.noticeRecycler
        recyclerView.layoutManager =
            LinearLayoutManager(this)
        noticeAdapter = NoticeAdapter(noticeData)
        recyclerView.adapter = noticeAdapter

        fetchNoticeInfo()
    }




    private fun fetchNoticeInfo() {

        val uid=auth.currentUser!!.uid

        val ref = db.collection("notice")

        try {
            ref.get().addOnSuccessListener { docuent ->
                noticeData.clear()
                for (document in docuent) {
                    val data = document.toObject(NoticeData::class.java)

                    if (data != null && data.userUid==uid ) {
                        noticeData.add(data)
                        noticeData.reverse()

                    }
                    noticeAdapter.notifyDataSetChanged()
                }
            }.addOnFailureListener {
                Toast.makeText(
                    this,
                    "error to fetch notice",
                    Toast.LENGTH_SHORT
                ).show()
            }
        } catch (e: Exception) {

        }


    }

}