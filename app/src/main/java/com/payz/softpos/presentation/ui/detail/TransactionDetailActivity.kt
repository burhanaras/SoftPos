package com.payz.softpos.presentation.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.payz.softpos.R
import com.payz.softpos.presentation.ui.detail.ui.main.TransactionDetailFragment

class TransactionDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaction_detail)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, TransactionDetailFragment.newInstance())
                .commitNow()
        }
    }
}