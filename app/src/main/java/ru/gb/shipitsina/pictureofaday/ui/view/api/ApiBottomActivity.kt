package ru.gb.shipitsina.pictureofaday.ui.view.api

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import ru.gb.shipitsina.pictureofaday.R
import ru.gb.shipitsina.pictureofaday.databinding.ActivityApiBinding
import ru.gb.shipitsina.pictureofaday.databinding.ActivityBottomApiBinding

class ApiBottomActivity:AppCompatActivity() {
    lateinit var binding: ActivityBottomApiBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBottomApiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.bottom_view_earth -> {
                    true
                }
                R.id.bottom_view_mars -> {
                    true
                }
                R.id.bottom_view_system -> {
                    true
                }
                else -> {
                    false
                }
            }
        }
    }


}