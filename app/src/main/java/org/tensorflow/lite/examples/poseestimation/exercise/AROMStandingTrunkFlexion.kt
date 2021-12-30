package org.tensorflow.lite.examples.poseestimation.exercise

import android.content.Context
import org.tensorflow.lite.examples.poseestimation.core.Point
import org.tensorflow.lite.examples.poseestimation.core.Utilities
import org.tensorflow.lite.examples.poseestimation.domain.model.Person
import org.tensorflow.lite.examples.poseestimation.domain.model.Phase

class TrunkFlexionInStanding(
    context: Context
) : IExercise(
    context = context,
    id = 0// 178
) {
    private var hipAngleUpMin = 160f
    private var hipAngleUpMax = 190f
    private var hipAngleDownMin = 30f
    private var hipAngleDownMax = 70f

    private var wrongHipAngleUpMin = 160f
    private var wrongHipAngleUpMax = 190f
    private var wrongHipAngleDownMin = 70f
    private var wrongHipAngleDownMax = 150f

    private val straightHandAngleMin = 150f
    private val straightHandAngleMax = 225f

    private val totalStates = 3

    private var rightStateIndex = 0
    override var wrongStateIndex = 0
    private var wrongFrameCount = 0
    private val maxWrongCountFrame = 3

    override fun rightExerciseCount(
        person: Person,
        canvasHeight: Int,
        canvasWidth: Int,
        phases: List<Phase>
    ) {
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
        val leftWristPoint = Point(
            person.keyPoints[9].coordinate.x,
            person.keyPoints[9].coordinate.y
        )
        val leftElbowPoint = Point(
            person.keyPoints[7].coordinate.x,
            person.keyPoints[7].coordinate.y
        )

        if (phases.size >= 2) {
            hipAngleUpMin = phases[0].constraints[0].minValue.toFloat()
            hipAngleUpMax = phases[0].constraints[0].maxValue.toFloat()
            hipAngleDownMin = phases[1].constraints[0].minValue.toFloat()
            hipAngleDownMax = phases[1].constraints[0].maxValue.toFloat()
        }

        val leftHipAngle = Utilities.angle(leftShoulderPoint, leftHipPoint, leftKneePoint)
        val leftStraightHandAngle =
            Utilities.angle(leftShoulderPoint, leftElbowPoint, leftWristPoint, true)

        val insideBox = isInsideBox(person, canvasHeight, canvasWidth)
        val rightCountStates: Array<FloatArray> = arrayOf(
            floatArrayOf(
                hipAngleUpMin,
                hipAngleUpMax
            ),
            floatArrayOf(
                hipAngleDownMin,
                hipAngleDownMax
            ),
            floatArrayOf(
                hipAngleUpMin,
                hipAngleUpMax
            )
        )

        val leftHandStraight =
            leftStraightHandAngle > straightHandAngleMin && leftStraightHandAngle < straightHandAngleMax

        if (leftHipAngle > rightCountStates[rightStateIndex][0]
            && leftHipAngle < rightCountStates[rightStateIndex][1]
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
            if (!leftHandStraight) {
                wrongFrameCount++
                if (wrongFrameCount >= maxWrongCountFrame) {
                    leftHandNotStraight()
                    wrongFrameCount = 0
                }
            }
        }

    }

    override fun wrongExerciseCount(person: Person, canvasHeight: Int, canvasWidth: Int) {
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

        wrongHipAngleUpMin = hipAngleUpMin
        wrongHipAngleUpMax = hipAngleUpMax
        wrongHipAngleDownMin = hipAngleDownMax
        wrongHipAngleDownMax = hipAngleDownMax + 80

        val leftHipAngle = Utilities.angle(leftShoulderPoint, leftHipPoint, leftKneePoint)

        val insideBox = isInsideBox(person, canvasHeight, canvasWidth)
        val wrongCountStates: Array<FloatArray> = arrayOf(
            floatArrayOf(
                wrongHipAngleUpMin,
                wrongHipAngleUpMax
            ),
            floatArrayOf(
                wrongHipAngleDownMin,
                wrongHipAngleDownMax
            ),
            floatArrayOf(
                wrongHipAngleUpMin,
                wrongHipAngleUpMax
            )
        )

        if (leftHipAngle > wrongCountStates[wrongStateIndex][0]
            && leftHipAngle < wrongCountStates[wrongStateIndex][1]
            && insideBox
        ) {
            wrongStateIndex += 1
            if (wrongStateIndex == wrongCountStates.size) {
                wrongStateIndex = 0
                wrongCount()
            }
        }
    }
}