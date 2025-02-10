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
    private val onAttendanceSelected: (Int, String) -> Unit, // Callback for selected attendance status
    private val onPresentCountUpdated: (Int) -> Unit // Callback for updating present count
) : RecyclerView.Adapter<AttendanceAdapter.AttendanceViewHolder>() {

    private var presentCount = 0 // Track the number of students marked as present

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

        // Preselect the radio button based on the student's current attendance status
        when (student.status) {
            "Present" -> holder.radioButtonPresent.isChecked = true
            "Absent" -> holder.radioButtonAbsent.isChecked = true
            else -> holder.radioGroup.clearCheck()
        }

        // Handle radio button selection and update attendance status
        holder.radioGroup.setOnCheckedChangeListener { group, checkedId ->
            val previousStatus = student.status
            val selectedStatus = when (checkedId) {
                R.id.rbPresent -> "Present"
                R.id.rbAbsent -> "Absent"
                else -> null
            }

            if (selectedStatus != null) {
                // Update the student's attendance status
                student.status = selectedStatus
                onAttendanceSelected(position, selectedStatus)

                // Update present count based on selection
                if (selectedStatus == "Present" && previousStatus != "Present") {
                    presentCount++
                } else if (selectedStatus == "Absent" && previousStatus == "Absent") {
                    presentCount--
                }

                // Notify activity to update the present count
                onPresentCountUpdated(presentCount)
            }
        }
    }

    override fun getItemCount() = students.size
}