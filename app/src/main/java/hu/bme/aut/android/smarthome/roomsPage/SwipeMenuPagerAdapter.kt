package hu.bme.aut.android.smarthome.roomsPage

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import hu.bme.aut.android.smarthome.presetsPage.fragment.PresetsMenuFragment
import hu.bme.aut.android.smarthome.roomsPage.fragment.HomeMenuFragment
import hu.bme.aut.android.smarthome.settingsPage.fragment.SettingsMenuFragment

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