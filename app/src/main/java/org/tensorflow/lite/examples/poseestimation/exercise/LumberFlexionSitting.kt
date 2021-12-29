package org.tensorflow.lite.examples.poseestimation.exercise

import android.content.Context
import org.tensorflow.lite.examples.poseestimation.core.Point
import org.tensorflow.lite.examples.poseestimation.core.Utilities
import org.tensorflow.lite.examples.poseestimation.domain.model.Person
import org.tensorflow.lite.examples.poseestimation.domain.model.Phase
import org.tensorflow.lite.examples.poseestimation.domain.model.Rule
import org.tensorflow.lite.examples.poseestimation.domain.model.RuleType

class LumberFlexionSitting(
    context: Context
) : IExercise(
    context = context,
    id = 0 //341
) {
    private var sittingHipAngleMin = 200f
    private var sittingHipAngleMax = 250f
    private var sittingShoulderAngleMin = 120f
    private var sittingShoulderAngleMax = 190f

    private var downHipAngleMin = 260f
    private var downHipAngleMax = 320f
    private var downShoulderAngleMin = 80f
    private var downShoulderAngleMax = 140f

    private var wrongUpHipAngleMin = 160f
    private var wrongUpHipAngleMax = 190f
    private var wrongUpKneeAngleMin = 160f
    private var wrongUpKneeAngleMax = 190f

    private var wrongDownHipAngleMin = 100f
    private var wrongDownHipAngleMax = 160f
    private var wrongDownKneeAngleMin = 100f
    private var wrongDownKneeAngleMax = 160f

    private val totalStates = 3
    private var rightStateIndex = 0

    private var wrongStateIndex = 0
    private var wrongFrameCount = 0
    private val maxWrongCountFrame = 3

    override fun rightExerciseCount(
        person: Person,
        canvasHeight: Int,
        canvasWidth: Int,
        phases: List<Phase>
    ) {
        val leftElbowPoint = Point(
            person.keyPoints[7].coordinate.x,
            -person.keyPoints[7].coordinate.y
        )
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
        if (phases.size >= 2) {
            sittingHipAngleMin = phases[0].constraints[0].minValue.toFloat()
            sittingHipAngleMax = phases[0].constraints[0].maxValue.toFloat()
            sittingShoulderAngleMin = phases[0].constraints[0].minValue.toFloat()
            sittingShoulderAngleMax = phases[0].constraints[0].maxValue.toFloat()

            downHipAngleMin = phases[1].constraints[0].minValue.toFloat()
            downHipAngleMax = phases[1].constraints[0].maxValue.toFloat()
            downShoulderAngleMin = phases[1].constraints[0].minValue.toFloat()
            downShoulderAngleMax = phases[1].constraints[0].maxValue.toFloat()
        }

        val insideBox = isInsideBox(person, canvasHeight, canvasWidth)
        val hipAngle = Utilities.angle(leftShoulderPoint, leftHipPoint, leftKneePoint, false)
        val shoulderAngle = Utilities.angle(leftHipPoint, leftShoulderPoint, leftElbowPoint, false)

        val rightCountStates: Array<FloatArray> = arrayOf(
            floatArrayOf(
                sittingHipAngleMin,
                sittingHipAngleMax,
                sittingShoulderAngleMin,
                sittingShoulderAngleMax
            ),
            floatArrayOf(
                downHipAngleMin,
                downHipAngleMax,
                downShoulderAngleMin,
                downShoulderAngleMax
            ),
            floatArrayOf(
                sittingHipAngleMin,
                sittingHipAngleMax,
                sittingShoulderAngleMin,
                sittingShoulderAngleMax
            )
        )
        if (hipAngle > rightCountStates[rightStateIndex][0] && hipAngle < rightCountStates[rightStateIndex][1]
            && shoulderAngle > rightCountStates[rightStateIndex][2] && shoulderAngle < rightCountStates[rightStateIndex][3]
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
        val leftElbowPoint = Point(
            person.keyPoints[7].coordinate.x,
            -person.keyPoints[7].coordinate.y
        )
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

        wrongUpHipAngleMin = sittingHipAngleMin
        wrongUpHipAngleMax = sittingHipAngleMax
        wrongUpKneeAngleMin = sittingShoulderAngleMin
        wrongUpKneeAngleMax = sittingShoulderAngleMax
        wrongDownHipAngleMin = downHipAngleMin + 40
        wrongDownHipAngleMax = downHipAngleMax + 70
        wrongDownKneeAngleMin = downShoulderAngleMin + 40
        wrongDownKneeAngleMax = downShoulderAngleMax + 70

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
        val hipAngle = Utilities.angle(shoulderPoint, hipPoint, kneePoint, false)
        val shoulderAngle = Utilities.angle(leftElbowPoint, shoulderPoint, hipPoint)

        if (hipAngle > wrongCountStates[wrongStateIndex][0] && hipAngle < wrongCountStates[wrongStateIndex][1] &&
            shoulderAngle > wrongCountStates[wrongStateIndex][2] && shoulderAngle < wrongCountStates[wrongStateIndex][3] &&
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
        val elbowPoint = Point(
            person.keyPoints[7].coordinate.x,
            person.keyPoints[7].coordinate.y
        )
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
                startPoint = kneePoint,
                middlePoint = hipPoint,
                endPoint = shoulderPoint,
                clockWise = true
            ),
            Rule(
                type = RuleType.ANGLE,
                startPoint = hipPoint,
                middlePoint = shoulderPoint,
                endPoint = elbowPoint,
                clockWise = false
            )
        )
    }
}
