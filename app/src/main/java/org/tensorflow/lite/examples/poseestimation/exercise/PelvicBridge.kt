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

class PelvicBridge(
    context: Context
) : IExercise(
    context = context,
    id = 122,
    imageResourceId = R.drawable.reach_arms_over_head //need to change
) {
    private var hipAngleDownMin = 115f
    private var hipAngleDownMax = 135f
    private var hipAngleUpMin = 160f
    private var hipAngleUpMax = 190f

    private val totalStates = 3
    private var rightStateIndex = 0

    override fun exerciseCount(
        person: Person,
        canvasHeight: Int,
        canvasWidth: Int,
        phases: List<Phase>
    ) {
        val rightShoulderPoint = Point(
            person.keyPoints[6].coordinate.x,
            -person.keyPoints[6].coordinate.y
        )
        val rightHipPoint = Point(
            person.keyPoints[12].coordinate.x,
            -person.keyPoints[12].coordinate.y
        )
        val rightKneePoint = Point(
            person.keyPoints[14].coordinate.x,
            -person.keyPoints[14].coordinate.y
        )
        if (phases.size >= 2) {
            hipAngleDownMin = phases[0].constraints[0].minValue.toFloat()
            hipAngleDownMax = phases[0].constraints[0].maxValue.toFloat()
            hipAngleUpMin = phases[1].constraints[0].minValue.toFloat()
            hipAngleUpMax = phases[1].constraints[0].maxValue.toFloat()
        } else {
            hipAngleDownMin = 115f
            hipAngleDownMax = 135f
            hipAngleUpMin = 160f
            hipAngleUpMax = 190f
        }
        val insideBox = isInsideBox(person, canvasHeight, canvasWidth)
        val rightHipAngle = Utilities.angle(rightShoulderPoint, rightHipPoint, rightKneePoint)
        val rightCountStates: Array<FloatArray> = arrayOf(
            floatArrayOf(
                hipAngleDownMin,
                hipAngleDownMax
            ),
            floatArrayOf(
                hipAngleUpMin,
                hipAngleUpMax
            ),
            floatArrayOf(
                hipAngleDownMin,
                hipAngleDownMax
            )
        )
        if (rightHipAngle > rightCountStates[rightStateIndex][0] && rightHipAngle < rightCountStates[rightStateIndex][1] && insideBox) {
            rightStateIndex +=1
            if (rightStateIndex == totalStates) {
                rightStateIndex = 0
                repetitionCount()
            }
        } else {
            if (!insideBox) {
                standInside()
            }
        }
    }

    override fun wrongExerciseCount(person: Person, canvasHeight: Int, canvasWidth: Int) {

    }

    override fun drawingRules(person: Person, phases: List<Phase>): List<Rule> {
        val rightShoulderPoint = Point(
            person.keyPoints[6].coordinate.x,
            person.keyPoints[6].coordinate.y
        )
        val rightHipPoint = Point(
            person.keyPoints[12].coordinate.x,
            person.keyPoints[12].coordinate.y
        )
        val rightKneePoint = Point(
            person.keyPoints[14].coordinate.x,
            person.keyPoints[14].coordinate.y
        )
        return mutableListOf(
            Rule(
                type = RuleType.ANGLE,
                startPoint = rightShoulderPoint,
                middlePoint = rightHipPoint,
                endPoint = rightKneePoint
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