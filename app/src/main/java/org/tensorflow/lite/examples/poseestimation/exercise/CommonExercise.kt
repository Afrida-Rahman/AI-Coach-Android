package org.tensorflow.lite.examples.poseestimation.exercise

import android.content.Context
import android.util.Log
import org.tensorflow.lite.examples.poseestimation.R
import org.tensorflow.lite.examples.poseestimation.core.AudioPlayer
import org.tensorflow.lite.examples.poseestimation.core.Point
import org.tensorflow.lite.examples.poseestimation.core.Utilities
import org.tensorflow.lite.examples.poseestimation.domain.model.*

class CommonExercise(
    context: Context,
    private var phaseList: List<Phase>,
    private var maxRepCount: Int = 0,
    private var maxSetCount: Int = 0,
    private val windowWidth: Int,
    private val windowHeight: Int,
) {
    private var phaseIndex = 0
    private var constraintsIndex = 0
    private var rightCountIndex = 0
    private val audioPlayer = AudioPlayer(context)
    private var setCounter = 0
    private var repetitionCounter = 0
    private var lastTimePlayed: Int = System.currentTimeMillis().toInt()

    init {
        val phaseIndices = mutableListOf<Int>()
        phaseList = phaseList.sortedBy { it.phaseNum }.filter {
            val shouldAdd = !phaseIndices.contains(it.phaseNum)
            phaseIndices.add(it.phaseNum)
            shouldAdd
        }
    }

    fun rightExerciseCount(
        person: Person
    ) {
        val constraintList = phaseList[phaseIndex].constraints
        val insideBox = isInsideBox(person, windowHeight, windowWidth)
        if (phaseList.isNotEmpty()) {
            val rightCountStatus = mutableListOf<CountStatus>()
            if (phaseIndex < phaseList.size) {
                val rightCountState = mutableListOf<CountState>()
                if (constraintsIndex < phaseList[phaseIndex].constraints.size &&
                    constraintList[constraintsIndex].type == ConstraintType.ANGLE
                ) {
                    rightCountState.add(
                        CountState(
                            calculatedAngle = Utilities.angle(
                                startPoint = Point(
                                    person.keyPoints[constraintList[constraintsIndex].startPointIndex].coordinate.x,
                                    person.keyPoints[constraintList[constraintsIndex].startPointIndex].coordinate.y
                                ),
                                middlePoint = Point(
                                    person.keyPoints[constraintList[constraintsIndex].middlePointIndex].coordinate.x,
                                    person.keyPoints[constraintList[constraintsIndex].middlePointIndex].coordinate.y
                                ),
                                endPoint = Point(
                                    person.keyPoints[constraintList[constraintsIndex].endPointIndex].coordinate.x,
                                    person.keyPoints[constraintList[constraintsIndex].endPointIndex].coordinate.y
                                ),
                                clockWise = constraintList[constraintsIndex].clockWise
                            ),
                            angleMin = constraintList[constraintsIndex].minValue,
                            angleMax = constraintList[constraintsIndex].maxValue,
                            uniqueId = constraintList[constraintsIndex].uniqueId
                        )
                    )
                    constraintsIndex++
                }
                rightCountState.forEach { _ ->
                    rightCountStatus.add(
                        CountStatus(
                            isRight = (rightCountState[rightCountIndex].calculatedAngle > rightCountState[rightCountIndex].angleMin
                                    && rightCountState[rightCountIndex].calculatedAngle < rightCountState[rightCountIndex].angleMax
                                    && insideBox)
                        )
                    )
                }
                if (!(rightCountStatus.contains(CountStatus(isRight = false)))) {
                    phaseIndex++
                    if (phaseIndex == phaseList.size - 1) {
                        phaseIndex = 0
                        repetitionCount()
                    }
                } else {
                    if (!insideBox) {
                        standInside()
                    }
                }
            }
        }
    }

    fun wrongExerciseCount(
        person: Person,
        canvasHeight: Int,
        canvasWidth: Int,
        phases: List<Phase>
    ) {
        TODO("Not yet implemented")
    }

    fun drawingRules(person: Person, phases: List<Phase>): List<Rule> {
        TODO("Not yet implemented")
    }

    private fun isInsideBox(person: Person, canvasHeight: Int, canvasWidth: Int): Boolean {
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

    private fun repetitionCount() {
        repetitionCounter++
        audioPlayer.play(R.raw.right_count)
        Log.d("MaxCount", "Set count: ${this.maxSetCount} - Rep Count: ${this.maxRepCount}")
        if (repetitionCounter >= maxRepCount) {
            repetitionCounter = 0
            setCounter++
        }
    }

    fun standInside() {
        val timestamp = System.currentTimeMillis().toInt()
        if (timestamp - lastTimePlayed >= 3500) {
            lastTimePlayed = timestamp
            audioPlayer.play(R.raw.stand_inside_box)
        }
    }

    private fun getRightCountState(): List<CountState> {
        return emptyList()
    }

}