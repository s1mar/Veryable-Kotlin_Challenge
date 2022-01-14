package com.veryable.android

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.veryable.android.controllers.HeaderControl
import com.veryable.android.data.AccountModel
import com.veryable.android.databinding.ActivityPayoutsListBinding
import org.json.JSONException
import java.lang.reflect.Type


class PayoutsListActivity : AppCompatActivity(),AccountAdapterCallback {

    private lateinit var binding : ActivityPayoutsListBinding

    private lateinit var headerController : HeaderControl

    private lateinit var bankAdapter: PayoutAdapter
    private lateinit var cardAdapter: PayoutAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_payouts_list)
        initHeader()
        makeNetworkCallAndGetDataSet()
    }

    private fun initHeader(){
        headerController = HeaderControl(binding.header)
        headerController.updateHeaderText(getString(R.string.label_accounts))
        headerController.enableBackButton(false)
    }

    private fun makeNetworkCallAndGetDataSet(){
        val queue = Volley.newRequestQueue(this@PayoutsListActivity)
        val jsonObjectRequest = JsonArrayRequest(Request.Method.GET,
            "https://veryable-public-assets.s3.us-east-2.amazonaws.com/veryable.json",
            null,
            { response ->
                try {
                    val listOfMyClassObject: Type = object : TypeToken<ArrayList<AccountModel?>?>() {}.type
                    val listOfAccounts = Gson().fromJson(
                        response.toString(),
                        listOfMyClassObject
                    ) as ArrayList<AccountModel?>?
                    setAdapter(listOfAccounts)
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }) {
            Log.d("Error",it.localizedMessage)
            Toast.makeText(this@PayoutsListActivity, it.localizedMessage, Toast.LENGTH_SHORT).show()
        }
        queue.add(jsonObjectRequest)
    }


    @SuppressLint("SetTextI18n", "NotifyDataSetChanged")
    fun setAdapter(list: ArrayList<AccountModel?>?){
        list?.let{
            if (!::bankAdapter.isInitialized) {
                val filterList=list.filter { it?.account_type == "bank" }
                bankAdapter = PayoutAdapter(filterList as ArrayList<AccountModel>,this@PayoutsListActivity)
                if(filterList.isNotEmpty()) {
                    binding.containerBankAccounts.labelAccType.text=getString(R.string.label_bank_accounts)
                    binding.containerBankAccounts.recyclerAccounts.layoutManager = LinearLayoutManager(this)
                    binding.containerBankAccounts.recyclerAccounts.setHasFixedSize(true)
                    binding.containerBankAccounts.recyclerAccounts.adapter = bankAdapter
                    bankAdapter.notifyDataSetChanged()
                    binding.containerBankAccounts.root.visibility= View.VISIBLE
                } else {
                    binding.containerBankAccounts.root.visibility= View.GONE
                }
            }
            if (!::cardAdapter.isInitialized) {
                val filterList=list.filter { it?.account_type == "card" }
                cardAdapter = PayoutAdapter(filterList as ArrayList<AccountModel>,this@PayoutsListActivity)
                if(filterList.isNotEmpty()) {
                    binding.containerCardAccounts.labelAccType.text = getString(R.string.txt_card_accounts)
                    binding.containerCardAccounts.recyclerAccounts.layoutManager = LinearLayoutManager(this)
                    binding.containerCardAccounts.recyclerAccounts.setHasFixedSize(true)
                    binding.containerCardAccounts.recyclerAccounts.adapter = cardAdapter
                    cardAdapter.notifyDataSetChanged()
                    binding.containerCardAccounts.root.visibility= View.VISIBLE
                } else {
                    binding.containerCardAccounts.root.visibility= View.GONE
                }
            }
            if (binding.containerCardAccounts.root.visibility == View.GONE && binding.containerBankAccounts.root.visibility == View.GONE) {
                binding.tvEmptyView.visibility = View.VISIBLE
            } else {
                binding.tvEmptyView.visibility = View.GONE
            }
        }
    }

    override fun onAccountClick(accountModel: AccountModel) {
            val intent= Intent(this@PayoutsListActivity,AccountDetailActivity::class.java)
            intent.putExtra(AccountModel::class.java.simpleName,accountModel)
            startActivity(intent)
    }
}