package com.veryable.android

import com.veryable.android.data.AccountModel

interface AccountAdapterCallback {
    fun onAccountClick(accountModel: AccountModel)
}