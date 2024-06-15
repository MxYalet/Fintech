package com.example.fintech.ui.activity


import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.fintech.R
import com.example.fintech.databinding.ActivityHomeBinding
import com.example.fintech.ui.activity.AccountActivity
import com.example.fintech.ui.activity.HomeViewModel
import com.example.fintech.ui.activity.TransferActivity
import com.example.fintech.ui.fragment.transfer.TransferFragment
import com.google.android.material.tabs.TabLayout

class HomeActivity : AppCompatActivity(), TabLayout.OnTabSelectedListener {

    private lateinit var binding: ActivityHomeBinding
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        val userName = intent.getStringExtra("USER_NAME") ?: ""
        viewModel.setUserName(userName)

        binding.bottomTab.addOnTabSelectedListener(this)

        // Set up the TransferFragment
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, TransferFragment())
            .commit()
    }

    fun getHomeViewModelCustom(): HomeViewModel {
        return viewModel
    }

    override fun onTabSelected(tab: TabLayout.Tab?) {
        when (tab?.position) {
            0 -> {
                // Home tab selected
                val intent = Intent(applicationContext, HomeActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intent)
                finish()
            }
            1 -> {
                // Transfer tab selected
                val intent = Intent(applicationContext, TransferActivity::class.java)
                startActivity(intent)
            }
            2 -> {
                // Profile tab selected
                val intent = Intent(applicationContext, AccountActivity::class.java)
                startActivity(intent)
            }
        }
    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {}

    override fun onTabReselected(tab: TabLayout.Tab?) {}
}
