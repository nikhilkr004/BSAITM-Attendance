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
}