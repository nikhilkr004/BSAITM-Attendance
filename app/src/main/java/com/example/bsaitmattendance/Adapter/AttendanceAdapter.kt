package com.example.bsaitmattendance.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bsaitmattendance.R

class AttendanceAdapter(
    private val students: List<Student>,
    private val onAttendanceSelected: (Int, String) -> Unit, // Callback for attendance selection
    private val onPresentCountUpdated: (Int) -> Unit // Callback for present count update
) : RecyclerView.Adapter<AttendanceAdapter.AttendanceViewHolder>() {

    private var presentCount = students.count { it.status == "Present" } // Initialize present count

    inner class AttendanceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val studentNameTextView: TextView = itemView.findViewById(R.id.tvStudentName)
        val radioGroup: RadioGroup = itemView.findViewById(R.id.rgAttendance)
        val radioButtonPresent: RadioButton = itemView.findViewById(R.id.rbPresent)
        val radioButtonAbsent: RadioButton = itemView.findViewById(R.id.rbAbsent)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AttendanceViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.student_item, parent, false)
        return AttendanceViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: AttendanceViewHolder, position: Int) {
        val student = students[position]
        holder.studentNameTextView.text = student.name

        // **Disable listener before setting selection**
        holder.radioGroup.setOnCheckedChangeListener(null)

        // **Preselect the radio button based on attendance status**
        when (student.status) {
            "Present" -> holder.radioButtonPresent.isChecked = true
            "Absent" -> holder.radioButtonAbsent.isChecked = true
            else -> holder.radioGroup.clearCheck()
        }

        // **Enable listener after setting selection**
        holder.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            val previousStatus = student.status
            val selectedStatus = when (checkedId) {
                R.id.rbPresent -> "Present"
                R.id.rbAbsent -> "Absent"
                else -> return@setOnCheckedChangeListener // Ignore invalid selections
            }

            if (selectedStatus != previousStatus) { // **Only update if status actually changes**
                student.status = selectedStatus
                onAttendanceSelected(position, selectedStatus)

                // **Update present count correctly**
                if (selectedStatus == "Present" && previousStatus!="Present") {
                    presentCount++ // **Increase present count when marking present**
                } else if (selectedStatus == "Absent" && previousStatus=="Present") {
                    presentCount-- // **Decrease present count when marking absent**
                }

                // **Notify activity to update present count**
                onPresentCountUpdated(presentCount)
            }
        }
    }

    override fun getItemCount() = students.size
}
