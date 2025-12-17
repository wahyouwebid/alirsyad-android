package com.alirsyad.app.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class LoginViewPagerAdapter(val list: List<Fragment>, activity: FragmentActivity)
    : FragmentStateAdapter(activity) {

    override fun getItemCount() = list.size

    override fun createFragment(position: Int) = list[position]
}