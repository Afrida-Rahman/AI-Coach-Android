package org.tensorflow.lite.examples.poseestimation.exercise

import android.content.Context
import android.graphics.Color
import org.tensorflow.lite.examples.poseestimation.R
import org.tensorflow.lite.examples.poseestimation.core.Point
import org.tensorflow.lite.examples.poseestimation.core.Utilities
import org.tensorflow.lite.examples.poseestimation.domain.model.Person
import org.tensorflow.lite.examples.poseestimation.domain.model.Phase
import org.tensorflow.lite.examples.poseestimation.domain.model.Rule

class SingleLegRaiseInQuadruped(
    context: Context
) : IExercise(
    context = context,
    id = 502,
    imageResourceId = R.drawable.exercise
) {
    private var hipAngleMin = 70f
    private var hipAngleMax = 100f

    private var upKneeAngleMin = 160f
    private var upKneeAngleMax = 190f
    private var downKneeAngleMin = 70f
    private var downKneeAngleMax = 100f

    private var wrongUpKneeAngleMin = 0f
    private var wrongUpKneeAngleMax = 0f
    private var wrongDownKneeAngleMin = 0f
    private var wrongDownKneeAngleMax = 0f

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
        val rightKneePoint = Point(
            person.keyPoints[14].coordinate.x,
            -person.keyPoints[14].coordinate.y
        )
        val leftAnklePoint = Point(
            person.keyPoints[15].coordinate.x,
            -person.keyPoints[15].coordinate.y
        )
        if (phases.size >= 2){
            downKneeAngleMin = phases[0].constraints[0].minValue.toFloat()
            downKneeAngleMax = phases[0].constraints[0].maxValue.toFloat()

            upKneeAngleMin = phases[0].constraints[1].minValue.toFloat()
            upKneeAngleMax = phases[0].constraints[1].maxValue.toFloat()
        }else{
            downKneeAngleMin = 80f
            downKneeAngleMax = 120f

            upKneeAngleMin = 160f
            upKneeAngleMax = 190f

        }

        val insideBox = isInsideBox(person, canvasHeight, canvasWidth)
        val hipAngle = Utilities.angle(leftShoulderPoint, leftHipPoint, leftKneePoint, true)
        val kneeAngle = Utilities.angle(leftHipPoint, leftKneePoint, leftAnklePoint)

        val rightCountStates: Array<FloatArray> = arrayOf(
            floatArrayOf(
                hipAngleMin,
                hipAngleMax,
                upKneeAngleMin,
                upKneeAngleMax
            ),
            floatArrayOf(
                hipAngleMin,
                hipAngleMax,
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

    }

    override fun wrongExerciseCount(person: Person, canvasHeight: Int, canvasWidth: Int) {

    }

    override fun drawingRules(person: Person, phases: List<Phase>): List<Rule> {
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