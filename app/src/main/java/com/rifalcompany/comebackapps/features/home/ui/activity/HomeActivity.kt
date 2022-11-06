package com.rifalcompany.comebackapps.features.home.ui.activity

import android.os.Bundle
import android.util.Log
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.rifalcompany.comebackapps.R
import com.rifalcompany.comebackapps.databinding.ActivityHomeBinding
import com.rifalcompany.comebackapps.features.home.ui.fragment.HomeFragment
import com.rifalcompany.comebackapps.features.home.ui.fragment.SettingFragment
import com.rifalcompany.comebackapps.pref.Pref
import com.rifalcompany.comebackapps.pref.PrefConst
import nl.joery.animatedbottombar.AnimatedBottomBar


class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val window: Window = this.window
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(
            this,
            com.airbnb.lottie.R.color.background_floating_material_dark
        )

        val fragmentManager: FragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        val homeFragment = HomeFragment()
        fragmentTransaction.replace(R.id.fl_home, homeFragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()

        binding.bottomBar.setOnTabSelectListener(object : AnimatedBottomBar.OnTabSelectListener {
            override fun onTabSelected(
                lastIndex: Int,
                lastTab: AnimatedBottomBar.Tab?,
                newIndex: Int,
                newTab: AnimatedBottomBar.Tab
            ) {
                val fragmentManager: FragmentManager = supportFragmentManager
                val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
                val homeFragment = HomeFragment()
                val settingFragment = SettingFragment()

                Log.d("bottom_bar", "Selected index: $newIndex, title: ${newTab.title}")

                if (newIndex == 0) {
                    fragmentTransaction.replace(R.id.fl_home, homeFragment)
                } else if (newIndex == 1) {
                    fragmentTransaction.replace(R.id.fl_home, settingFragment)
                }
                fragmentTransaction.addToBackStack(null)
                fragmentTransaction.commit()
            }

            // An optional method that will be fired whenever an already selected tab has been selected again.
            override fun onTabReselected(index: Int, tab: AnimatedBottomBar.Tab) {
                Log.d("bottom_barxxx", "Reselected index: $index, title: ${tab.title}")
            }
        })
    }

    override fun onBackPressed() {
//        val fragment = supportFragmentManager.findFragmentById(R.id.fl_home)
//        if (fragment is SettingFragment){
//            //
//        }else{
//            super.onBackPressed()
//        }

    }
}