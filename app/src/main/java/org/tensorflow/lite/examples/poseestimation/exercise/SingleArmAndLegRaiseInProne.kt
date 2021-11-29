package org.tensorflow.lite.examples.poseestimation.exercise

import android.content.Context
import android.util.Log
import org.tensorflow.lite.examples.poseestimation.core.Point
import org.tensorflow.lite.examples.poseestimation.core.Utilities
import org.tensorflow.lite.examples.poseestimation.domain.model.Person
import org.tensorflow.lite.examples.poseestimation.domain.model.Phase
import org.tensorflow.lite.examples.poseestimation.domain.model.Rule
import org.tensorflow.lite.examples.poseestimation.domain.model.RuleType

class SingleArmAndLegRaiseInProne(
    context: Context
) :  IExercise(
    context = context,
    id = 501
) {
    private var downArmAngleMin = 190f
    private var downArmAngleMax = 220f
    private var downLegAngleMin = 190f
    private var downLegAngleMax = 220f

    private var upArmAngleMin = 160f
    private var upArmAngleMax = 180f
    private var upLegAngleMin = 140f
    private var upLegAngleMax = 170f

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
        val rightKneePoint = Point(
            person.keyPoints[14].coordinate.x,
            -person.keyPoints[14].coordinate.y
        )
        val rightAnklePoint = Point(
            person.keyPoints[16].coordinate.x,
            -person.keyPoints[16].coordinate.y
        )
        if (phases.size >= 2){
            downArmAngleMin = phases[0].constraints[0].minValue.toFloat()
            downArmAngleMax = phases[0].constraints[0].maxValue.toFloat()
            downLegAngleMin = phases[0].constraints[0].minValue.toFloat()
            downLegAngleMax = phases[0].constraints[0].maxValue.toFloat()

            upArmAngleMin = phases[1].constraints[0].minValue.toFloat()
            upArmAngleMax = phases[1].constraints[0].maxValue.toFloat()
            upLegAngleMin = phases[1].constraints[0].minValue.toFloat()
            upLegAngleMax = phases[1].constraints[0].maxValue.toFloat()
        }
        val insideBox = isInsideBox(person, canvasHeight, canvasWidth)
        val shoulderAngle = Utilities.angle(leftWristPoint, leftShoulderPoint, leftHipPoint, false)
        val kneeAngle = Utilities.angle(leftHipPoint, rightKneePoint, rightAnklePoint, false)
        val rightCountStates: Array<FloatArray> = arrayOf(
            floatArrayOf(
                downArmAngleMin,
                downArmAngleMax,
                downLegAngleMin,
                downLegAngleMax
            ),
            floatArrayOf(
                upArmAngleMin,
                upArmAngleMax,
                upLegAngleMin,
                upLegAngleMax
            ),
            floatArrayOf(
                downArmAngleMin,
                downArmAngleMax,
                downLegAngleMin,
                downLegAngleMax
            )
        )
        Log.d("Armangle","{shoulder: $shoulderAngle, knee: $kneeAngle}")
        if (shoulderAngle > rightCountStates[rightStateIndex][0] && shoulderAngle < rightCountStates[rightStateIndex][1]
            && kneeAngle > rightCountStates[rightStateIndex][2] && kneeAngle < rightCountStates[rightStateIndex][3]
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
        val rightKneePoint = Point(
            person.keyPoints[14].coordinate.x,
            person.keyPoints[14].coordinate.y
        )
        val rightAnklePoint = Point(
            person.keyPoints[16].coordinate.x,
            person.keyPoints[16].coordinate.y
        )
        return mutableListOf(
            Rule(
                type = RuleType.ANGLE,
                startPoint = leftWristPoint,
                middlePoint = leftShoulderPoint,
                endPoint = leftHipPoint,
                clockWise = false
            ),
            Rule(
                type = RuleType.ANGLE,
                startPoint = leftHipPoint,
                middlePoint = rightKneePoint,
                endPoint = rightAnklePoint,
                clockWise = false
            )
        )
    }
}