package org.tensorflow.lite.examples.poseestimation.exercise

import android.content.Context
import org.tensorflow.lite.examples.poseestimation.core.Point
import org.tensorflow.lite.examples.poseestimation.core.Utilities
import org.tensorflow.lite.examples.poseestimation.domain.model.Person
import org.tensorflow.lite.examples.poseestimation.domain.model.Phase
import org.tensorflow.lite.examples.poseestimation.domain.model.Rule
import org.tensorflow.lite.examples.poseestimation.domain.model.RuleType

class ProneOnElbows(
    context: Context
) : IExercise(
    context = context,
    id = 167
) {
    private var downHipAngleMin = 180f
    private var downHipAngleMax = 190f
    private var upHipAngleMin = 160f
    private var upHipAngleMax = 175f

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
            downHipAngleMin = phases[0].constraints[0].minValue.toFloat()
            downHipAngleMax = phases[0].constraints[0].maxValue.toFloat()
            upHipAngleMin = phases[1].constraints[0].minValue.toFloat()
            upHipAngleMax = phases[1].constraints[0].maxValue.toFloat()
        }

        val insideBox = isInsideBox(person, canvasHeight, canvasWidth)
        val hipAngle = Utilities.angle(leftShoulderPoint, leftHipPoint, leftKneePoint, false)
        val rightCountStates: Array<FloatArray> = arrayOf(
            floatArrayOf(
                downHipAngleMin,
                downHipAngleMax
            ),
            floatArrayOf(
                upHipAngleMin,
                upHipAngleMax
            ),
            floatArrayOf(
                downHipAngleMin,
                downHipAngleMax
            )
        )

        if (hipAngle > rightCountStates[rightStateIndex][0] && hipAngle < rightCountStates[rightStateIndex][1]
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
                clockWise = false
            )
        )
    }
}