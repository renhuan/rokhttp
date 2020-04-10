package com.example.administrator.myokhttp

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class UserData(
        var name: String?,
        var gender: String?,
        var age: Int) : Parcelable {

    override fun toString(): String {
        return "UserData(name='$name', gender='$gender', age=$age)"
    }

}
