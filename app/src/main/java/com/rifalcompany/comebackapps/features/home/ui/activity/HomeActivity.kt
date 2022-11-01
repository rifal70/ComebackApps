package com.rifalcompany.comebackapps.features.home.ui.activity

import android.app.Dialog
import android.os.Bundle
import android.text.Html
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProviders
import com.rifalcompany.comebackapps.R
import com.rifalcompany.comebackapps.databinding.ActivityHomeBinding
import com.rifalcompany.comebackapps.features.home.viewmodel.HomeViewModel
import com.rifalcompany.comebackapps.utils.CommonUtils

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private var loadingDialog: Dialog? = null

    private val viewModel: HomeViewModel by lazy {
        ViewModelProviders.of(this).get(HomeViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val window: Window = this.window
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, com.airbnb.lottie.R.color.background_floating_material_dark)


        binding.btnHome.setOnClickListener {
            showLoading(getString(R.string.loading_text))
            var toInt: Int? = null
            try {
                toInt = binding.etHome.text.toString().toInt()
            }catch (e: Exception){
                hideLoading()
                Toast.makeText(this, "" + e, Toast.LENGTH_SHORT).show()
            }

            toInt?.let { it1 -> viewModel.getPokemonVM(it1) }
            viewModel.getPokemonModel().observe(this){
                hideLoading()
                if (it != null) {
                    binding.tvHome.text = Html.fromHtml(Html.fromHtml(it.keterangan).toString())
                }
            }
        }
    }

    private fun showLoading(loadingText: String) {
        loadingDialog = CommonUtils.showLoadingDialog(this, loadingText)
    }

    private fun hideLoading() {
        loadingDialog?.cancel()
    }
}