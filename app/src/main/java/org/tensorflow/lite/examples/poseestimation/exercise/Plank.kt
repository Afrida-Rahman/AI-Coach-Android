package org.tensorflow.lite.examples.poseestimation.exercise

import android.content.Context
import android.util.Log
import org.tensorflow.lite.examples.poseestimation.core.Point
import org.tensorflow.lite.examples.poseestimation.core.Utilities
import org.tensorflow.lite.examples.poseestimation.domain.model.Person
import org.tensorflow.lite.examples.poseestimation.domain.model.Phase
import org.tensorflow.lite.examples.poseestimation.domain.model.Rule
import org.tensorflow.lite.examples.poseestimation.domain.model.RuleType

class Plank(
    context: Context
) : IExercise(
    context = context,
    id = 352
) {
    private var downHipAngleMin = 200f
    private var downHipAngleMax = 220f

    private var upHipAngleMin = 170f
    private var upHipAngleMax = 185f

    private var wrongUpHipAngleMin = 0f
    private var wrongUpHipAngleMax = 0f

    private var wrongDownHipAngleMin = 0f
    private var wrongDownHipAngleMax = 0f

    private var isInLastState = false
    private var lastStateTimestamp = 0L
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
//        if (phases.isNotEmpty()) {
//            upHipAngleMin = phases[0].constraints[0].minValue.toFloat()
//            upHipAngleMax = phases[0].constraints[0].maxValue.toFloat()
//            downHipAngleMin = phases[1].constraints[0].minValue.toFloat()
//            downHipAngleMax = phases[1].constraints[0].maxValue.toFloat()
//        }

        val insideBox = isInsideBox(person, canvasHeight, canvasWidth)
        val hipAngle = Utilities.angle(leftShoulderPoint, leftHipPoint, leftKneePoint, true)

        val rightCountStates: Array<FloatArray> = arrayOf(
            floatArrayOf(
                downHipAngleMin,
                downHipAngleMax
            ),
            floatArrayOf(
                upHipAngleMin,
                upHipAngleMax
            )
        )
        if (hipAngle > rightCountStates[rightStateIndex][0] && hipAngle < rightCountStates[rightStateIndex][1]
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
            if (!insideBox) {
                standInside()
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

        wrongUpHipAngleMin = upHipAngleMin
        wrongUpHipAngleMax = upHipAngleMax
        wrongDownHipAngleMin = downHipAngleMin + 40
        wrongDownHipAngleMax = downHipAngleMax + 70

        val wrongCountStates: Array<FloatArray> = arrayOf(
            floatArrayOf(
                wrongUpHipAngleMin,
                wrongUpHipAngleMax,
            ),
            floatArrayOf(
                wrongDownHipAngleMin,
                wrongDownHipAngleMax,
            ),
            floatArrayOf(
                wrongUpHipAngleMin,
                wrongUpHipAngleMax,
            )
        )

        val insideBox = isInsideBox(person, canvasHeight, canvasWidth)
        val hipAngle = Utilities.angle(shoulderPoint, hipPoint, kneePoint, true)


        if (hipAngle > wrongCountStates[wrongStateIndex][0] && hipAngle < wrongCountStates[wrongStateIndex][1] &&
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
        return mutableListOf(
            Rule(
                type = RuleType.ANGLE,
                startPoint = shoulderPoint,
                middlePoint = hipPoint,
                endPoint = kneePoint,
                clockWise = true
            )
        )
    }
}
