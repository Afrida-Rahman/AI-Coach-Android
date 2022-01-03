package org.tensorflow.lite.examples.poseestimation.exercise

import android.content.Context
import org.tensorflow.lite.examples.poseestimation.core.Point
import org.tensorflow.lite.examples.poseestimation.core.Utilities
import org.tensorflow.lite.examples.poseestimation.domain.model.Person
import org.tensorflow.lite.examples.poseestimation.domain.model.Phase

class SitToStand(
    context: Context,
) : IExercise(
    context = context,
    id = 142
) {
    private var upHipAngleMin = 160f
    private var upHipAngleMax = 190f
    private var upKneeAngleMin = 160f
    private var upKneeAngleMax = 190f

    private var downHipAngleMin = 70f
    private var downHipAngleMax = 100f
    private var downKneeAngleMin = 70f
    private var downKneeAngleMax = 100f

    private var wrongUpHipAngleMin = 160f
    private var wrongUpHipAngleMax = 190f
    private var wrongUpKneeAngleMin = 160f
    private var wrongUpKneeAngleMax = 190f

    private var wrongDownHipAngleMin = 100f
    private var wrongDownHipAngleMax = 130f
    private var wrongDownKneeAngleMin = 100f
    private var wrongDownKneeAngleMax = 130f

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
        val leftAnklePoint = Point(
            person.keyPoints[15].coordinate.x,
            -person.keyPoints[15].coordinate.y
        )
        if (phases.size >= 2) {
            upHipAngleMin = phases[0].constraints[0].minValue.toFloat()
            upHipAngleMax = phases[0].constraints[0].maxValue.toFloat()
            upKneeAngleMin = phases[0].constraints[0].minValue.toFloat()
            upKneeAngleMax = phases[0].constraints[0].maxValue.toFloat()

            downHipAngleMin = phases[1].constraints[0].minValue.toFloat()
            downHipAngleMax = phases[1].constraints[0].maxValue.toFloat()
            downKneeAngleMin = phases[1].constraints[0].minValue.toFloat()
            downKneeAngleMax = phases[1].constraints[0].maxValue.toFloat()
        }

        val insideBox = isInsideBox(person, canvasHeight, canvasWidth)
        val hipAngle = Utilities.angle(leftShoulderPoint, leftHipPoint, leftKneePoint, true)
        val kneeAngle = Utilities.angle(leftHipPoint, leftKneePoint, leftAnklePoint)

        val rightCountStates: Array<FloatArray> = arrayOf(
            floatArrayOf(
                downHipAngleMin,
                downHipAngleMax,
                downKneeAngleMin,
                downKneeAngleMax
            ),
            floatArrayOf(
                upHipAngleMin,
                upHipAngleMax,
                upKneeAngleMin,
                upKneeAngleMax
            ),
            floatArrayOf(
                downHipAngleMin,
                downHipAngleMax,
                downKneeAngleMin,
                downKneeAngleMax
            )
        )
        if (hipAngle > rightCountStates[rightStateIndex][0] && hipAngle < rightCountStates[rightStateIndex][1]
            && kneeAngle > rightCountStates[rightStateIndex][2] && kneeAngle < rightCountStates[rightStateIndex][3]
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
        val shoulderPoint = Point(
            person.keyPoints[5].coordinate.x,
            -person.keyPoints[5].coordinate.y
        )
        val hipPoint = Point(
            person.keyPoints[11].coordinate.x,
            -person.keyPoints[11].coordinate.y
        )
        val kneePoint = Point(
            person.keyPoints[13].coordinate.x,
            -person.keyPoints[13].coordinate.y
        )
        val anklePoint = Point(
            person.keyPoints[15].coordinate.x,
            -person.keyPoints[15].coordinate.y
        )

        wrongUpHipAngleMin = upHipAngleMin
        wrongUpHipAngleMax = upHipAngleMax
        wrongUpKneeAngleMin = upKneeAngleMin
        wrongUpKneeAngleMax = upKneeAngleMax
        wrongDownHipAngleMin = downHipAngleMin + 30
        wrongDownHipAngleMax = downHipAngleMax + 30
        wrongDownKneeAngleMin = downKneeAngleMin + 30
        wrongDownKneeAngleMax = downKneeAngleMax + 30

        val wrongCountStates: Array<FloatArray> = arrayOf(
            floatArrayOf(
                wrongDownHipAngleMin,
                wrongDownHipAngleMax,
                wrongDownKneeAngleMin,
                wrongDownKneeAngleMax
            ),
            floatArrayOf(
                wrongUpHipAngleMin,
                wrongUpHipAngleMax,
                wrongUpKneeAngleMin,
                wrongUpKneeAngleMax
            ),
            floatArrayOf(
                wrongDownHipAngleMin,
                wrongDownHipAngleMax,
                wrongDownKneeAngleMin,
                wrongDownKneeAngleMax
            )
        )

        val insideBox = isInsideBox(person, canvasHeight, canvasWidth)
        val hipAngle = Utilities.angle(shoulderPoint, hipPoint, kneePoint, true)
        val kneeAngle = Utilities.angle(hipPoint, kneePoint, anklePoint)

        if (hipAngle > wrongCountStates[wrongStateIndex][0] && hipAngle < wrongCountStates[wrongStateIndex][1] &&
            kneeAngle > wrongCountStates[wrongStateIndex][2] && kneeAngle < wrongCountStates[wrongStateIndex][3] &&
            insideBox
        ) {
            if (insideBox) {
                wrongStateIndex += 1
                if (wrongStateIndex == wrongCountStates.size) {
                    wrongStateIndex = 0
                    wrongCount()
                }
            }
        }
    }
}