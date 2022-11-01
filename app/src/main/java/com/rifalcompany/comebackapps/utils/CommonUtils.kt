package com.rifalcompany.comebackapps.utils

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import androidx.core.graphics.drawable.toDrawable
import com.rifalcompany.comebackapps.databinding.ProgressDialogBinding

object CommonUtils {
    fun showLoadingDialog(
        context: Context,
        loadingText: String,
        cancelable: Boolean = true
    ): Dialog {
        val binding = ProgressDialogBinding.inflate(LayoutInflater.from(context))
        val progressDialog = Dialog(context)
        progressDialog.setContentView(binding.root)

        binding.tvLoadingText.text = loadingText

        progressDialog.let {
            it.show()
            it.window?.setBackgroundDrawable(Color.TRANSPARENT.toDrawable())
            it.setCancelable(cancelable)
            it.setCanceledOnTouchOutside(false)

            return it
        }
    }
}