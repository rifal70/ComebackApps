package com.rifalcompany.comebackapps.features.home.ui.fragment

import ShowFragment
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.rifalcompany.comebackapps.databinding.FragmentSettingBinding
import com.rifalcompany.comebackapps.features.show.ui.activity.ShowActivity
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class SettingFragment : Fragment() {
    private lateinit var binding: FragmentSettingBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingBinding.inflate(inflater, container, false)

        binding.ivFirstMenu.setOnClickListener {
            val dialogFragment = ShowFragment()
            fragmentManager?.let { it1 -> dialogFragment.show(it1,"") }
        }

        binding.rvBaruNih.setOnClickListener {
            val i = Intent(requireContext(), ShowActivity::class.java)
            startActivity(i)
        }

        val url = "https://sun9-74.userapi.com/impf/c855132/v855132795/106fdc/6ZgWNglmQq0.jpg?size=604x404&quality=96&sign=ec2c1f0307ed685693e5aa58826bcc55&type=album"
        Picasso.get().isLoggingEnabled = true;
        Picasso.get()
            .load(url)
            .into(binding.ivHidden, object : Callback {
                override fun onSuccess() {
                    Log.d(TAG, "success")
                    binding.ribbonView.ribbonDrawable = binding.ivHidden.drawable
                }
                override fun onError(e: Exception?) {
                    Log.d(TAG, ""+e)
                }
            })

        return binding.root
    }
}
