package dev.acuon.sessions.utils

import android.content.res.ColorStateList
import dev.acuon.sessions.R

object Constants {
    const val FRAGMENT_LOGIN_TAG = "login"
    const val FRAGMENT_SIGN_UP_TAG = "signup"
    const val SHARED_PREFERENCE_KEY = "currentLoggedInUser"
    const val WRONG_PASSWORD = "Wrong Password"
    const val USER_NOT_REGISTERED = "User not Registered"
    const val PLEASE_ENTER_USERNAME = "Please enter Username"
    const val USER_ALREADY_EXISTS = "User already exists"
    const val PASSWORDS_DO_NOT_MATCH = "Passwords do not match"
    const val SIGN_UP_SUCCESSFUL = "Sign Up Successful"
    const val LOGIN_SUCCESSFUL = "Login Successful"
    const val PLEASE_ENTER_PASSWORD = "Please enter password"

    const val NAME = "name"
    const val PASSWORD = "password"
    const val THE_USERNAME_IS = "The Username is: "
    const val THE_PASSWORD_IS = "The Password is: "

    private val STATES = arrayOf(intArrayOf(android.R.attr.state_checked), intArrayOf())
    private val COLORS = intArrayOf(R.color.black, R.color.white)
    val COLOR_STATE_LIST = ColorStateList(STATES, COLORS)
}