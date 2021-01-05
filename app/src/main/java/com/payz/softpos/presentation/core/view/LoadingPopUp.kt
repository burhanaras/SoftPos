package com.payz.softpos.presentation.core.view

import android.app.AlertDialog
import android.content.Context
import android.net.Uri
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.VideoView
import com.payz.softpos.R
import com.payz.softpos.presentation.core.extension.animateGone
import com.payz.softpos.presentation.core.extension.animateVisible


class LoadingPopUp(private val loadingPopUpType: LoadingPopUpType = LoadingPopUpType.SALE) {

    companion object {
        private var isShown = false
        private var alertDialog: AlertDialog? = null
        private var handler = Handler()
        private var runnable = Runnable {
            alertDialog?.show()
            isShown = true
        }
    }


    private lateinit var ivProgress: ImageView
    private lateinit var tvExplanation: TextView
    private lateinit var tvAmount: TextView
    private lateinit var videoView: VideoView

    fun show(context: Context, amount: String) {
        if (isShown) return
        // get view
        val li = LayoutInflater.from(context)
        val promptsView: View = li.inflate(R.layout.popup_loading, null)
        ivProgress = promptsView.findViewById(R.id.ivResultLoadingPopUp)
        tvExplanation = promptsView.findViewById(R.id.tvLoadingPopUpExplanation)
        if (loadingPopUpType == LoadingPopUpType.REFUND) {
            tvExplanation.text = "Wait while POS terminal refunds amount."
        }
        tvAmount = promptsView.findViewById(R.id.tvLoadingPopUpAmount)
        tvAmount.text = amount
        videoView = promptsView.findViewById(R.id.videoViewLoadingPopUp)
        val uri: Uri =
            Uri.parse("android.resource://" + context.packageName.toString() + "/" + R.raw.pos_video_0)
        videoView.setVideoURI(uri)
        videoView.setOnPreparedListener {
            it.isLooping = true
            videoView.start()
        }
        videoView.animateVisible()

        val alertDialogBuilder = AlertDialog.Builder(context)

        // set prompts.xml to alert dialog builder
        alertDialogBuilder.setView(promptsView)
        // set dialog message
        alertDialogBuilder.setCancelable(false)
        // create alert dialog
        alertDialog = alertDialogBuilder.create()
        // show it
        handler.postDelayed(runnable, 10)
    }

    fun dismiss() {
        handler.removeCallbacks(runnable)
        alertDialog?.dismiss()
        isShown = false
    }

    fun update(state: String?) {
        tvExplanation.text = state
        tvAmount.animateGone()
        videoView.animateGone()
        ivProgress.animateVisible()
        handler.postDelayed({ dismiss() }, 4000)
    }

}

enum class LoadingPopUpType {
    SALE, REFUND
}