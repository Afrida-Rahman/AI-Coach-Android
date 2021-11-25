package org.tensorflow.lite.examples.poseestimation.exercise

import android.content.Context
import android.graphics.Color
import org.tensorflow.lite.examples.poseestimation.core.Point
import org.tensorflow.lite.examples.poseestimation.core.Utilities
import org.tensorflow.lite.examples.poseestimation.domain.model.Person
import org.tensorflow.lite.examples.poseestimation.domain.model.Phase
import org.tensorflow.lite.examples.poseestimation.domain.model.Rule
import org.tensorflow.lite.examples.poseestimation.domain.model.RuleType

class SingleLegRaiseInProne(
    context: Context
) : IExercise(
    context = context,
    id = 499
) {
    private var downHipAngleMin = 175f
    private var downHipAngleMax = 310f
    private var upHipAngleMin = 120f
    private var upHipAngleMax = 140f

    private var downKneeAngleMin = 170f
    private var downKneeAngleMax = 190f
    private var upKneeAngleMin = 170f
    private var upKneeAngleMax = 190f

    private var isInLastState = false
    private var lastStateTimestamp = 0L

    private val totalStates = 2
    private var rightStateIndex = 0

    private var wrongStateIndex = 0
    private var wrongFrameCount = 0
    private val maxWrongCountFrame = 3
    override fun exerciseCount(
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
            rightStateIndex += 1
            if (rightStateIndex == rightCountStates.size - 1) {
                wrongStateIndex = 0
                isInLastState = false
            }
            if (rightStateIndex == totalStates) {
                if (!isInLastState) {
                    isInLastState = true
                    lastStateTimestamp = System.currentTimeMillis()
                } else {
                    if ((System.currentTimeMillis() - lastStateTimestamp) > 2000) {
                        rightStateIndex = 0
                        repetitionCount()
                    }
                }
            }
        } else {
            if (!insideBox) {
                standInside()
            } else if (wrongFrameCount >= maxWrongCountFrame) {
                wrongFrameCount = 0
            }
        }
    }

    override fun wrongExerciseCount(person: Person, canvasHeight: Int, canvasWidth: Int) {

    }

    override fun drawingRules(person: Person, phases: List<Phase>): List<Rule> {
        val leftShoulderPoint = Point(
            person.keyPoints[5].coordinate.x,
            person.keyPoints[5].coordinate.y
        )
        val leftHipPoint = Point(
            person.keyPoints[11].coordinate.x,
            person.keyPoints[11].coordinate.y
        )
        val leftKneePoint = Point(
            person.keyPoints[13].coordinate.x,
            person.keyPoints[13].coordinate.y
        )
        val leftAnklePoint = Point(
            person.keyPoints[15].coordinate.x,
            person.keyPoints[15].coordinate.y
        )
        return mutableListOf(
            Rule(
                type = RuleType.ANGLE,
                startPoint = leftShoulderPoint,
                middlePoint = leftHipPoint,
                endPoint = leftKneePoint,
                clockWise = false
            ),
            Rule(
                type = RuleType.ANGLE,
                startPoint = leftHipPoint,
                middlePoint = leftKneePoint,
                endPoint = leftAnklePoint,
                clockWise = false
            )
        )
    }

    override fun getBorderColor(person: Person, canvasHeight: Int, canvasWidth: Int): Int {
        return if (isInsideBox(person, canvasHeight, canvasWidth)) {
            Color.GREEN
        } else {
            Color.RED
        }
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
}