package hu.bme.aut.android.smarthome.homeFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import hu.bme.aut.android.smarthome.R
import hu.bme.aut.android.smarthome.databinding.FragmentSwipeMenuBinding


class SwipeMenuFragment : Fragment() {

    private lateinit var binding : FragmentSwipeMenuBinding

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
        val tabLayout = binding.tabLayout
        TabLayoutMediator(tabLayout, binding.vpMenu) { tab, position ->
            val imageViewHome = getLayoutInflater().inflate(R.layout.custom_tab_image, null)
            imageViewHome.setBackgroundResource(R.drawable.ic_home_orange)

            val imageViewPresets = getLayoutInflater().inflate(R.layout.custom_tab_image, null)
            imageViewPresets.setBackgroundResource(R.drawable.ic_access_time_orange)

            val imageViewSettings = getLayoutInflater().inflate(R.layout.custom_tab_image, null)
            imageViewSettings.setBackgroundResource(R.drawable.ic_settings_orange)

            if(position == 0) tab.setCustomView(imageViewHome)
            if(position == 1) tab.setCustomView(imageViewPresets)
            if(position == 2) tab.setCustomView(imageViewSettings)
        }.attach()
    }

}