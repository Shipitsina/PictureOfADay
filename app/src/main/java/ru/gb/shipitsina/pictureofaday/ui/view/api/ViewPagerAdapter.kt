package ru.gb.shipitsina.pictureofaday.ui.view.api

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter

private const val EARTH_FRAGMENT = 0
private const val MARS_FRAGMENT = 1
private const val SYSTEM_FRAGMENT = 2

 class ViewPagerAdapter(fa: FragmentActivity): FragmentStateAdapter(fa) {

     private val fragments = arrayOf(EarthFragment(),MarsFragment(),SystemFragment())

     override fun getItemCount() = fragments.size

     override fun createFragment(position: Int)= fragments[position]

 }