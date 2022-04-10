package com.devlife.nikulin.presentation.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.devlife.nikulin.domain.entities.MemesTypes
import com.devlife.nikulin.presentation.fragments.HotMemesFragment
import com.devlife.nikulin.presentation.fragments.LatestMemesFragment
import com.devlife.nikulin.presentation.fragments.TopMemesFragment


class MemesViewPagerAdapter(activity: FragmentActivity, private val tabsList: List<MemesTypes>) : FragmentStateAdapter(activity) {

    override fun getItemCount() = tabsList.size

    override fun createFragment(position: Int): Fragment {
        return when (tabsList[position]) {
            MemesTypes.LATEST -> LatestMemesFragment.newInstance()
            MemesTypes.TOP -> TopMemesFragment.newInstance()
            MemesTypes.HOT -> HotMemesFragment.newInstance()
        }
    }
}



