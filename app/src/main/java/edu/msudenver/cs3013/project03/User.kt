package edu.msudenver.cs3013.project03

import java.io.Serializable

data class User(
    var username: String = "admin",
    var password: String = "admin",
    var firstName: String? = null,
    var lastName: String? = null,
    var emailAddress: String? = null
) : Serializable
