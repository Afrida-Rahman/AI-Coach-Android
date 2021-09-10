package org.tensorflow.lite.examples.poseestimation.exercise

import android.content.Context
import org.tensorflow.lite.examples.poseestimation.R
import org.tensorflow.lite.examples.poseestimation.core.AudioPlayer
import org.tensorflow.lite.examples.poseestimation.domain.model.Person
import org.tensorflow.lite.examples.poseestimation.domain.model.Phase
import org.tensorflow.lite.examples.poseestimation.domain.model.Rule

abstract class IExercise(
    context: Context,
    val id: Int,
    val name: String,
    val description: String,
    val imageResourceId: Int,
    val maxRepCount: Int = 5,
    val maxSetCount: Int = 3
) {
    companion object {
        const val deltaValue = 15f
    }

    private val audioPlayer = AudioPlayer(context)
    private var repetitionCounter = 0
    private var wrongCounter = 0
    private var lastTimePlayed: Int = System.currentTimeMillis().toInt()
    private val wrongAudioInstruction = R.raw.keep_hand_straight

    abstract fun exerciseCount(person: Person, phases: List<Phase>)
    abstract fun wrongExerciseCount(person: Person)
    abstract fun drawingRules(person: Person, phases: List<Phase>): List<Rule>
    abstract fun getBorderColor(person: Person, canvasHeight: Int, canvasWidth: Int): Int

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
    }

    fun wrongCount() {
        wrongCounter++
    }

    fun handNotStraight() {
        val timestamp = System.currentTimeMillis().toInt()
        if (timestamp - lastTimePlayed >= 3500) {
            lastTimePlayed = timestamp
            audioPlayer.play(wrongAudioInstruction)
        }
    }

    fun getRepetitionCount() = repetitionCounter

    fun getWrongCount() = wrongCounter

}