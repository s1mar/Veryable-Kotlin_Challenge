package com.veryable.android.data

import kotlinx.serialization.Serializable



/*
"id": 101,
    "account_type":"bank",
    "account_name":"WF Checking Account",
    "desc": "Wells Fargo (x2356)"
 */


@Serializable
data class AccountModel(
val id : Int,
val account_type:String,
val account_name:String,
val desc:String
):java.io.Serializable

