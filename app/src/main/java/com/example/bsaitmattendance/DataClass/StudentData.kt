package com.example.bsaitmattendance.DataClass

import android.os.Parcel
import android.os.Parcelable

data class StudentData(
    val name:String?="",
    val email:String?="",
    val password:String?="",
    val number:String?="",
    val course:String?="",
    val branch:String?="",
    val semester:String?="",
    val batch:String?="",
    val profileImage:String?="",
    val userId:String?="",
    val rollNo:String?=""

):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(email)
        parcel.writeString(password)
        parcel.writeString(number)
        parcel.writeString(course)
        parcel.writeString(branch)
        parcel.writeString(semester)
        parcel.writeString(batch)
        parcel.writeString(profileImage)
        parcel.writeString(userId)
        parcel.writeString(rollNo)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<StudentData> {
        override fun createFromParcel(parcel: Parcel): StudentData {
            return StudentData(parcel)
        }

        override fun newArray(size: Int): Array<StudentData?> {
            return arrayOfNulls(size)
        }
    }
}