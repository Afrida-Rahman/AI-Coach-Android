package org.tensorflow.lite.examples.poseestimation.core

import android.content.Context
import android.media.MediaPlayer
import org.tensorflow.lite.examples.poseestimation.R

class AsyncAudioPlayer(context: Context) {

    companion object {
        const val LEAN_LEFT = "lean left"
        const val LEAN_RIGHT = "lean right"
        const val RETURN = "return"
        const val FINISH = "finish"
        const val CONGRATS = "congrats"
        const val TAKE_REST = "take rest"
        const val START = "start"
        const val SET_1 = "set 1"
        const val SET_2 = "set 2"
        const val SET_3 = "set 3"
        const val SET_4 = "set 4"
        const val SET_5 = "set 5"
        const val SET_6 = "set 6"
        const val SET_7 = "set 7"
        const val SET_8 = "set 8"
        const val SET_9 = "set 9"
        const val SET_10 = "set 10"
        const val SET_COMPLETED = "set completed"
    }

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
    private val leanLeft = MediaPlayer.create(context, R.raw.lean_left)
    private val leanRight = MediaPlayer.create(context, R.raw.lean_right)
    private val returnAudio = MediaPlayer.create(context, R.raw.return_audio)
    private val finishAudio = MediaPlayer.create(context, R.raw.finish_audio)
    private val congrats = MediaPlayer.create(context, R.raw.congratulate_patient)
    private val takeRest = MediaPlayer.create(context, R.raw.take_rest)
    private val startAudio = MediaPlayer.create(context, R.raw.start)
    private val firstSet = MediaPlayer.create(context, R.raw.first_set)
    private val secondSet = MediaPlayer.create(context, R.raw.second_set)
    private val thirdSet = MediaPlayer.create(context, R.raw.third_set)
    private val fourthSet = MediaPlayer.create(context, R.raw.fourth_set)
    private val fifthSet = MediaPlayer.create(context, R.raw.fifth_set)
    private val sixthSet = MediaPlayer.create(context, R.raw.sixth_set)
    private val seventhSet = MediaPlayer.create(context, R.raw.seventh_set)
    private val eighthSet = MediaPlayer.create(context, R.raw.eighth_set)
    private val ninthSet = MediaPlayer.create(context, R.raw.ninth_set)
    private val tenthSet = MediaPlayer.create(context, R.raw.tenth_set)
    private val setCompleted = MediaPlayer.create(context, R.raw.set_complete)

    fun playText(text: String) {
        when (text.lowercase()) {
            LEAN_LEFT -> leanLeft.start()
            LEAN_RIGHT -> leanRight.start()
            RETURN -> returnAudio.start()
            FINISH -> finishAudio.start()
            CONGRATS -> congrats.start()
            TAKE_REST -> takeRest.start()
            START -> startAudio.start()
            SET_1 -> firstSet.start()
            SET_2 -> secondSet.start()
            SET_3 -> thirdSet.start()
            SET_4 -> fourthSet.start()
            SET_5 -> fifthSet.start()
            SET_6 -> sixthSet.start()
            SET_7 -> seventhSet.start()
            SET_8 -> eighthSet.start()
            SET_9 -> ninthSet.start()
            SET_10 -> tenthSet.start()
            SET_COMPLETED -> setCompleted.start()
            else -> {}
        }
    }

    fun playNumber(number: Int) {
        when (number) {
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