package org.tensorflow.lite.examples.poseestimation.exercise

import android.content.Context
import org.tensorflow.lite.examples.poseestimation.core.Point
import org.tensorflow.lite.examples.poseestimation.core.Utilities
import org.tensorflow.lite.examples.poseestimation.domain.model.Person
import org.tensorflow.lite.examples.poseestimation.domain.model.Phase

class Quadruped(
    context: Context
) : IExercise(
    context = context,
    id = 345
) {
    private var hipAngleMin = 80f
    private var hipAngleMax = 120f
    private var shoulderAngleMin = 80f
    private var shoulderAngleMax = 120f

    private var rightStateIndex = 0
    private var startTime = System.currentTimeMillis()
    private var isInRightState = false

    override var wrongStateIndex = 0
    private var wrongFrameCount = 0
    private val maxWrongCountFrame = 3

    override fun rightExerciseCount(
        person: Person,
        canvasHeight: Int,
        canvasWidth: Int,
        phases: List<Phase>
    ) {
        val leftElbowPoint = Point(
            person.keyPoints[7].coordinate.x,
            -person.keyPoints[7].coordinate.y
        )
        val leftShoulderPoint = Point(
            person.keyPoints[5].coordinate.x,
            -person.keyPoints[5].coordinate.y
        )
        val leftHipPoint = Point(
            person.keyPoints[11].coordinate.x,
            -person.keyPoints[11].coordinate.y
        )
        val leftKneePoint = Point(
            person.keyPoints[13].coordinate.x,
            -person.keyPoints[13].coordinate.y
        )
        if (phases.isNotEmpty()) {
            hipAngleMin = phases[0].constraints[0].minValue.toFloat()
            hipAngleMax = phases[0].constraints[0].maxValue.toFloat()
            shoulderAngleMin = phases[0].constraints[0].minValue.toFloat()
            shoulderAngleMax = phases[0].constraints[0].maxValue.toFloat()
        }

        val insideBox = isInsideBox(person, canvasHeight, canvasWidth)
        val shoulderAngle = Utilities.angle(leftElbowPoint, leftShoulderPoint, leftHipPoint, true)
        val hipAngle = Utilities.angle(leftShoulderPoint, leftHipPoint, leftKneePoint, true)

        val rightCountStates: Array<FloatArray> = arrayOf(
            floatArrayOf(
                hipAngleMin,
                hipAngleMax,
                shoulderAngleMin,
                shoulderAngleMax
            )
        )
        if (hipAngle > rightCountStates[rightStateIndex][0] && hipAngle < rightCountStates[rightStateIndex][1]
            && shoulderAngle > rightCountStates[rightStateIndex][2] && shoulderAngle < rightCountStates[rightStateIndex][3]
            && insideBox
        ) {
            rightStateIndex += 1
            if (rightStateIndex == rightCountStates.size - 1) {
                wrongStateIndex = 0
            }
            if (rightStateIndex == rightCountStates.size) {
                rightStateIndex = rightCountStates.size - 1
                if (!isInRightState) {
                    isInRightState = true
                    startTime = System.currentTimeMillis()
                } else {
                    if ((System.currentTimeMillis() - startTime) > 3000) {
                        rightStateIndex = 0
                        repetitionCount()
                        startTime = System.currentTimeMillis()
                    }
                }
            }
        } else {
            isInRightState = false
            if (!insideBox) {
                standInside()
            } else if (wrongFrameCount >= maxWrongCountFrame) {
                wrongFrameCount = 0
            }
        }
    }

    override fun wrongExerciseCount(person: Person, canvasHeight: Int, canvasWidth: Int) {

    }
}