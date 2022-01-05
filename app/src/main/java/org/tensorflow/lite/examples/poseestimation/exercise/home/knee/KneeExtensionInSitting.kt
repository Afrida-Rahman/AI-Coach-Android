package org.tensorflow.lite.examples.poseestimation.exercise.home.knee

import android.content.Context
import org.tensorflow.lite.examples.poseestimation.core.Point
import org.tensorflow.lite.examples.poseestimation.core.Utilities
import org.tensorflow.lite.examples.poseestimation.domain.model.Person
import org.tensorflow.lite.examples.poseestimation.exercise.home.HomeExercise

class KneeExtensionInSitting(
    context: Context
) : HomeExercise(
    context = context,
    id = 471
) {
    private var downKneeAngleMin = 70f
    private var downKneeAngleMax = 100f
    private var upKneeAngleMin = 160f
    private var upKneeAngleMax = 190f

    private var wrongDownKneeAngleMin = 70f
    private var wrongDownKneeAngleMax = 100f
    private var wrongUpKneeAngleMin = 101f
    private var wrongUpKneeAngleMax = 159f

    private val totalStates = 2
    private var rightStateIndex = 0

    override var wrongStateIndex = 0

    override fun rightExerciseCount(
        person: Person,
        canvasHeight: Int,
        canvasWidth: Int
    ) {
        val hipPoint = Point(
            person.keyPoints[12].coordinate.x,
            -person.keyPoints[12].coordinate.y
        )
        val kneePoint = Point(
            person.keyPoints[14].coordinate.x,
            -person.keyPoints[14].coordinate.y
        )
        val anklePoint = Point(
            person.keyPoints[16].coordinate.x,
            -person.keyPoints[16].coordinate.y
        )
        if (rightCountPhases.size >= 2) {
            downKneeAngleMin = rightCountPhases[0].constraints[0].minValue.toFloat()
            downKneeAngleMax = rightCountPhases[0].constraints[0].maxValue.toFloat()
            upKneeAngleMin = rightCountPhases[1].constraints[0].minValue.toFloat()
            upKneeAngleMax = rightCountPhases[1].constraints[0].maxValue.toFloat()
        }

        val rightCountStates: Array<FloatArray> = arrayOf(
            floatArrayOf(
                downKneeAngleMin,
                downKneeAngleMax
            ),
            floatArrayOf(
                upKneeAngleMin,
                upKneeAngleMax
            )
        )

        val insideBox = isInsideBox(person, canvasHeight, canvasWidth)
        val kneeAngle = Utilities.angle(hipPoint, kneePoint, anklePoint, false)
        if (kneeAngle > rightCountStates[rightStateIndex][0] && kneeAngle < rightCountStates[rightStateIndex][1]
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
                onEvent(CommonInstructionEvent.HandIsNotStraight)
            }
        }
    }

    override fun wrongExerciseCount(person: Person, canvasHeight: Int, canvasWidth: Int) {
        val hipPoint = Point(
            person.keyPoints[12].coordinate.x,
            -person.keyPoints[12].coordinate.y
        )
        val kneePoint = Point(
            person.keyPoints[14].coordinate.x,
            -person.keyPoints[14].coordinate.y
        )
        val anklePoint = Point(
            person.keyPoints[16].coordinate.x,
            -person.keyPoints[16].coordinate.y
        )
        wrongDownKneeAngleMin = downKneeAngleMin
        wrongDownKneeAngleMax = downKneeAngleMax
        wrongUpKneeAngleMin = downKneeAngleMax + 1
        wrongUpKneeAngleMax = upKneeAngleMin - 1

        val wrongCountStates: Array<FloatArray> = arrayOf(
            floatArrayOf(
                wrongDownKneeAngleMin,
                wrongDownKneeAngleMax
            ),
            floatArrayOf(
                wrongUpKneeAngleMin,
                wrongUpKneeAngleMax
            ),
            floatArrayOf(
                wrongDownKneeAngleMin,
                wrongDownKneeAngleMax
            )
        )

        val insideBox = isInsideBox(person, canvasHeight, canvasWidth)
        val kneeAngle = Utilities.angle(hipPoint, kneePoint, anklePoint, false)
        if (kneeAngle > wrongCountStates[wrongStateIndex][0] && kneeAngle < wrongCountStates[wrongStateIndex][1]
            && insideBox
        ) {
            wrongStateIndex += 1
            if (wrongStateIndex == wrongCountStates.size) {
                wrongStateIndex = 0
                wrongCount()
            }
        }
    }
}