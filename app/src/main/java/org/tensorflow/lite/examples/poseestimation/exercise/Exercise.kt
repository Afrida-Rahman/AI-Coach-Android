package org.tensorflow.lite.examples.poseestimation.exercise

import org.tensorflow.lite.examples.poseestimation.R
import org.tensorflow.lite.examples.poseestimation.core.AudioPlayer
import org.tensorflow.lite.examples.poseestimation.data.Person
import org.tensorflow.lite.examples.poseestimation.data.Rule

abstract class Exercise(
    val name: String,
    val description: String,
    val image: Int,
    private val audioPlayer: AudioPlayer,
    private var repetitionCounter: Int = 0,
    private val maxRepetitionCounter: Int = 10
) {
    companion object {
        const val deltaValue = 15f
    }

    private var setCounter = 0
    private var lastTimePlayed: Int = System.currentTimeMillis().toInt()
    private val wrongAudioInstruction = R.raw.keep_hand_straight

    abstract fun exerciseCount(person: Person)
    abstract fun drawingRules(person: Person): List<Rule>

    fun repetitionCount() {
        repetitionCounter++
        val resourceId = when (repetitionCounter) {
            1 -> R.raw.one
            2 -> R.raw.two
            3 -> R.raw.three
            4 -> R.raw.four
            5 -> R.raw.five
            6 -> R.raw.six
            7 -> R.raw.seven
            8 -> R.raw.eight
            9 -> R.raw.nine
            10 -> R.raw.ten
            else -> R.raw.hello
        }
        audioPlayer.play(resourceId)
        if (repetitionCounter >= maxRepetitionCounter) {
            setCounter++
            repetitionCounter = 0
        }
    }

    fun handNotStraight() {
        val timestamp = System.currentTimeMillis().toInt()
        if (timestamp - lastTimePlayed >= 3500) {
            lastTimePlayed = timestamp
            audioPlayer.play(wrongAudioInstruction)
        }
    }

    fun getRepetitionCount() = repetitionCounter

    fun getSetCount() = setCounter

}