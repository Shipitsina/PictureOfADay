package ru.gb.shipitsina.pictureofaday.ui.view.api

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

private const val EARTH_FRAGMENT = 0
private const val MARS_FRAGMENT = 1
private const val SYSTEM_FRAGMENT = 2

 class ViewPagerAdapter(private val fm: FragmentManager): FragmentStatePagerAdapter(fm) {

    private val fragments = arrayOf(EarthFragment(),MarsFragment(),SystemFragment())
   /* override fun getItemCount(): Int {
        return fragments.size
    }*/

    override fun getItem(position: Int): Fragment {
        return when(position){
            EARTH_FRAGMENT -> fragments[EARTH_FRAGMENT] as Fragment
            MARS_FRAGMENT -> fragments[MARS_FRAGMENT] as Fragment
            SYSTEM_FRAGMENT -> fragments[SYSTEM_FRAGMENT] as Fragment
            else -> fragments[EARTH_FRAGMENT] as Fragment
        }
    }

     override fun getCount(): Int {
         TODO("Not yet implemented")
     }

     override fun getPageTitle(position: Int): CharSequence? {
        return when(position){
            EARTH_FRAGMENT -> "EARTH"
            MARS_FRAGMENT -> "MARS"
            SYSTEM_FRAGMENT -> "SYSTEM"
            else -> "EARTH"
        }
    }
}