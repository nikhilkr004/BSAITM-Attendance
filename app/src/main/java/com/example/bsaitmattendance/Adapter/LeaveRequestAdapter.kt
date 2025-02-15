package com.example.bsaitmattendance.Adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.bsaitmattendance.DataClass.LeaveRequest
import com.example.bsaitmattendance.DataClass.StudentData
import com.example.bsaitmattendance.databinding.LeaveRequestItemBinding
import com.github.marlonlom.utilities.timeago.TimeAgo
import com.google.firebase.firestore.FirebaseFirestore

class LeaveRequestAdapter(val data:List<LeaveRequest>):RecyclerView.Adapter<LeaveRequestAdapter.ViewHolder>() {
    class ViewHolder(val binding: LeaveRequestItemBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(data: LeaveRequest) {
            val db=FirebaseFirestore.getInstance()
            val context=binding.root.context
            binding.leaveTitle.text=data.title
            binding.leaveDuration.text="${data.fromDate} - ${data.toDate}"
            binding.leaveTitle.text=data.title
            binding.time.text=TimeAgo.using(data.time.toLong())
            binding.acceptBtn.setOnClickListener { updateStatus(
                "Accepted",
                data.leaveId,
                context,
                db
            ) }
            binding.textView11.setOnClickListener { updateStatus(
                "Rejected",
                data.leaveId,
                context,db
            ) }

            try {

                val ref = db.collection("students").document(data.studentId)
                ref.get().addOnSuccessListener {
                        document->

                    if (document.exists()) {

                        val userdata = document.toObject(StudentData::class.java)

                        try {
                            binding.studentName.text = userdata!!.name.toString()

                        }catch (e:Exception){

                        }



                    }
                }.addOnFailureListener { Toast.makeText(context, "error to fetch data ", Toast.LENGTH_SHORT).show() }
            }catch (e:Exception){
                Log.d("@@","error")
            }



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
        val inflater=LayoutInflater.from(parent.context)
        val binding=LeaveRequestItemBinding.inflate(inflater,parent, false)
        return  ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data=data[position]
        holder.bind(data)
    }
}