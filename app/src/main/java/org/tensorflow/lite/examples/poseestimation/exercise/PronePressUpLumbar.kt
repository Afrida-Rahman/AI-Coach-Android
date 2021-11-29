package org.tensorflow.lite.examples.poseestimation.exercise

import android.content.Context
import android.util.Log
import org.tensorflow.lite.examples.poseestimation.core.Point
import org.tensorflow.lite.examples.poseestimation.core.Utilities
import org.tensorflow.lite.examples.poseestimation.domain.model.Person
import org.tensorflow.lite.examples.poseestimation.domain.model.Phase
import org.tensorflow.lite.examples.poseestimation.domain.model.Rule
import org.tensorflow.lite.examples.poseestimation.domain.model.RuleType

class PronePressUpLumbar(
    context: Context
) : IExercise(
    context = context,
    id = 158
) {
    private var downElbowAngleMin = 30f
    private var downElbowAngleMax = 200f
    private var upElbowAngleMin = 170f
    private var upElbowAngleMax = 190f

    private var downHipAngleMin = 170f
    private var downHipAngleMax = 190f
    private var upHipAngleMin = 120f
    private var upHipAngleMax = 150f

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
            if (phases[0].constraints.size > 1) {
                downElbowAngleMin = phases[0].constraints[0].minValue.toFloat()
                downElbowAngleMax = phases[0].constraints[0].maxValue.toFloat()
                downHipAngleMin = phases[0].constraints[1].minValue.toFloat()
                downHipAngleMax = phases[0].constraints[1].maxValue.toFloat()

                upElbowAngleMin = phases[1].constraints[0].minValue.toFloat()
                upElbowAngleMax = phases[1].constraints[0].maxValue.toFloat()
                upHipAngleMin = phases[1].constraints[1].minValue.toFloat()
                upHipAngleMax = phases[1].constraints[1].maxValue.toFloat()
            }
        }

        val insideBox = isInsideBox(person, canvasHeight, canvasWidth)
        val elbowAngle = Utilities.angle(leftWristPoint, leftElbowPoint, leftShoulderPoint, false)
        val hipAngle = Utilities.angle(leftShoulderPoint, leftHipPoint, leftKneePoint, true)
        val rightCountStates: Array<FloatArray> = arrayOf(
            floatArrayOf(
                downElbowAngleMin,
                downElbowAngleMax,
                downHipAngleMin,
                downHipAngleMax
            ),
            floatArrayOf(
                upElbowAngleMin,
                upElbowAngleMax,
                upHipAngleMin,
                upHipAngleMax
            ),
            floatArrayOf(
                downElbowAngleMin,
                downElbowAngleMax,
                downHipAngleMin,
                downHipAngleMax
            )
        )
        if (elbowAngle > rightCountStates[rightStateIndex][0] && elbowAngle < rightCountStates[rightStateIndex][1]
            && hipAngle > rightCountStates[rightStateIndex][2] && hipAngle < rightCountStates[rightStateIndex][3]
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
        val leftElbowPoint = Point(
            person.keyPoints[7].coordinate.x,
            person.keyPoints[7].coordinate.y
        )
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
        return mutableListOf(
            Rule(
                type = RuleType.ANGLE,
                startPoint = leftWristPoint,
                middlePoint = leftElbowPoint,
                endPoint = leftShoulderPoint,
                clockWise = false
            ),
            Rule(
                type = RuleType.ANGLE,
                startPoint = leftShoulderPoint,
                middlePoint = leftHipPoint,
                endPoint = leftKneePoint,
                clockWise = false
            )
        )
    }
}