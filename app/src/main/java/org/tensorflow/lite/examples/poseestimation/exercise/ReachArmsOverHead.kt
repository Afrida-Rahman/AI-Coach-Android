package org.tensorflow.lite.examples.poseestimation.exercise

import android.content.Context
import org.tensorflow.lite.examples.poseestimation.core.Point
import org.tensorflow.lite.examples.poseestimation.core.Utilities
import org.tensorflow.lite.examples.poseestimation.domain.model.Person
import org.tensorflow.lite.examples.poseestimation.domain.model.Phase

class ReachArmsOverHead(
    context: Context
) : IExercise(
    context = context,
    id = 347
) {
    private var shoulderAngleDownMin = 0f
    private var shoulderAngleDownMax = 30f
    private var shoulderAngleUpMin = 150f
    private var shoulderAngleUpMax = 190f

    private var wrongShoulderAngleDownMin = 0f
    private var wrongShoulderAngleDownMax = 30f
    private var wrongShoulderAngleUpMin = 120f
    private var wrongShoulderAngleUpMax = 150f

    private val straightHandAngleMin = 150f
    private val straightHandAngleMax = 210f

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
            -person.keyPoints[5].coordinate.y
        )
        val rightShoulderPoint = Point(
            person.keyPoints[6].coordinate.x,
            -person.keyPoints[6].coordinate.y
        )
        val leftWristPoint = Point(
            person.keyPoints[9].coordinate.x,
            -person.keyPoints[9].coordinate.y
        )
        val rightWristPoint = Point(
            person.keyPoints[10].coordinate.x,
            -person.keyPoints[10].coordinate.y
        )
        val leftHipPoint = Point(
            person.keyPoints[11].coordinate.x,
            -person.keyPoints[11].coordinate.y
        )
        val rightHipPoint = Point(
            person.keyPoints[12].coordinate.x,
            -person.keyPoints[12].coordinate.y
        )
        val leftElbowPoint = Point(
            person.keyPoints[7].coordinate.x,
            -person.keyPoints[7].coordinate.y
        )
        val rightElbowPoint = Point(
            person.keyPoints[8].coordinate.x,
            -person.keyPoints[8].coordinate.y
        )
        if (phases.size >= 2) {
            shoulderAngleDownMin = phases[0].constraints[0].minValue.toFloat()
            shoulderAngleDownMax = phases[0].constraints[0].maxValue.toFloat()
            shoulderAngleUpMin = phases[1].constraints[0].minValue.toFloat()
            shoulderAngleUpMax = phases[1].constraints[0].maxValue.toFloat()
        } else {
            shoulderAngleDownMin = 0f
            shoulderAngleDownMax = 30f
            shoulderAngleUpMin = 150f
            shoulderAngleUpMax = 195f
        }

        val leftShoulderAngle =
            Utilities.angle(leftElbowPoint, leftShoulderPoint, leftHipPoint, false)
        val rightShoulderAngle =
            Utilities.angle(rightElbowPoint, rightShoulderPoint, rightHipPoint, true)
        val leftStraightHandAngle =
            Utilities.angle(leftShoulderPoint, leftElbowPoint, leftWristPoint, true)
        val rightStraightHandAngle =
            Utilities.angle(rightShoulderPoint, rightElbowPoint, rightWristPoint, false)

        val rightHandStraight =
            rightStraightHandAngle > straightHandAngleMin && rightStraightHandAngle < straightHandAngleMax
        val leftHandStraight =
            leftStraightHandAngle > straightHandAngleMin && leftStraightHandAngle < straightHandAngleMax
        val insideBox = isInsideBox(person, canvasHeight, canvasWidth)
        val rightCountStates: Array<FloatArray> = arrayOf(
            floatArrayOf(
                shoulderAngleDownMin,
                shoulderAngleDownMax,
                shoulderAngleDownMin,
                shoulderAngleDownMax
            ),
            floatArrayOf(
                shoulderAngleUpMin,
                shoulderAngleUpMax,
                shoulderAngleUpMin,
                shoulderAngleUpMax
            ),
            floatArrayOf(
                shoulderAngleDownMin,
                shoulderAngleDownMax,
                shoulderAngleDownMin,
                shoulderAngleDownMax
            )
        )
        if (
            leftShoulderAngle > rightCountStates[rightStateIndex][0] && leftShoulderAngle < rightCountStates[rightStateIndex][1] &&
            rightShoulderAngle > rightCountStates[rightStateIndex][2] && rightShoulderAngle < rightCountStates[rightStateIndex][3] &&
            rightHandStraight && leftHandStraight && insideBox
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
            } else {
                if (!rightHandStraight && !leftHandStraight) {
                    wrongFrameCount++
                    if (wrongFrameCount >= maxWrongCountFrame) {
                        handNotStraight()
                        wrongFrameCount = 0
                    }
                } else if (!rightHandStraight) {
                    wrongFrameCount++
                    if (wrongFrameCount >= maxWrongCountFrame) {
                        rightHandNotStraight()
                        wrongFrameCount = 0
                    }
                } else if (!leftHandStraight) {
                    wrongFrameCount++
                    if (wrongFrameCount >= maxWrongCountFrame) {
                        leftHandNotStraight()
                        wrongFrameCount = 0
                    }
                }
            }
        }
    }

    override fun wrongExerciseCount(person: Person, canvasHeight: Int, canvasWidth: Int) {
        val leftShoulderPoint = Point(
            person.keyPoints[5].coordinate.x,
            -person.keyPoints[5].coordinate.y
        )
        val rightShoulderPoint = Point(
            person.keyPoints[6].coordinate.x,
            -person.keyPoints[6].coordinate.y
        )
        val leftWristPoint = Point(
            person.keyPoints[9].coordinate.x,
            -person.keyPoints[9].coordinate.y
        )
        val rightWristPoint = Point(
            person.keyPoints[10].coordinate.x,
            -person.keyPoints[10].coordinate.y
        )
        val leftHipPoint = Point(
            person.keyPoints[11].coordinate.x,
            -person.keyPoints[11].coordinate.y
        )
        val rightHipPoint = Point(
            person.keyPoints[12].coordinate.x,
            -person.keyPoints[12].coordinate.y
        )

        wrongShoulderAngleDownMin = shoulderAngleDownMin
        wrongShoulderAngleDownMax = shoulderAngleDownMax
        wrongShoulderAngleUpMin = shoulderAngleUpMin - 40
        wrongShoulderAngleUpMax = shoulderAngleUpMax - 40

        val wrongCountStates: Array<FloatArray> = arrayOf(
            floatArrayOf(
                wrongShoulderAngleDownMin,
                wrongShoulderAngleDownMax,
                wrongShoulderAngleDownMin,
                wrongShoulderAngleDownMax
            ),
            floatArrayOf(
                wrongShoulderAngleUpMin,
                wrongShoulderAngleUpMax,
                wrongShoulderAngleUpMin,
                wrongShoulderAngleUpMax
            ),
            floatArrayOf(
                wrongShoulderAngleDownMin,
                wrongShoulderAngleDownMax,
                wrongShoulderAngleDownMin,
                wrongShoulderAngleDownMax
            )
        )
        val leftShoulderAngle = Utilities.angle(leftWristPoint, leftShoulderPoint, leftHipPoint)
        val rightShoulderAngle =
            Utilities.angle(rightWristPoint, rightShoulderPoint, rightHipPoint, true)
        val insideBox = isInsideBox(person, canvasHeight, canvasWidth)
        if (
            leftShoulderAngle > wrongCountStates[wrongStateIndex][0] && leftShoulderAngle < wrongCountStates[wrongStateIndex][1] &&
            rightShoulderAngle > wrongCountStates[wrongStateIndex][2] && rightShoulderAngle < wrongCountStates[wrongStateIndex][3]
            && insideBox
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
}
