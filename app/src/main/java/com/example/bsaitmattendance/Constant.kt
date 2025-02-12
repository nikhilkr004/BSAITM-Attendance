package com.example.bsaitmattendance

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import com.example.bsaitmattendance.databinding.ProgressDialogBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

object Constant {
    private var dialog: AlertDialog? = null
    fun showDialog(context: Context, message: String) {
        val process = ProgressDialogBinding.inflate(LayoutInflater.from(context))
        process.text.text = message.toString()

        dialog = AlertDialog.Builder(context).setView(process.root).setCancelable(true).create()
        dialog!!.show()
    }

    fun hideDialog() {
        dialog!!.dismiss()
    }


    val ref=FirebaseDatabase.getInstance().reference

    fun database(): DatabaseReference {
        return ref.child("Notice").child("Student_Notice")
    }


    val category = arrayOf(
        "java",
        "c-language",
        "cplus",
        "html",
        "python"
    )

    val course = arrayOf(


        "diploma",
        "btech",
    )

    val semester = arrayOf(
        "sem1",
        "sem2",
        "sem3",
        "sem4",
        "sem5",
        "sem6",
        "sem7",
        "sem8"
    )

    val batch = arrayOf(
        "b1",
        "b2",
        "b3"
    )

    val branch = arrayOf(
        "computer",
        "electrical",
        "electronic",
        "civil",
        "mechanical"
    )

    val diplomcomputersem1b1 = arrayOf(
        "English and Communication Skills - I",
        "Applied Mathematics - I",
        "Applied Physics - I",
        "Fundamentals of IT",
        "Computer Workshop"
    )

    val diplomcomputersem2b1 = arrayOf(
        "Advances in IT",
        "Applied Mathematics - II",
        "Applied Physics - II",
        "Analog Electronics",
        "Engineering Graphics",
        "Multimedia Applications",
        "Environmental Studies & Disaster Management"
    )

    val diplomcomputersem3b1 = arrayOf(
        "Industrial/In-House Training - I",
        "Operating Systems",
        "Digital Electronics",
        "Programming in C",
        "Data Base Management System"
    )

    val diplomcomputersem4b1 = arrayOf(
        "English and Communication Skills - II",
        "Computer Organisation & Architecture",
        "Data Structures using C",
        "Object Oriented Programming using Java",
        "Open Elective (MOOCs/Offline)",
        "Minor Project"
    )

    val diplomcomputersem5b1 = arrayOf(
        "Industrial/In-House Training - II",
        "Web Technologies",
        "Python Programming",
        "Computer Networks",
        "Programme Elective - I",
        "Multidisciplinary Elective (MOOCs/Offline)"
    )

    val diplomcomputersem6b1 = arrayOf(
        "Application Development using Web Framework",
        "Entrepreneurship Development & Management",
        "Software Engineering",
        "Programme Elective - II",
        "Major Project/Industrial Training"
    )

}