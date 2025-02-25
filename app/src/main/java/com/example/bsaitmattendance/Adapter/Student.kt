package com.example.bsaitmattendance.Adapter

 data class Student(
    val id: String?=null,
    val name: String?="",
   val branch:String?="",
    var status: String? = null // Status can be "Present", "Absent", or null
)
