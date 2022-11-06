package com.rifalcompany.comebackapps.features.home.ui.fragment

import android.app.Dialog
import android.graphics.Typeface
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Html
import android.text.TextUtils
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.viewbinding.ViewBinding
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.rifalcompany.comebackapps.R
import com.rifalcompany.comebackapps.databinding.FragmentHomeBinding
import com.rifalcompany.comebackapps.features.home.viewmodel.HomeViewModel
import com.rifalcompany.comebackapps.pref.Pref
import com.rifalcompany.comebackapps.pref.PrefConst
import com.rifalcompany.comebackapps.utils.CommonUtils
import com.skydoves.balloon.Balloon
import com.skydoves.balloon.overlay.BalloonOverlayAnimation
import com.skydoves.balloon.overlay.BalloonOverlayRect
import com.skydoves.balloon.showAlignTop
import org.imaginativeworld.whynotimagecarousel.listener.CarouselListener
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem

class HomeFragment : Fragment() {
    private var loadingDialog: Dialog? = null
    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by lazy {
        ViewModelProviders.of(this).get(HomeViewModel::class.java)
    }
    private val baloonStatus = Pref.loadBoolean(PrefConst.BALLON_STATUS, false)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        binding.tvMarquee.ellipsize = TextUtils.TruncateAt.MARQUEE;
        binding.tvMarquee.text =
            "this apps is comeback application ..... this apps is comeback application ..... this apps is comeback application .....";
        binding.tvMarquee.isSelected = true
        binding.tvMarquee.isSingleLine = true

        binding.carousel.registerLifecycle(lifecycle)
        val list = mutableListOf<CarouselItem>()
        list.add(
            CarouselItem(
                imageUrl = "https://avatars.mds.yandex.net/i?id=252e5b798a4b2dbb575240c69caa8e5f8f3c0ac0-5681290-images-thumbs&n=13"
            )
        )

        list.add(
            CarouselItem(
                imageUrl = "https://2.bp.blogspot.com/-6gEf-51mo-w/UgDBXTZekYI/AAAAAAAACjg/Mznhw8KYWcA/s1600/tantra+sex+06.jpg",
                caption = "Photo by Aaron Wu on Unsplash"
            )
        )
        binding.carousel.setData(list)

        binding.btnSearch.setOnClickListener {
            showLoading(getString(R.string.loading_text))

            val dialog = BottomSheetDialog(requireContext())
            val view = layoutInflater.inflate(R.layout.bottom_sheet_dialog, null)
            val btnClose = view.findViewById<Button>(R.id.btn_dismiss)
            val tvBottomSheet = view.findViewById<TextView>(R.id.tv_bottomsheet)
            val shimmer = view.findViewById<ShimmerFrameLayout>(R.id.shimmer)

            tvBottomSheet.visibility = View.GONE
            shimmer.visibility = View.VISIBLE
            shimmer.startShimmer()

            btnClose.setOnClickListener {
                dialog.dismiss()
            }
            dialog.setCancelable(false)
            dialog.setContentView(view)
            dialog.show()

            var toInt: Int? = null
            try {
                if (binding.etHome.text.toString().toInt() <= 0) {
                    Toast.makeText(requireContext(), "invalid number", Toast.LENGTH_SHORT).show()
                    dialog.dismiss()
                }
                toInt = binding.etHome.text.toString().toInt() - 1
            } catch (e: Exception) {
                loadingDialog?.cancel()
                dialog.dismiss()
                Toast.makeText(requireContext(), "" + e, Toast.LENGTH_SHORT).show()
                Pref.saveBoolean(
                    PrefConst.BALLON_STATUS, false
                ) 
            }

            toInt?.let { it1 -> viewModel.getPokemonVM(it1) }
            viewModel.getPokemonModel().observe(viewLifecycleOwner) {
                if (it != null) {
                    tvBottomSheet.text = Html.fromHtml(Html.fromHtml(it.keterangan).toString())
                    Handler(Looper.getMainLooper()).postDelayed({
                        loadingDialog?.cancel()
                        tvBottomSheet.visibility = View.VISIBLE
                        shimmer.visibility = View.GONE
                        shimmer.stopShimmer()
                    }, 800)
                } else {
                    Handler(Looper.getMainLooper()).postDelayed({
                        loadingDialog?.cancel()
                    }, 800)
                }
            }
        }

        binding.carousel.carouselListener = object : CarouselListener {
            override fun onBindViewHolder(binding: ViewBinding, item: CarouselItem, position: Int) {
                // ...
            }

            override fun onClick(position: Int, carouselItem: CarouselItem) {
                if (position == 0) {
                    Toast.makeText(requireContext(), "ca", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onLongClick(position: Int, dataObject: CarouselItem) {
                // ...
            }
        }

        Log.d("ballonstatus", "onCreateView: $baloonStatus")

        if (!baloonStatus){
            val balloon = Balloon.Builder(requireContext())
                .setText("You can edit your profile now!")
                .setTextSize(15f)
                .setTextTypeface(Typeface.BOLD)
                .setTextGravity(Gravity.START)
                .setIsVisibleOverlay(true) // sets the visibility of the overlay for highlighting an anchor.
                .setOverlayColorResource(R.color.overlay) // background color of the overlay using a color resource.
                .setOverlayPadding(6f) // sets a padding value of the overlay shape internally.
                .setOverlayPaddingColorResource(R.color.white) // sets color of the overlay padding using a color resource.
                .setBalloonOverlayAnimation(BalloonOverlayAnimation.FADE) // default is fade.
                .setOverlayShape(BalloonOverlayRect)
                .setOnBalloonClickListener {
                    Pref.saveBoolean(
                        PrefConst.BALLON_STATUS, true
                    )}
                .setOnBalloonDismissListener {
                    Pref.saveBoolean(
                        PrefConst.BALLON_STATUS, true
                    )}
                .setOnBalloonOutsideTouchListener { _, _ ->
                    Pref.saveBoolean(
                        PrefConst.BALLON_STATUS, true
                    ) }
                .setLifecycleOwner(viewLifecycleOwner)
                .build()
            binding.etHome.showAlignTop(balloon)

        }

        return binding.root
    }

    private fun showLoading(loadingText: String){
        loadingDialog = CommonUtils.showLoadingDialog(requireContext(), loadingText)
    }
}