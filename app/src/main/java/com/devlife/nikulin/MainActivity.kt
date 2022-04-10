package com.devlife.nikulin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.devlife.nikulin.databinding.ActivityMainBinding
import com.devlife.nikulin.domain.entities.MemesTypes
import com.devlife.nikulin.presentation.adapters.MemesViewPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val memesTabs = MemesTypes.getTabsList()
        val memesViewPagerAdapter = MemesViewPagerAdapter(this, memesTabs)
        binding.vpMemes.adapter = memesViewPagerAdapter

        TabLayoutMediator(binding.tlTabs, binding.vpMemes) { tab, position ->
            tab.text = memesTabs[position].titleName
        }.attach()

    }
}