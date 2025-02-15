package com.example.bsaitmattendance.DataClass

data class LeaveRequest(
    val studentId: String = "",
    val studentName: String = "",
    val teacherId: String = "",
    val teacherName: String = "",
    val fromDate: String = "",
    val toDate: String = "",
    val reason: String = "",
    val title:String="",
    val time:String="",
    val leaveId:String="",
    val status: String = "Pending"

)
