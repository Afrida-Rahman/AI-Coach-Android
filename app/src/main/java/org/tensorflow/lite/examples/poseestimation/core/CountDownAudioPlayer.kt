package org.tensorflow.lite.examples.poseestimation.core

import android.content.Context
import android.media.MediaPlayer
import org.tensorflow.lite.examples.poseestimation.R

class CountDownAudioPlayer(context: Context) {
    private val one = MediaPlayer.create(context, R.raw.one)
    private val two = MediaPlayer.create(context, R.raw.two)
    private val three = MediaPlayer.create(context, R.raw.three)
    private val four = MediaPlayer.create(context, R.raw.four)
    private val five = MediaPlayer.create(context, R.raw.five)
    private val six = MediaPlayer.create(context, R.raw.six)
    private val seven = MediaPlayer.create(context, R.raw.seven)
    private val eight = MediaPlayer.create(context, R.raw.eight)
    private val nine = MediaPlayer.create(context, R.raw.nine)
    private val ten = MediaPlayer.create(context, R.raw.ten)
    private val eleven = MediaPlayer.create(context, R.raw.eleven)
    private val twelve = MediaPlayer.create(context, R.raw.twelve)
    private val thirteen = MediaPlayer.create(context, R.raw.thirteen)
    private val fourteen = MediaPlayer.create(context, R.raw.fourteen)
    private val fifteen = MediaPlayer.create(context, R.raw.fifteen)
    private val sixteen = MediaPlayer.create(context, R.raw.sixteen)
    private val seventeen = MediaPlayer.create(context, R.raw.seventeen)
    private val eighteen = MediaPlayer.create(context, R.raw.eightteen)
    private val nineteen = MediaPlayer.create(context, R.raw.nineteen)
    private val twenty = MediaPlayer.create(context, R.raw.twenty)
    private val rightCount = MediaPlayer.create(context, R.raw.right_count)

    fun play(count: Int) {
        when (count) {
            1 -> one.start()
            2 -> two.start()
            3 -> three.start()
            4 -> four.start()
            5 -> five.start()
            6 -> six.start()
            7 -> seven.start()
            8 -> eight.start()
            9 -> nine.start()
            10 -> ten.start()
            11 -> eleven.start()
            12 -> twelve.start()
            13 -> thirteen.start()
            14 -> fourteen.start()
            15 -> fifteen.start()
            16 -> sixteen.start()
            17 -> seventeen.start()
            18 -> eighteen.start()
            19 -> nineteen.start()
            20 -> twenty.start()
            else -> rightCount.start()
        }
    }
}