package org.tensorflow.lite.examples.poseestimation.exercise

import android.content.Context
import org.tensorflow.lite.examples.poseestimation.core.Point
import org.tensorflow.lite.examples.poseestimation.core.Utilities
import org.tensorflow.lite.examples.poseestimation.domain.model.Person
import org.tensorflow.lite.examples.poseestimation.domain.model.Phase

class SingleArmRaiseInProne(
    context: Context
) : IExercise(
    context = context,
    id = 500
) {
    private var downArmAngleMin = 190f
    private var downArmAngleMax = 220f
    private var upArmAngleMin = 160f
    private var upArmAngleMax = 180f

    private val totalStates = 3
    private var rightStateIndex = 0

    override var wrongStateIndex = 0
    private var wrongFrameCount = 0
    private val maxWrongCountFrame = 3
    override fun rightExerciseCount(
        person: Person,
        canvasHeight: Int,
        canvasWidth: Int,
        phases: List<Phase>
    ) {
        val leftWristPoint = Point(
            person.keyPoints[9].coordinate.x,
            -person.keyPoints[9].coordinate.y
        )
        val leftShoulderPoint = Point(
            person.keyPoints[5].coordinate.x,
            -person.keyPoints[5].coordinate.y
        )
        val leftHipPoint = Point(
            person.keyPoints[11].coordinate.x,
            -person.keyPoints[11].coordinate.y
        )
        if (phases.size >= 2) {
            downArmAngleMin = phases[0].constraints[0].minValue.toFloat()
            downArmAngleMax = phases[0].constraints[0].maxValue.toFloat()
            upArmAngleMin = phases[1].constraints[0].minValue.toFloat()
            upArmAngleMax = phases[1].constraints[0].maxValue.toFloat()
        }

        val insideBox = isInsideBox(person, canvasHeight, canvasWidth)
        val shoulderAngle = Utilities.angle(leftWristPoint, leftShoulderPoint, leftHipPoint, false)
        val rightCountStates: Array<FloatArray> = arrayOf(
            floatArrayOf(
                downArmAngleMin,
                downArmAngleMax
            ),
            floatArrayOf(
                upArmAngleMin,
                upArmAngleMax
            ),
            floatArrayOf(
                downArmAngleMin,
                downArmAngleMax
            )
        )
        if (shoulderAngle > rightCountStates[rightStateIndex][0] && shoulderAngle < rightCountStates[rightStateIndex][1]
            && insideBox
        ) {
            rightStateIndex += 1
            if (rightStateIndex == rightCountStates.size - 1) {
                wrongStateIndex = 0
            }
            if (rightStateIndex == totalStates) {
                rightStateIndex = 0
                repetitionCount()
            }
        } else {
            if (!insideBox) {
                onEvent(CommonInstructionEvent.OutSideOfBox)
            } else if (wrongFrameCount >= maxWrongCountFrame) {
                wrongFrameCount = 0
            }
        }
    }

    override fun wrongExerciseCount(person: Person, canvasHeight: Int, canvasWidth: Int) {

    }
}