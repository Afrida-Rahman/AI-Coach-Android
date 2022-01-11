package org.tensorflow.lite.examples.poseestimation.exercise.home.back

import android.content.Context
import org.tensorflow.lite.examples.poseestimation.core.Point
import org.tensorflow.lite.examples.poseestimation.core.Utilities
import org.tensorflow.lite.examples.poseestimation.core.VisualizationUtils
import org.tensorflow.lite.examples.poseestimation.domain.model.Person
import org.tensorflow.lite.examples.poseestimation.exercise.home.HomeExercise

class SingleArmRaiseInQuadruped(
    context: Context
) : HomeExercise(
    context = context,
    id = 350
) {
    private var downKneeAngleMin = 75f
    private var downKneeAngleMax = 110f
    private var downHipAngleMin = 75f
    private var downHipAngleMax = 115f
    private var downShoulderAngleMin = 75f
    private var downShoulderAngleMax = 115f
    private var downElbowAngleMin = 160f//170f
    private var downElbowAngleMax = 185f

    private var upKneeAngleMin = 70f
    private var upKneeAngleMax = 110f
    private var upHipAngleMin = 90f
    private var upHipAngleMax = 110f
    private var upShoulderAngleMin = 140f//160f
    private var upShoulderAngleMax = 200f
    private var upElbowAngleMin = 150f
    private var upElbowAngleMax = 180f

    private var rightStateIndex = 0

    override var wrongStateIndex = 0
    private var wrongFrameCount = 0
    private val maxWrongCountFrame = 3

    override fun rightExerciseCount(
        person: Person,
        canvasHeight: Int,
        canvasWidth: Int
    ) {
        val leftWristPoint = Point(
            person.keyPoints[9].coordinate.x,
            -person.keyPoints[9].coordinate.y
        )
        val leftElbowPoint = Point(
            person.keyPoints[7].coordinate.x,
            -person.keyPoints[7].coordinate.y
        )
        val leftAnklePoint = Point(
            person.keyPoints[15].coordinate.x,
            -person.keyPoints[15].coordinate.y
        )
        val leftKneePoint = Point(
            person.keyPoints[13].coordinate.x,
            -person.keyPoints[13].coordinate.y
        )
        val leftShoulderPoint = Point(
            person.keyPoints[5].coordinate.x,
            -person.keyPoints[5].coordinate.y
        )
        val leftHipPoint = Point(
            person.keyPoints[11].coordinate.x,
            -person.keyPoints[11].coordinate.y
        )
        if (rightCountPhases.isNotEmpty()) {
            downKneeAngleMin = rightCountPhases[0].constraints[3].minValue.toFloat()
            downKneeAngleMax = rightCountPhases[0].constraints[3].maxValue.toFloat()
            downHipAngleMin = rightCountPhases[0].constraints[2].minValue.toFloat()
            downHipAngleMax = rightCountPhases[0].constraints[2].maxValue.toFloat()
            downShoulderAngleMin = rightCountPhases[0].constraints[1].minValue.toFloat()
            downShoulderAngleMax = rightCountPhases[0].constraints[1].maxValue.toFloat()
            downElbowAngleMin = rightCountPhases[0].constraints[0].minValue.toFloat()
            downElbowAngleMax = rightCountPhases[0].constraints[0].maxValue.toFloat()

            upKneeAngleMin = rightCountPhases[1].constraints[3].minValue.toFloat()
            upKneeAngleMax = rightCountPhases[1].constraints[3].maxValue.toFloat()
            upHipAngleMin = rightCountPhases[1].constraints[2].minValue.toFloat()
            upHipAngleMax = rightCountPhases[1].constraints[2].maxValue.toFloat()
            upShoulderAngleMin = rightCountPhases[1].constraints[1].minValue.toFloat()
            upShoulderAngleMax = rightCountPhases[1].constraints[1].maxValue.toFloat()
            upElbowAngleMin = rightCountPhases[1].constraints[0].minValue.toFloat()
            upElbowAngleMax = rightCountPhases[1].constraints[0].maxValue.toFloat()
        }

        val insideBox = VisualizationUtils.isInsideBox(person, canvasHeight, canvasWidth)
        val kneeAngle = Utilities.angle(leftHipPoint, leftKneePoint, leftAnklePoint, false)
        val hipAngle = Utilities.angle(leftShoulderPoint, leftHipPoint, leftKneePoint, true)
        val shoulderAngle = Utilities.angle(leftElbowPoint, leftShoulderPoint, leftHipPoint, true)
        val elbowAngle = Utilities.angle(leftWristPoint, leftElbowPoint, leftShoulderPoint, false)
        val rightCountStates: Array<FloatArray> = arrayOf(
            floatArrayOf(
                downKneeAngleMin,
                downKneeAngleMax,
                downHipAngleMin,
                downHipAngleMax,
                downShoulderAngleMin,
                downShoulderAngleMax,
                downElbowAngleMin,
                downElbowAngleMax
            ),
            floatArrayOf(
                upKneeAngleMin,
                upKneeAngleMax,
                upHipAngleMin,
                upHipAngleMax,
                upShoulderAngleMin,
                upShoulderAngleMax,
                upElbowAngleMin,
                upElbowAngleMax
            ),
            floatArrayOf(
                downKneeAngleMin,
                downKneeAngleMax,
                downHipAngleMin,
                downHipAngleMax,
                downShoulderAngleMin,
                downShoulderAngleMax,
                downElbowAngleMin,
                downElbowAngleMax
            )
        )
        if (kneeAngle > rightCountStates[rightStateIndex][0] && kneeAngle < rightCountStates[rightStateIndex][1]
            && hipAngle > rightCountStates[rightStateIndex][2] && hipAngle < rightCountStates[rightStateIndex][3]
            && shoulderAngle > rightCountStates[rightStateIndex][4] && shoulderAngle < rightCountStates[rightStateIndex][5]
            && elbowAngle > rightCountStates[rightStateIndex][6] && elbowAngle < rightCountStates[rightStateIndex][7]
            && insideBox
        ) {
            rightStateIndex += 1
            if (rightStateIndex == rightCountStates.size - 1) {
                wrongStateIndex = 0
            }
            if (rightStateIndex == rightCountStates.size) {
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