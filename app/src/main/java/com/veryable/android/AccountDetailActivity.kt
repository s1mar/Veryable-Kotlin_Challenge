package com.veryable.android

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.veryable.android.data.AccountModel
import com.veryable.android.databinding.ActivityDetailsBinding

class AccountDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsBinding
    private lateinit var accountModel: AccountModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_details)

        initialize()
        setBackPressListeners()
        setValues()
    }

    private fun setValues(){

        //Set the right account type image
        var image : Int = R.drawable.account
        if(accountModel.account_type == "card")
        {
            image = R.drawable.card
        }
        binding.imgAccType.setImageResource(image)

        //Setting account details
        binding.labelAccName.text = accountModel.account_name
        binding.labelAccDetails.text = accountModel.desc

    }


    private fun initialize(){
        binding.header.labelTitle.text = getString(R.string.label_details)
        if (intent.hasExtra(AccountModel::class.java.simpleName)) {
            accountModel = intent.getSerializableExtra(AccountModel::class.java.simpleName) as AccountModel
        }
    }

    private fun setBackPressListeners(){
        binding.header.btnBack.setOnClickListener {
            onBackPressed()
        }
        binding.btnDone.setOnClickListener{
            onBackPressed()
        }
    }
}