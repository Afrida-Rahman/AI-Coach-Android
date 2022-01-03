package org.tensorflow.lite.examples.poseestimation.exercise.home

import android.content.Context
import android.graphics.Color
import android.util.Log
import androidx.annotation.RawRes
import org.tensorflow.lite.examples.poseestimation.R
import org.tensorflow.lite.examples.poseestimation.core.AudioPlayer
import org.tensorflow.lite.examples.poseestimation.core.Utilities
import org.tensorflow.lite.examples.poseestimation.domain.model.Constraint
import org.tensorflow.lite.examples.poseestimation.domain.model.ConstraintType
import org.tensorflow.lite.examples.poseestimation.domain.model.Person
import org.tensorflow.lite.examples.poseestimation.domain.model.Phase
import org.tensorflow.lite.examples.poseestimation.exercise.CommonInstructions.isLeftHandStraight
import org.tensorflow.lite.examples.poseestimation.exercise.CommonInstructions.isRightHandStraight

abstract class HomeExercise(
    context: Context,
    val id: Int,
    var name: String = "",
    val active: Boolean = true,
    var protocolId: Int = 0,
    var instruction: String? = "",
    var imageUrls: List<String> = listOf(),
    var maxRepCount: Int = 0,
    var maxSetCount: Int = 0
) {
    private var phaseIndex = 0
    open var wrongStateIndex = 0
    private var stateStarted = false
    private var lastStateTimestamp = 0L
    private val audioPlayer = AudioPlayer(context)
    private var setCounter = 0
    private var wrongCounter = 0
    private var repetitionCounter = 0
    private var holdTimeLimitCounter = 0L
    private var maxHoldTime = 0
    private var lastTimePlayed: Int = System.currentTimeMillis().toInt()

    open fun rightExerciseCount(
        person: Person,
        canvasHeight: Int,
        canvasWidth: Int,
        phases: List<Phase>
    ) {
        val phaseList = sortedPhaseList(phases)
        if (phaseList.isNotEmpty() && phaseIndex < phaseList.size) {
            val phase = phaseList[phaseIndex]
            val constraintSatisfied = isConstraintSatisfied(
                person,
                phase.constraints
            )
            maxHoldTime = phase.holdTime
            Log.d(
                "IExercise",
                "$phaseIndex: -> $constraintSatisfied - $holdTimeLimitCounter / $maxHoldTime"
            )

            if (isInsideBox(person, canvasHeight, canvasWidth) && constraintSatisfied) {
//                phaseIndex++
//                if (phaseIndex == phaseList.size) {
//                    if (!isInLastState) {
//                        isInLastState = true
//                        lastStateTimestamp = System.currentTimeMillis()
//                        phaseIndex--
//                    } else {
//                        if (holdTimeLimitCounter > phase.holdTime) {
//                            phaseIndex = 0
//                            wrongStateIndex = 0
//                            repetitionCount()
//                            isInLastState = false
//                        } else {
//                            phaseIndex--
//                        }
//                    }
//                    holdTimeLimitCounter = System.currentTimeMillis() - lastStateTimestamp
//                } else {
//                    holdTimeLimitCounter = 0L
//                }
                if (!stateStarted) {
                    lastStateTimestamp = System.currentTimeMillis()
                    stateStarted = true
                }
                if (phaseIndex == phaseList.size - 1) {
                    phaseIndex = 0
                    wrongStateIndex = 0
                    repetitionCount()
                } else {
                    if (holdTimeLimitCounter > maxHoldTime * 1000) {
                        phaseIndex++
                        stateStarted = false
                    }
                    holdTimeLimitCounter = System.currentTimeMillis() - lastStateTimestamp
                }
            } else {
                stateStarted = false
                holdTimeLimitCounter = 0
            }
            if (phaseIndex == phaseList.size) {
                commonInstruction(
                    person,
                    phaseList[phaseIndex].constraints,
                    canvasHeight,
                    canvasWidth
                )
            } else {
                commonInstruction(
                    person,
                    phaseList[phaseIndex].constraints,
                    canvasHeight,
                    canvasWidth
                )
            }
        }
    }

    abstract fun wrongExerciseCount(person: Person, canvasHeight: Int, canvasWidth: Int)

    private fun isConstraintSatisfied(person: Person, constraints: List<Constraint>): Boolean {
        var constraintSatisfied = true
        constraints.forEach {
            when (it.type) {
                ConstraintType.ANGLE -> {
                    val angle = Utilities.angle(
                        startPoint = person.keyPoints[it.startPointIndex].toRealPoint(),
                        middlePoint = person.keyPoints[it.middlePointIndex].toRealPoint(),
                        endPoint = person.keyPoints[it.endPointIndex].toRealPoint(),
                        clockWise = it.clockWise
                    )
                    if (angle < it.minValue || angle > it.maxValue) {
                        constraintSatisfied = false
                    }
                }
                ConstraintType.LINE -> {}
            }
        }
        return constraintSatisfied
    }

    private fun sortedPhaseList(phases: List<Phase>): List<Phase> {
        val phaseIndices = mutableListOf<Int>()
        return phases.sortedBy { it.phaseNumber }.filter {
            val shouldAdd = !phaseIndices.contains(it.phaseNumber)
            phaseIndices.add(it.phaseNumber)
            shouldAdd
        }
    }

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

    private fun commonInstruction(
        person: Person,
        constraints: List<Constraint>,
        canvasHeight: Int,
        canvasWidth: Int
    ) {
        constraints.forEach {
            if (!isInsideBox(
                    person,
                    canvasHeight,
                    canvasWidth
                )
            ) onEvent(CommonInstructionEvent.OutSideOfBox)
            else if (!isLeftHandStraight(
                    person = person,
                    constraint = it
                )
            ) onEvent(CommonInstructionEvent.LeftHandIsNotStraight)
            else if (!isRightHandStraight(
                    person = person,
                    constraint = it
                )
            ) onEvent(CommonInstructionEvent.RightHandIsNotStraight)
        }
    }


    open fun onEvent(event: CommonInstructionEvent) {
        when (event) {
            is CommonInstructionEvent.OutSideOfBox -> playAudio(R.raw.stand_inside_box)
            is CommonInstructionEvent.HandIsNotStraight -> playAudio(R.raw.keep_hand_straight)
            is CommonInstructionEvent.LeftHandIsNotStraight -> playAudio(R.raw.left_hand_straight)
            is CommonInstructionEvent.RightHandIsNotStraight -> playAudio(R.raw.right_hand_straight)
        }
    }

    private fun playAudio(@RawRes resource: Int) {
        val timestamp = System.currentTimeMillis().toInt()
        if (timestamp - lastTimePlayed >= 3500) {
            lastTimePlayed = timestamp
            audioPlayer.play(resource)
        }
    }

    fun setExercise(
        exerciseName: String,
        exerciseInstruction: String?,
        exerciseImageUrls: List<String>,
        repetitionLimit: Int,
        setLimit: Int,
        protoId: Int
    ) {
        name = exerciseName
        maxRepCount = repetitionLimit
        maxSetCount = setLimit
        protocolId = protoId
        instruction = exerciseInstruction
        imageUrls = exerciseImageUrls
    }

    fun getRepetitionCount() = repetitionCounter

    fun getWrongCount() = wrongCounter

    fun getSetCount() = setCounter

    fun getHoldTimeLimitCount(): Int = (holdTimeLimitCounter / 1000).toInt()

    fun getMaxHoldTime(): Int = maxHoldTime

    sealed class CommonInstructionEvent {
        object OutSideOfBox : CommonInstructionEvent()
        object HandIsNotStraight : CommonInstructionEvent()
        object LeftHandIsNotStraight : CommonInstructionEvent()
        object RightHandIsNotStraight : CommonInstructionEvent()
    }
}