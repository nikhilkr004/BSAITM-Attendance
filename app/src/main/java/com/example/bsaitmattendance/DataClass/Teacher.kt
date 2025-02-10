package com.example.bsaitmattendance.DataClass

import android.os.Parcel
import android.os.Parcelable

data class Teacher(
    val name:String?="",
    val email:String?="",
    val uid:String?="",
    val date:String?="",
    val image:String?="",
    val number:String?=""
):Parcelable {
    constructor(parcel: Parcel) : this(
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
        parcel.writeString(uid)
        parcel.writeString(date)
        parcel.writeString(image)
        parcel.writeString(number)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Teacher> {
        override fun createFromParcel(parcel: Parcel): Teacher {
            return Teacher(parcel)
        }

        override fun newArray(size: Int): Array<Teacher?> {
            return arrayOfNulls(size)
        }
    }
}