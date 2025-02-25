package com.example.bsaitmattendance.DataClass

import com.google.firebase.Timestamp

data class AttendanceSession(
    val session:String?="",
    val teacherUid:String?="",
    val teacherName: String?="",
    val course: String?="",
    val branch: String?="",
    val semester: String?="",
    val batch: String?="",
    val subject: String?="",
    val date: String?="",
    val time: Timestamp?=null,
    val presentCount: Int=0,
    val totalStudents: Int=0,
    val presentStudents: List<String>
)
