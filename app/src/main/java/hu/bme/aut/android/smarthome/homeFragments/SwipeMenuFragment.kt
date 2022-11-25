package hu.bme.aut.android.smarthome.homeFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.navigation.NavigationBarView
import hu.bme.aut.android.smarthome.R


import hu.bme.aut.android.smarthome.databinding.FragmentSwipeMenuBinding

class SwipeMenuFragment : Fragment() {

    private lateinit var binding : FragmentSwipeMenuBinding
    private lateinit var viewPager : ViewPager2
    private val mOnNavigationItemSelectedListener = NavigationBarView.OnItemSelectedListener{ item ->
        when (item.itemId) {
            R.id.homeMenuFragment -> {
                viewPager.currentItem = 0
                return@OnItemSelectedListener true
            }
            R.id.presetsMenuFragment -> {
                viewPager.currentItem = 1
                return@OnItemSelectedListener true
            }
            R.id.settingsMenuFragment -> {
                viewPager.currentItem = 2
                return@OnItemSelectedListener true
            }
        }
        false
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSwipeMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vpMenu.adapter = SwipeMenuPagerAdapter(this)

        viewPager = binding.vpMenu
        val bottomNavigationView=binding.bottomNavigation
        viewPager.adapter = SwipeMenuPagerAdapter(this)
        bottomNavigationView.setOnItemSelectedListener(mOnNavigationItemSelectedListener)
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                when (position) {
                    0 -> bottomNavigationView.menu.findItem(R.id.homeMenuFragment).isChecked = true
                    1 -> bottomNavigationView.menu.findItem(R.id.presetsMenuFragment).isChecked = true
                    2 -> bottomNavigationView.menu.findItem(R.id.settingsMenuFragment).isChecked = true
                }
            }
        })

    }

}