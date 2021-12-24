package org.tensorflow.lite.examples.poseestimation.exercise

import android.content.Context
import android.util.Log
import org.tensorflow.lite.examples.poseestimation.R
import org.tensorflow.lite.examples.poseestimation.core.AudioPlayer
import org.tensorflow.lite.examples.poseestimation.core.Utilities
import org.tensorflow.lite.examples.poseestimation.domain.model.Constraint
import org.tensorflow.lite.examples.poseestimation.domain.model.ConstraintType
import org.tensorflow.lite.examples.poseestimation.domain.model.Person
import org.tensorflow.lite.examples.poseestimation.domain.model.Phase

class CommonExercise(
    context: Context,
    private var phases: List<Phase>,
    private val windowWidth: Int,
    private val windowHeight: Int,
    private var maxSetCount: Int = 0,
    private var maxRepCount: Int = 0,
) {
    private var phaseIndex = 0
    private val audioPlayer = AudioPlayer(context)
    private var setCounter = 0
    private var repetitionCounter = 0
    private var lastTimePlayed: Int = System.currentTimeMillis().toInt()

    init {
        val phaseIndices = mutableListOf<Int>()
        phases = phases.sortedBy { it.phaseNumber }.filter {
            val shouldAdd = !phaseIndices.contains(it.phaseNumber)
            phaseIndices.add(it.phaseNumber)
            shouldAdd
        }
    }

    fun rightExerciseCount(
        person: Person
    ) {
        if (phases.isNotEmpty() && phaseIndex < phases.size) {
            var isConstraintsSatisfied = true

            phases[phaseIndex].constraints.forEach {
                when (it.type) {
                    ConstraintType.ANGLE -> {
                        val angle = Utilities.angle(
                            startPoint = person.keyPoints[it.startPointIndex].toRealPoint(),
                            middlePoint = person.keyPoints[it.middlePointIndex].toRealPoint(),
                            endPoint = person.keyPoints[it.endPointIndex].toRealPoint(),
                            clockWise = it.clockWise
                        )
                        if (angle < it.minValue || angle > it.maxValue) isConstraintsSatisfied =
                            false
                    }
                    ConstraintType.LINE -> {}
                }
            }

            if (isInsideBox(person, windowHeight, windowWidth) && isConstraintsSatisfied) {
                phaseIndex++
                if (phaseIndex == phases.size - 1) {
                    phaseIndex = 0
                    repetitionCount()
                }
            }
        }
    }

    fun wrongExerciseCount(
        person: Person
    ) {
        TODO("Not yet implemented")
    }

    fun drawingRules(person: Person): List<Constraint> = phases[phaseIndex].constraints

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
}