package com.example.bsaitmattendance.DataClass

import com.google.firebase.Timestamp

data class LeaveRequest(
    var studentId: String = "",
    val studentName: String = "",
    val teacherId: String = "",
    val teacherName: String = "",
    val fromDate: String = "",
    val toDate: String = "",
    val reason: String = "",
    val title:String="",
    var time:Timestamp?=null,
    var leaveId:String="",
    val status: String = "Pending"

)
