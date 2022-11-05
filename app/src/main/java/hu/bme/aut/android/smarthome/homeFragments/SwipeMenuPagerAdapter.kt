package hu.bme.aut.android.smarthome.homeFragments

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class SwipeMenuPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = NUM_PAGES

    override fun createFragment(position: Int): Fragment = when(position){
        0 -> HomeMenuFragment()
        1 -> PresetsMenuFragment()
        2 -> SettingsMenuFragment()
        else -> HomeMenuFragment()
    }

    companion object{
        const val NUM_PAGES = 3
    }
}