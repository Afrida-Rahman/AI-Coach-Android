package org.tensorflow.lite.examples.poseestimation.exercise

import android.content.Context
import android.graphics.Color
import android.util.Log
import org.tensorflow.lite.examples.poseestimation.R
import org.tensorflow.lite.examples.poseestimation.core.Point
import org.tensorflow.lite.examples.poseestimation.core.Utilities
import org.tensorflow.lite.examples.poseestimation.domain.model.Person
import org.tensorflow.lite.examples.poseestimation.domain.model.Phase
import org.tensorflow.lite.examples.poseestimation.domain.model.Rule
import org.tensorflow.lite.examples.poseestimation.domain.model.RuleType

class KneeSquat(
    context: Context
) : IExercise(
    context = context,
    id = 418, //458,
    imageResourceId = R.drawable.knee_squat
) {
    private var upHipAngleMin = 165f
    private var upHipAngleMax = 190f
    private var upKneeAngleMin = 165f
    private var upKneeAngleMax = 190f

    private var downHipAngleMin = 60f
    private var downHipAngleMax = 90f
    private var downKneeAngleMin = 60f
    private var downKneeAngleMax = 90f

    private var wrongUpHipAngleMin = 0f
    private var wrongUpHipAngleMax = 0f
    private var wrongUpKneeAngleMin = 0f
    private var wrongUpKneeAngleMax = 0f

    private var wrongDownHipAngleMin = 0f
    private var wrongDownHipAngleMax = 0f
    private var wrongDownKneeAngleMin = 0f
    private var wrongDownKneeAngleMax = 0f

//    private val straightHandAngleMin = 150f
//    private val straightHandAngleMax = 210f

    private val totalStates = 3
    private var rightStateIndex = 0
    private var wrongStateIndex = 0

    override fun exerciseCount(person: Person, canvasHeight: Int, canvasWidth: Int, phases: List<Phase>) {
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
        Log.d("hi", "hipAngle:: $hipAngle , kneeAngle:: $kneeAngle")

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
            rightStateIndex +=1
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
        wrongDownHipAngleMin = downHipAngleMin + 30
        wrongDownHipAngleMax = downHipAngleMax + 30
        wrongDownKneeAngleMin = downKneeAngleMin + 30
        wrongDownKneeAngleMax = downKneeAngleMax + 30

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