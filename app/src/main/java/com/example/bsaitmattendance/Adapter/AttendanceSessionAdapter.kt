package com.example.bsaitmattendance.Adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bsaitmattendance.DataClass.AttendanceSession
import com.example.bsaitmattendance.R
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry

class AttendanceSessionAdapter (private var sessions: List<AttendanceSession>) :
    RecyclerView.Adapter<AttendanceSessionAdapter.AttendanceSessionViewHolder>() {

        fun updateList(newlist:List<AttendanceSession>) {
            sessions=newlist
            notifyDataSetChanged()
        }

    class AttendanceSessionViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val teacherName: TextView = view.findViewById(R.id.textView10)
        val date: TextView = view.findViewById(R.id.date)
        val classDetails: TextView = view.findViewById(R.id.courses)
        val subject: TextView = view.findViewById(R.id.subject)
        val presentCount: TextView = view.findViewById(R.id.numberOfStudent)
        val pieChart: PieChart = view.findViewById(R.id.pieChart)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AttendanceSessionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.attendance_session_item, parent, false)
        return AttendanceSessionViewHolder(view)
    }

    override fun onBindViewHolder(holder: AttendanceSessionViewHolder, position: Int) {
        val session = sessions[position]

        holder.teacherName.text = "${session.teacherName}"
        holder.date.text = "${session.date}"
        holder.classDetails.text = "${session.course} - ${session.branch} - ${session.semester} - ${session.batch}"
        holder.subject.text = "${session.subject}"
        holder.presentCount.text = "${session.presentCount}"

        // Setup Pie Chart
        setupPieChart(holder.pieChart, session.presentCount, session.totalStudents)
    }

    override fun getItemCount(): Int = sessions.size

    private fun setupPieChart(pieChart: PieChart, present: Int, total: Int) {
        val absent = total - present
        val entries = listOf(
            PieEntry(present.toFloat(), ""),
            PieEntry(absent.toFloat(), "")
        )

        val dataSet = PieDataSet(entries, "Attendance")
        dataSet.colors = listOf(Color.GREEN, Color.RED)
        dataSet.valueTextSize = 14f

        val pieData = PieData(dataSet)
        pieChart.data = pieData
        pieChart.description.isEnabled = false
        pieChart.legend.isEnabled = false
        pieChart.setUsePercentValues(true)
        pieChart.invalidate() // Refresh chart
    }
}