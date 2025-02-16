package com.example.bsaitmattendance.Activity

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bsaitmattendance.Adapter.LeaveRequestAdapter
import com.example.bsaitmattendance.DataClass.LeaveRequest
import com.example.bsaitmattendance.R
import com.example.bsaitmattendance.databinding.ActivityLeaveRequestBinding
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.toObject

class LeaveRequestActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityLeaveRequestBinding.inflate(layoutInflater)
    }
    private lateinit var leaveRequestAdapter: LeaveRequestAdapter
    private lateinit var leaveArray: ArrayList<LeaveRequest>
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
        db = FirebaseFirestore.getInstance()
        leaveArray = ArrayList()


        binding.backBtn.setOnClickListener { finish() }

        val recyclerview = binding.leaveRequestRecyclerview
        recyclerview.layoutManager = LinearLayoutManager(this)
        leaveRequestAdapter = LeaveRequestAdapter(leaveArray, true)
        recyclerview.adapter = leaveRequestAdapter

        fatchStudentLeaveRequest()
    }

    private fun fatchStudentLeaveRequest() {

        val userId = FirebaseAuth.getInstance().currentUser!!.uid
        db.collection("leave_requests")
            .whereEqualTo("teacherId", userId)
            .addSnapshotListener { snapshots, _ ->
                if (snapshots != null) {
                    val leaveList = snapshots.documents.mapNotNull { doc ->
                        doc.toObject(LeaveRequest::class.java)?.copy(leaveId = doc.id)
                    }

                    // 🔥 Sort List By Timestamp (Latest First)
                    val sortedLeaveList = leaveList.sortedByDescending { it.time }

                    leaveRequestAdapter.updateList(sortedLeaveList) // ✅ RecyclerView Update
                }
            }
    }

}


