package org.tensorflow.lite.examples.poseestimation.exercise

import android.content.Context
import org.tensorflow.lite.examples.poseestimation.core.Point
import org.tensorflow.lite.examples.poseestimation.core.Utilities
import org.tensorflow.lite.examples.poseestimation.domain.model.Person
import org.tensorflow.lite.examples.poseestimation.domain.model.Phase

class SingleLegRaiseInProne(
    context: Context
) : IExercise(
    context = context,
    id = 499
) {
    private var downHipAngleMin = 170f
    private var downHipAngleMax = 200f
    private var downKneeAngleMin = 140f
    private var downKneeAngleMax = 190f

    private var upHipAngleMin = 120f
    private var upHipAngleMax = 160f
    private var upKneeAngleMin = 140f
    private var upKneeAngleMax = 220f

    private var isInLastState = false
    private var lastStateTimestamp = 0L

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
            downHipAngleMin = phases[0].constraints[0].minValue.toFloat()
            downHipAngleMax = phases[0].constraints[0].maxValue.toFloat()
            downKneeAngleMin = phases[0].constraints[0].minValue.toFloat()
            downKneeAngleMax = phases[0].constraints[0].maxValue.toFloat()

            upHipAngleMin = phases[1].constraints[0].minValue.toFloat()
            upHipAngleMax = phases[1].constraints[0].maxValue.toFloat()
            upKneeAngleMin = phases[1].constraints[0].minValue.toFloat()
            upKneeAngleMax = phases[1].constraints[0].maxValue.toFloat()
        }

        val insideBox = isInsideBox(person, canvasHeight, canvasWidth)
        val hipAngle = Utilities.angle(leftShoulderPoint, leftHipPoint, leftKneePoint, false)
        val kneeAngle = Utilities.angle(leftHipPoint, leftKneePoint, leftAnklePoint, false)

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
            )
        )
        if (hipAngle > rightCountStates[rightStateIndex][0] && hipAngle < rightCountStates[rightStateIndex][1]
            && kneeAngle > rightCountStates[rightStateIndex][2] && kneeAngle < rightCountStates[rightStateIndex][3]
            && insideBox
        ) {

            if (rightCountStates.size - 1 > rightStateIndex) rightStateIndex += 1
            if (rightStateIndex == rightCountStates.size - 1) {
                wrongStateIndex = 0
            }
            if (rightStateIndex == rightCountStates.size - 1) {
                if (!isInLastState) {
                    isInLastState = true
                    lastStateTimestamp = System.currentTimeMillis()
                } else {
                    if ((System.currentTimeMillis() - lastStateTimestamp) > 1000) {
                        rightStateIndex = 0
                        repetitionCount()
                        isInLastState = false
                    }
                }
            }
        } else {
            isInLastState = false
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