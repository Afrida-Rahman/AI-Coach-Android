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

class HalfSquat(
    context: Context
) : IExercise(
    context = context,
    id = 495,
    imageResourceId = R.drawable.half_squat
) {
    private var upHipAngleMin = 170f
    private var upHipAngleMax = 190f
    private var upKneeAngleMin = 170f
    private var upKneeAngleMax = 190f

    private var downHipAngleMin = 80f
    private var downHipAngleMax = 120f
    private var downKneeAngleMin = 80f
    private var downKneeAngleMax = 120f

    private var wrongUpHipAngleMin = 0f
    private var wrongUpHipAngleMax = 0f
    private var wrongUpKneeAngleMin = 0f
    private var wrongUpKneeAngleMax = 0f

    private var wrongDownHipAngleMin = 0f
    private var wrongDownHipAngleMax = 0f
    private var wrongDownKneeAngleMin = 0f
    private var wrongDownKneeAngleMax = 0f

    private val totalStates = 3
    private var rightStateIndex = 0
    private var wrongStateIndex = 0

    override fun exerciseCount(person: Person, canvasHeight: Int,canvasWidth: Int,phases: List<Phase>) {
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
//        if (phases.size >= 2) {
//            upHipAngleMin = phases[0].constraints[0].minValue.toFloat()
//            upHipAngleMax = phases[0].constraints[0].maxValue.toFloat()
//            downHipAngleMin = phases[1].constraints[0].minValue.toFloat()
//            downHipAngleMax = phases[1].constraints[0].maxValue.toFloat()
//        } else {
//            upHipAngleMin = 160f
//            upHipAngleMax = 190f
//            downHipAngleMin = 60f
//            downHipAngleMax = 90f
//        }

        val insideBox = isInsideBox(person, canvasHeight, canvasWidth)
        val hipAngle = Utilities.angle(shoulderPoint, hipPoint, kneePoint, true)
        val kneeAngle = Utilities.angle(hipPoint, kneePoint, anklePoint)

        val rightCountStates: Array<FloatArray> = arrayOf(
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
            ),
            floatArrayOf(
                upHipAngleMin,
                upHipAngleMax,
                upKneeAngleMin,
                upKneeAngleMax
            )
        )
        if (hipAngle > rightCountStates[rightStateIndex][0] && hipAngle < rightCountStates[rightStateIndex][1] &&
            kneeAngle > rightCountStates[rightStateIndex][3] && kneeAngle < rightCountStates[rightStateIndex][4] &&
            insideBox
        ) {
            if (rightStateIndex == totalStates) {
                rightStateIndex = 0
                repetitionCount()
            }
        } else{
            if (!insideBox){
                standInside()
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
        wrongDownHipAngleMin = downHipAngleMin + 40
        wrongDownHipAngleMax = downHipAngleMax + 40
        wrongDownKneeAngleMin = downKneeAngleMin + 40
        wrongDownKneeAngleMax = downKneeAngleMax + 40

        val wrongCountStates: Array<FloatArray> = arrayOf(
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
            ),
            floatArrayOf(
                wrongUpHipAngleMin,
                wrongUpHipAngleMax,
                wrongUpKneeAngleMin,
                wrongUpKneeAngleMax
            )
        )

        val insideBox = isInsideBox(person, canvasHeight, canvasWidth)
        val hipAngle = Utilities.angle(shoulderPoint, hipPoint, kneePoint, true)
        val kneeAngle = Utilities.angle(hipPoint, kneePoint, anklePoint)


        if (hipAngle > wrongCountStates[wrongStateIndex][0] && hipAngle < wrongCountStates[wrongStateIndex][1] &&
            kneeAngle > wrongCountStates[wrongStateIndex][3] && kneeAngle < wrongCountStates[wrongStateIndex][4] &&
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

    override fun drawingRules(person: Person, phases: List<Phase>): List<Rule> {
        val shoulderPoint = Point(
            person.keyPoints[5].coordinate.x,
            person.keyPoints[5].coordinate.y
        )
        val hipPoint = Point(
            person.keyPoints[11].coordinate.x,
            person.keyPoints[11].coordinate.y
        )
        val kneePoint = Point(
            person.keyPoints[13].coordinate.x,
            person.keyPoints[13].coordinate.y
        )
        val anklePoint = Point(
            person.keyPoints[15].coordinate.x,
            person.keyPoints[15].coordinate.y
        )
        return mutableListOf(
            Rule(
                type = RuleType.ANGLE,
                startPoint = shoulderPoint,
                middlePoint = hipPoint,
                endPoint = kneePoint,
                clockWise = true
            ),
            Rule(
                type = RuleType.ANGLE,
                startPoint = hipPoint,
                middlePoint = kneePoint,
                endPoint = anklePoint,
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