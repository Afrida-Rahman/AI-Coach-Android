package org.tensorflow.lite.examples.poseestimation.exercise

import android.content.Context
import android.graphics.Color
import org.tensorflow.lite.examples.poseestimation.R
import org.tensorflow.lite.examples.poseestimation.core.Point
import org.tensorflow.lite.examples.poseestimation.core.Utilities
import org.tensorflow.lite.examples.poseestimation.domain.model.Person
import org.tensorflow.lite.examples.poseestimation.domain.model.Phase
import org.tensorflow.lite.examples.poseestimation.domain.model.Rule
import org.tensorflow.lite.examples.poseestimation.domain.model.RuleType

class SingleArmRaiseInProne(
    context: Context
) : IExercise(
    context = context,
    id = 500,
    imageResourceId = R.drawable.exercise
) {
    private var downArmAngleMin = 190f
    private var downArmAngleMax = 220f
    private var upArmAngleMin = 160f
    private var upArmAngleMax = 180f

    private val totalStates = 3
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
        } else {
            downArmAngleMin = 190f
            downArmAngleMax = 220f
            upArmAngleMin = 160f
            upArmAngleMax = 180f
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
                standInside()
            } else if (wrongFrameCount >= maxWrongCountFrame) {
                wrongFrameCount = 0
            }
        }
    }

    override fun wrongExerciseCount(person: Person, canvasHeight: Int, canvasWidth: Int) {

    }

    override fun drawingRules(person: Person, phases: List<Phase>): List<Rule> {
        val leftWristPoint = Point(
            person.keyPoints[9].coordinate.x,
            person.keyPoints[9].coordinate.y
        )
        val leftShoulderPoint = Point(
            person.keyPoints[5].coordinate.x,
            person.keyPoints[5].coordinate.y
        )
        val leftHipPoint = Point(
            person.keyPoints[11].coordinate.x,
            person.keyPoints[11].coordinate.y
        )
        return mutableListOf(
            Rule(
                type = RuleType.ANGLE,
                startPoint = leftWristPoint,
                middlePoint = leftShoulderPoint,
                endPoint = leftHipPoint,
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