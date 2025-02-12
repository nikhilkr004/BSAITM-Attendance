package com.example.bsaitmattendance

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bsaitmattendance.Adapter.AttendanceAdapter
import com.example.bsaitmattendance.Adapter.Student
import com.example.bsaitmattendance.Constant.diplomcomputersem1b1
import com.example.bsaitmattendance.Constant.diplomcomputersem2b1
import com.example.bsaitmattendance.Constant.diplomcomputersem3b1
import com.example.bsaitmattendance.Constant.diplomcomputersem4b1
import com.example.bsaitmattendance.Constant.diplomcomputersem5b1
import com.example.bsaitmattendance.Constant.diplomcomputersem6b1
import com.example.bsaitmattendance.databinding.ActivityMainBinding
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val students: MutableList<Student> = mutableListOf()
    private lateinit var db: FirebaseFirestore



    private lateinit var attendanceAdapter: AttendanceAdapter
    private lateinit var presentCount: String
    private val attendanceMap = mutableMapOf<String, String?>()
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
        setUpAutoText()

        binding.search.setOnClickListener {
            getUserInfo()
        }

        //back button
        binding.imageView2.setOnClickListener {
            finish()
        }


        // Setup RecyclerView
        val recyclerView = binding.recyclerview
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Initialize the Adapter
        attendanceAdapter = AttendanceAdapter(
            students,
            onAttendanceSelected = { position, status ->
                // Update attendance status for the student in the map
                val student = students[position]
                student.status = status
                attendanceMap[student.id!!] = status
            },
            onPresentCountUpdated = { updatedPresentCount ->
                // Update the present count and display it
                presentCount = updatedPresentCount.toString()
                binding.presentStudent.text = presentCount

            }
        )

        recyclerView.adapter = attendanceAdapter


        // Submit attendance button
        binding.submit.setOnClickListener {
            Constant.showDialog(this, "please wait...")
            submitAttendance()

        }


    }

    private fun submitAttendance() {
        val course = binding.courses.text.toString()
        val branch = binding.branch.text.toString()
        val semester = binding.semester.text.toString()
        val batch = binding.batch.text.toString()


        val currentDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        val checkInTime = SimpleDateFormat("hh:mm a", Locale.getDefault()).format(Date())

        for ((studentId, status) in attendanceMap) {
            val attandanceRef =
                db.collection(course).document(branch).collection(semester).document(batch)
                    .collection("studentsAttendance").document(studentId).collection("attendance")
                    .document(currentDate)


            val attendanceData = hashMapOf(
                "check_in_time" to checkInTime,
                "status" to (status ?: "Absent")
            )

            attandanceRef.set(attendanceData)
                .addOnSuccessListener {
                    Constant.hideDialog()
                    finish()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(
                        this,
                        "Error submitting attendance: ${e.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
        }
    }

    private fun getUserInfo() {
        Constant.showDialog(this, "loading students list")
        val course = binding.courses.text.toString()
        val branch = binding.branch.text.toString()
        val semester = binding.semester.text.toString()
        val batch = binding.batch.text.toString()


        val db = FirebaseFirestore.getInstance()
        val dataRef = db.collection(course)
            .document(branch)
            .collection(semester)
            .document(batch)
            .collection("studentList")

        dataRef.get().addOnSuccessListener { snapshot ->
            students.clear()
            val count = snapshot.size().toString()
            binding.totalStudent.text = count

            for (document in snapshot.documents) {
                val id = document.getString("id") ?: ""
                val name = document.getString("name") ?: ""
                val student = Student(id = id, name = name)

                students.add(student)
            }

            Constant.hideDialog()
            binding.submit.visibility = View.VISIBLE
            binding.linearLayout.visibility = View.GONE
            binding.studentListLayout.visibility = View.VISIBLE
            attendanceAdapter.notifyDataSetChanged()

        }.addOnFailureListener { e ->
            Constant.hideDialog()
            Log.e("FirestoreError", "Error fetching students: ${e.message}")
        }
    }

    private fun setUpAutoText() {
        val course = ArrayAdapter(this, R.layout.show_list, Constant.course)
        val semester = ArrayAdapter(this, R.layout.show_list, Constant.semester)
        val branch = ArrayAdapter(this, R.layout.show_list, Constant.branch)
        val batch = ArrayAdapter(this, R.layout.show_list, Constant.batch)

        binding.courses.setAdapter(course)
        binding.semester.setAdapter(semester)
        binding.branch.setAdapter(branch)
        binding.batch.setAdapter(batch)


        //// for the subject

        // Listeners to detect when all fields are selected
        binding.courses.setOnItemClickListener { _, _, _, _ -> checkAndSetSubjects() }
        binding.semester.setOnItemClickListener { _, _, _, _ -> checkAndSetSubjects() }
        binding.branch.setOnItemClickListener { _, _, _, _ -> checkAndSetSubjects() }
        binding.batch.setOnItemClickListener { _, _, _, _ -> checkAndSetSubjects() }

    }

    private fun checkAndSetSubjects() {
        val selectedCourse = binding.courses.text.toString()
        val selectedSemester = binding.semester.text.toString()
        val selectedBranch = binding.branch.text.toString()
        val selectedBatch = binding.batch.text.toString()

        // Ensure all fields are selected
        if (selectedCourse.isNotEmpty() && selectedSemester.isNotEmpty() &&
            selectedBranch.isNotEmpty() && selectedBatch.isNotEmpty()) {

            val course= selectedCourse.replace(" ", "").lowercase()
             val branch= selectedSemester.replace(" ", "").lowercase()
             val semester =  selectedBranch.replace(" ", "").lowercase()
              val batch=  selectedBatch.replace(" ", "").lowercase()

                val sub= course + branch + semester + batch



            setSubjects(sub)
        }
    }



    private fun setSubjects(selectedSemester: String) {
        val subjectList = when (selectedSemester) {
            "diplomcomputersem1b1" -> diplomcomputersem1b1
            "diplomcomputersem2b1" -> diplomcomputersem2b1
            "diplomcomputersem3b1" -> diplomcomputersem3b1
            "diplomcomputersem4b1" -> diplomcomputersem4b1
            "diplomcomputersem5b1" -> diplomcomputersem5b1
            "diplomcomputersem6b1" -> diplomcomputersem6b1
            else -> emptyArray()
        }

        // Set subjects in AutoCompleteTextView
        val subjectAdapter = ArrayAdapter(this,R.layout.show_list, subjectList)

        binding.selectSubject.setOnClickListener {

            binding.selectSubject.setAdapter(subjectAdapter)
        }

    }


}
