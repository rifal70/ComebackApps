
import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import com.google.android.exoplayer2.util.Util
import com.rifalcompany.comebackapps.databinding.FragmentShowBinding
import com.rifalcompany.comebackapps.pref.Pref
import com.rifalcompany.comebackapps.pref.PrefConst


class ShowFragment : DialogFragment() {
    private lateinit var binding: FragmentShowBinding
    private val videoUrl =
        "https://media.geeksforgeeks.org/wp-content/uploads/20201217163353/Screenrecorder-2020-12-17-16-32-03-350.mp4"
    private var mPlayer: SimpleExoPlayer? = null

    @SuppressLint("DialogFragmentCallbacksDetector")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.setCancelable(true)
        dialog.setCanceledOnTouchOutside(false)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)

        return object : Dialog(requireActivity(), theme) {
            override fun onBackPressed() {
                // On backpress, do your stuff here.
            }

            override fun dismiss() {
                val vid = Pref.loadBoolean(PrefConst.VID_CANCEL_STATUS, false)
                if (vid){
                    super.dismiss()
                    Pref.saveBoolean(PrefConst.VID_CANCEL_STATUS, false)
                }
                dialog
            }
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentShowBinding.inflate(inflater, container, false)
        binding.ivDismiss.setOnClickListener {
            Pref.saveBoolean(PrefConst.VID_CANCEL_STATUS, true)
            dialog?.dismiss()
        }
        return binding.root
    }

    private fun initPlayer() {
        // Create a player instance.
        val mPlayer = SimpleExoPlayer.Builder(requireActivity()).build()
        // Bind the player to the view.
        binding.idExoPlayerVIew.player = mPlayer
        //setting exoplayer when it is ready.
        mPlayer!!.playWhenReady = false
        // Set the media source to be played.
        mPlayer!!.setMediaSource(buildMediaSource())
        // Prepare the player.
        mPlayer!!.prepare()

    }

    override fun onStart() {
        super.onStart()
        if (Util.SDK_INT >= 24) {
            initPlayer()
        }
    }

    override fun onResume() {
        super.onResume()
        if (Util.SDK_INT < 24 || mPlayer == null) {
            initPlayer()
        }
    }

    override fun onPause() {
        super.onPause()
        if (Util.SDK_INT < 24) {
            releasePlayer()
        }
    }

    override fun onStop() {
        super.onStop()
        if (Util.SDK_INT >= 24) {
            releasePlayer()
        }
    }


    private fun releasePlayer() {
        if (mPlayer == null) {
            return
        }
        //release player when done
        mPlayer!!.release()
        mPlayer = null
    }

    //creating mediaSource
    private fun buildMediaSource(): MediaSource {
        // Create a data source factory.
        val dataSourceFactory: DataSource.Factory = DefaultHttpDataSource.Factory()
        // Create a progressive media source pointing to a stream uri.
        return ProgressiveMediaSource.Factory(dataSourceFactory)
            .createMediaSource(MediaItem.fromUri(videoUrl))
    }
}