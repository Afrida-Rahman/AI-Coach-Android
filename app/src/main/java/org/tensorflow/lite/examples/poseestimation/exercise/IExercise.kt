package org.tensorflow.lite.examples.poseestimation.exercise

import android.content.Context
import android.graphics.Color
import android.util.Log
import org.tensorflow.lite.examples.poseestimation.R
import org.tensorflow.lite.examples.poseestimation.core.AudioPlayer
import org.tensorflow.lite.examples.poseestimation.domain.model.Person
import org.tensorflow.lite.examples.poseestimation.domain.model.Phase
import org.tensorflow.lite.examples.poseestimation.domain.model.Rule

abstract class IExercise(
    context: Context,
    val id: Int,
    val active: Boolean = true,
    var name: String = "",
    var protocolId: Int = 0,
    var description: String = "",
    var instruction: String? = "",
    var imageUrls: List<String> = listOf(),
    var maxRepCount: Int = 0,
    var maxSetCount: Int = 0
) {
    private val audioPlayer = AudioPlayer(context)
    private var setCounter = 0
    private var wrongCounter = 0
    private var repetitionCounter = 0
    private var lastTimePlayed: Int = System.currentTimeMillis().toInt()

    abstract fun exerciseCount(
        person: Person,
        canvasHeight: Int,
        canvasWidth: Int,
        phases: List<Phase>
    )

    abstract fun wrongExerciseCount(person: Person, canvasHeight: Int, canvasWidth: Int)
    abstract fun drawingRules(person: Person, phases: List<Phase>): List<Rule>

    open fun getBorderColor(person: Person, canvasHeight: Int, canvasWidth: Int): Int {
        return if (isInsideBox(person, canvasHeight, canvasWidth)) {
            Color.GREEN
        } else {
            Color.RED
        }
    }

    open fun isInsideBox(person: Person, canvasHeight: Int, canvasWidth: Int): Boolean {
        val left = canvasWidth * 2f / 20f
        val right = canvasWidth * 18.5f / 20f
        val top = canvasHeight * 2.5f / 20f
        val bottom = canvasHeight * 18.5f / 20f
        var rightPosition = true
        person.keyPoints.forEach {
            val x = it.coordinate.x
            val y = it.coordinate.y
            if (x < left || x > right || y < top || y > bottom) {
                rightPosition = false
            }
        }
        return rightPosition
    }

    fun repetitionCount() {
        repetitionCounter++
        audioPlayer.play(R.raw.right_count)
        Log.d("MaxCount", "Set count: ${this.maxSetCount} - Rep Count: ${this.maxRepCount}")
        if (repetitionCounter >= maxRepCount) {
            repetitionCounter = 0
            setCounter++
        }
    }

    fun wrongCount() {
        wrongCounter++
        audioPlayer.play(R.raw.wrong_count)
    }

    fun standInside() {
        val timestamp = System.currentTimeMillis().toInt()
        if (timestamp - lastTimePlayed >= 3500) {
            lastTimePlayed = timestamp
            audioPlayer.play(R.raw.stand_inside_box)
        }
    }

    fun handNotStraight() {
        val timestamp = System.currentTimeMillis().toInt()
        if (timestamp - lastTimePlayed >= 3500) {
            lastTimePlayed = timestamp
            audioPlayer.play(R.raw.keep_hand_straight)
        }
    }

    fun rightHandNotStraight() {
        val timestamp = System.currentTimeMillis().toInt()
        if (timestamp - lastTimePlayed >= 3500) {
            lastTimePlayed = timestamp
            audioPlayer.play(R.raw.right_hand_straight)
        }
    }

    fun leftHandNotStraight() {
        val timestamp = System.currentTimeMillis().toInt()
        if (timestamp - lastTimePlayed >= 3500) {
            lastTimePlayed = timestamp
            audioPlayer.play(R.raw.left_hand_straight)
        }
    }

    fun setExercise(
        exerciseName: String,
        exerciseDescription: String,
        exerciseInstruction: String?,
        exerciseImageUrls: List<String>,
        repetitionLimit: Int,
        setLimit: Int,
        protoId: Int
    ) {
        name = exerciseName
        description = exerciseDescription
        maxRepCount = repetitionLimit
        maxSetCount = setLimit
        protocolId = protoId
        instruction = exerciseInstruction
        imageUrls = exerciseImageUrls
    }

    fun getRepetitionCount() = repetitionCounter

    fun getWrongCount() = wrongCounter

    fun getSetCount() = setCounter
}