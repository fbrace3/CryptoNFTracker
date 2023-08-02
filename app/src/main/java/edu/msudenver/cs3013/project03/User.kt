package edu.msudenver.cs3013.project03

import android.graphics.Bitmap
import java.io.Serializable

data class User(
    var username: String = "admin",
    var password: String = "admin",
    var firstName: String? = null,
    var lastName: String? = null,
    var emailAddress: String? = null,
    var profileImage: String? = null,
    var verified:Boolean = false

) : Serializable
