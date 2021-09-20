package com.example.nikulin.presentation.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.nikulin.domain.entities.MemesTypes
import com.example.nikulin.presentation.fragments.HotMemesFragment
import com.example.nikulin.presentation.fragments.LatestMemesFragment
import com.example.nikulin.presentation.fragments.TopMemesFragment

class MemesViewPagerAdapter(activity: FragmentActivity, val tabsList: List<MemesTypes>) : FragmentStateAdapter(activity) {

    override fun getItemCount() = tabsList.size

    override fun createFragment(position: Int): Fragment {
        return when (tabsList[position]) {
            MemesTypes.LATEST -> LatestMemesFragment.newInstance()
            MemesTypes.TOP -> TopMemesFragment.newInstance()
            MemesTypes.HOT -> HotMemesFragment.newInstance()
        }
    }
}



