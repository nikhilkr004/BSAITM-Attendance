package com.example.bsaitmattendance.DataClass

import com.google.firebase.Timestamp

data class NoticeData(
    val title:String?="",
    val disc:String?="",
    val date:Timestamp?=null,
    val noticeId:String="",
    val userUid:String=""
)
