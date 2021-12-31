package org.tensorflow.lite.examples.poseestimation.exercise

import android.content.Context
import android.graphics.Color
import android.util.Log
import org.tensorflow.lite.examples.poseestimation.R
import org.tensorflow.lite.examples.poseestimation.core.AudioPlayer
import org.tensorflow.lite.examples.poseestimation.core.Utilities
import org.tensorflow.lite.examples.poseestimation.domain.model.Constraint
import org.tensorflow.lite.examples.poseestimation.domain.model.ConstraintType
import org.tensorflow.lite.examples.poseestimation.domain.model.Person
import org.tensorflow.lite.examples.poseestimation.domain.model.Phase

abstract class IExercise(
    context: Context,
    val id: Int,
    var name: String = "",
    val active: Boolean = true,
    var protocolId: Int = 0,
    var instruction: String? = "",
    var imageUrls: List<String> = listOf(),
    var maxRepCount: Int = 0,
    var maxSetCount: Int = 0,
    var maxHoldTimeLimit: Long = 0L
) {
    private var phaseIndex = 0
    open var wrongStateIndex = 0
    private var isInLastState = false
    private var lastStateTimestamp = 0L
    private val audioPlayer = AudioPlayer(context)
    private var setCounter = 0
    private var wrongCounter = 0
    private var repetitionCounter = 0
    private var holdTimeLimitCounter = 0L
    private var lastTimePlayed: Int = System.currentTimeMillis().toInt()

    open fun rightExerciseCount(
        person: Person,
        canvasHeight: Int,
        canvasWidth: Int,
        phases: List<Phase>
    ) {
        val phaseIndices = mutableListOf<Int>()
        val phaseList = phases.sortedBy { it.phaseNumber }.filter {
            val shouldAdd = !phaseIndices.contains(it.phaseNumber)
            phaseIndices.add(it.phaseNumber)
            shouldAdd
        }
        if (phaseList.isNotEmpty() && phaseIndex < phaseList.size) {
            var isConstraintsSatisfied = true

            phaseList[phaseIndex].constraints.forEach {
                when (it.type) {
                    ConstraintType.ANGLE -> {
                        val angle = Utilities.angle(
                            startPoint = person.keyPoints[it.startPointIndex].toRealPoint(),
                            middlePoint = person.keyPoints[it.middlePointIndex].toRealPoint(),
                            endPoint = person.keyPoints[it.endPointIndex].toRealPoint(),
                            clockWise = it.clockWise
                        )
                        if (angle < it.minValue || angle > it.maxValue) {
                            isConstraintsSatisfied = false
                        }
                    }
                    ConstraintType.LINE -> {}
                }
            }
            Log.d("IExercise", "$phaseIndex: -> $isConstraintsSatisfied - $holdTimeLimitCounter ($maxHoldTimeLimit)")
            if (isInsideBox(person, canvasHeight, canvasWidth) && isConstraintsSatisfied) {
                phaseIndex++ // 3
                if (phaseIndex == phaseList.size) { // 3 == 3
                    if (!isInLastState) {
                        isInLastState = true
                        lastStateTimestamp = System.currentTimeMillis()
                        phaseIndex--
                    } else {
                        if (holdTimeLimitCounter > maxHoldTimeLimit) {
                            phaseIndex = 0
                            wrongStateIndex = 0
                            repetitionCount()
                            isInLastState = false
                        } else {
                            phaseIndex--
                        }
                    }
                    holdTimeLimitCounter = System.currentTimeMillis() - lastStateTimestamp
                } else {
                    holdTimeLimitCounter = 0L
                }
            } else {
                isInLastState = false
                holdTimeLimitCounter = 0L
            }
        }
    }

    abstract fun wrongExerciseCount(person: Person, canvasHeight: Int, canvasWidth: Int)

    fun drawingRules(phases: List<Phase>): List<Constraint> {
        return if (phases.size > phaseIndex) {
            phases[phaseIndex].constraints
        } else {
            emptyList()
        }
    }

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
        exerciseInstruction: String?,
        exerciseImageUrls: List<String>,
        repetitionLimit: Int,
        setLimit: Int,
        protoId: Int,
        holdLimit: Long
    ) {
        name = exerciseName
        maxRepCount = repetitionLimit
        maxSetCount = setLimit
        protocolId = protoId
        instruction = exerciseInstruction
        imageUrls = exerciseImageUrls
        maxHoldTimeLimit = holdLimit
    }

    fun getRepetitionCount() = repetitionCounter

    fun getWrongCount() = wrongCounter

    fun getSetCount() = setCounter

    fun getHoldTimeLimitCount() = holdTimeLimitCounter
}