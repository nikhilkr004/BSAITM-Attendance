package com.example.bsaitmattendance.Adapter

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.bsaitmattendance.DataClass.LeaveRequest
import com.example.bsaitmattendance.DataClass.StudentData
import com.example.bsaitmattendance.databinding.LeaveRequestItemBinding
import com.github.marlonlom.utilities.timeago.TimeAgo
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.Locale

class LeaveRequestAdapter(var data: List<LeaveRequest>, private val isTeacherView: Boolean) :
    RecyclerView.Adapter<LeaveRequestAdapter.ViewHolder>() {



    fun updateList(newList: List<LeaveRequest>) {
        data=newList
        notifyDataSetChanged()  // 🔥 RecyclerView ko refresh karega
    }


    class ViewHolder(val binding: LeaveRequestItemBinding) : RecyclerView.ViewHolder(binding.root) {

        val isTeacherView:Boolean=true
        fun bind(data: LeaveRequest, isTeacherView: Boolean) {
            val db = FirebaseFirestore.getInstance()
            val context = binding.root.context
            binding.leaveTitle.text = data.title
            binding.leaveDuration.text = "${data.fromDate} - ${data.toDate}"
            binding.leaveTitle.text = data.title

            binding.time.text=convertTimestampToDate(data.time)

            binding.textView4.text=data.status
            if (data.status=="Accepted"){
                binding.textView4.setTextColor(Color.GREEN)
            }
            else if (data.status=="Rejected"){
                binding.textView4.setTextColor(Color.RED)
            }

            if (isTeacherView) {
                // 🔥 Agar request "Accepted" ya "Rejected" hai, toh buttons hide kar do
                if (data.status == "Accepted" || data.status == "Rejected") {
                    binding.acceptBtn.visibility = View.GONE
                    binding.textView4.visibility=View.VISIBLE
                    binding.textView11.visibility = View.GONE
                } else {
                    binding.acceptBtn.visibility = View.VISIBLE
                    binding.textView11.visibility = View.VISIBLE

                    binding.acceptBtn.setOnClickListener {
                        updateStatus("Accepted",data.leaveId,context,db)
                    }
                    binding.textView11.setOnClickListener {
                        updateStatus("Rejected",data.leaveId,context,db)
                    }
                }
            } else {
                binding.acceptBtn.visibility = View.GONE
                binding.textView11.visibility = View.GONE
                binding.textView4.visibility=View.VISIBLE
            }


            try {

                val ref = db.collection("students").document(data.studentId)
                ref.get().addOnSuccessListener { document ->

                    if (document.exists()) {

                        val userdata = document.toObject(StudentData::class.java)

                        try {
                            binding.studentName.text = userdata!!.name.toString()

                        } catch (e: Exception) {

                        }


                    }
                }.addOnFailureListener {
                    Toast.makeText(
                        context,
                        "error to fetch data ",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } catch (e: Exception) {
                Log.d("@@", "error")
            }


        }

        private fun convertTimestampToDate(time: Timestamp?): CharSequence? {
            return time?.toDate()?.let {
                val sdf = SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault()) // 🔥 Format Define
                sdf.format(it)
            } ?: "N/A" // Default Value if Timestamp is Null
        }

        private fun updateStatus(
            status: String,
            leaveId: String,
            context: Context,
            db: FirebaseFirestore
        ) {
            db.collection("leave_requests").document(leaveId).update("status", status)
                .addOnSuccessListener {
                    Toast.makeText(context, "Status Updated to $status", Toast.LENGTH_SHORT).show()


                }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = LeaveRequestItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = data[position]
        holder.bind(data, isTeacherView)
    }
}