package org.tensorflow.lite.examples.poseestimation.core

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import androidx.annotation.RawRes

class AudioPlayer(
    private val context: Context
) {
    fun playFromFile(@RawRes filepath: Int) {
        val player = MediaPlayer.create(context, filepath)
        player.start()
        player.setOnCompletionListener {
            player.release()
        }
    }

    fun playFromUrl(url: String) {
        val player = MediaPlayer()
        val uri = Uri.parse(url)
        player.setDataSource(context, uri)
        player.start()
        player.setOnCompletionListener {
            player.release()
        }
    }
}