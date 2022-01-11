package org.tensorflow.lite.examples.poseestimation.exercise.home.back

import android.content.Context
import org.tensorflow.lite.examples.poseestimation.core.Point
import org.tensorflow.lite.examples.poseestimation.core.Utilities
import org.tensorflow.lite.examples.poseestimation.core.VisualizationUtils
import org.tensorflow.lite.examples.poseestimation.domain.model.Person
import org.tensorflow.lite.examples.poseestimation.exercise.home.HomeExercise

class LateralBendingStretchInStanding(
    context: Context
) : HomeExercise(
    context = context,
    id = 156
) {
    private var shoulderAngleDownMin = 0f
    private var shoulderAngleDownMax = 30f
    private var shoulderAngleUpMin = 180f
    private var shoulderAngleUpMax = 210f

    private val deviationAngleDownMin = 0f
    private val deviationAngleUpMin = 25f

    private var wrongShoulderAngleDownMin = 0f
    private var wrongShoulderAngleDownMax = 30f
    private var wrongShoulderAngleUpMin = 100f
    private var wrongShoulderAngleUpMax = 180f

    private val wrongDeviationAngleDownMin = 0f
    private val wrongDeviationAngleUpMin = 25f

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
        canvasWidth: Int
    ) {
        val leftWristPoint = Point(
            person.keyPoints[9].coordinate.x,
            -person.keyPoints[9].coordinate.y
        )
        val rightWristPoint = Point(
            person.keyPoints[10].coordinate.x,
            -person.keyPoints[10].coordinate.y
        )
        val leftElbowPoint = Point(
            person.keyPoints[7].coordinate.x,
            -person.keyPoints[7].coordinate.y
        )
        val rightElbowPoint = Point(
            person.keyPoints[8].coordinate.x,
            -person.keyPoints[8].coordinate.y
        )
        val leftShoulderPoint = Point(
            person.keyPoints[5].coordinate.x,
            -person.keyPoints[5].coordinate.y
        )
        val rightShoulderPoint = Point(
            person.keyPoints[6].coordinate.x,
            -person.keyPoints[6].coordinate.y
        )
        val leftHipPoint = Point(
            person.keyPoints[11].coordinate.x,
            -person.keyPoints[11].coordinate.y
        )
        val rightHipPoint = Point(
            person.keyPoints[12].coordinate.x,
            -person.keyPoints[12].coordinate.y
        )
        val shoulderMidPoint = Point(
            (leftShoulderPoint.x + rightShoulderPoint.x) / 2,
            -(rightShoulderPoint.y + rightShoulderPoint.y) / 2
        )
        val hipMidPoint = Point(
            (leftHipPoint.x + rightHipPoint.x) / 2,
            -(rightHipPoint.y + rightHipPoint.y) / 2
        )
        val shoulderDeviationPoint = Point(
            (leftHipPoint.x + rightHipPoint.x) / 2,
            -(rightShoulderPoint.y + rightShoulderPoint.y) / 2
        )

        if (rightCountPhases.size >= 2) {
            shoulderAngleDownMin = rightCountPhases[0].constraints[0].minValue.toFloat()
            shoulderAngleDownMax = rightCountPhases[0].constraints[0].maxValue.toFloat()
            shoulderAngleUpMin = rightCountPhases[1].constraints[0].minValue.toFloat()
            shoulderAngleUpMax = rightCountPhases[1].constraints[0].maxValue.toFloat()
        }

        val leftShoulderAngle =
            Utilities.angle(leftElbowPoint, leftShoulderPoint, leftHipPoint, false)

        val deviationAngle = Utilities.angle(shoulderMidPoint, hipMidPoint, shoulderDeviationPoint)
        val shoulderDeviationAngle = if (deviationAngle > 90) {
            360 - deviationAngle
        } else {
            deviationAngle
        }

        val leftStraightHandAngle =
            Utilities.angle(leftShoulderPoint, leftElbowPoint, leftWristPoint, true)
        val rightStraightHandAngle =
            Utilities.angle(rightShoulderPoint, rightElbowPoint, rightWristPoint, false)

        val rightHandStraight =
            rightStraightHandAngle > straightHandAngleMin && rightStraightHandAngle < straightHandAngleMax
        val leftHandStraight =
            leftStraightHandAngle > straightHandAngleMin && leftStraightHandAngle < straightHandAngleMax

        val insideBox = VisualizationUtils.isInsideBox(person, canvasHeight, canvasWidth)
        val rightCountStates: Array<FloatArray> = arrayOf(
            floatArrayOf(
                shoulderAngleDownMin,
                shoulderAngleDownMax,
                deviationAngleDownMin
            ),
            floatArrayOf(
                shoulderAngleUpMin,
                shoulderAngleUpMax,
                deviationAngleUpMin
            ),
            floatArrayOf(
                shoulderAngleDownMin,
                shoulderAngleDownMax,
                deviationAngleDownMin
            )
        )

        if (leftShoulderAngle > rightCountStates[rightStateIndex][0]
            && leftShoulderAngle < rightCountStates[rightStateIndex][1]
            && shoulderDeviationAngle > rightCountStates[rightStateIndex][2]
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
                onEvent(CommonInstructionEvent.OutSideOfBox)
            } else {
                if (!rightHandStraight && !leftHandStraight) {
                    wrongFrameCount++
                    if (wrongFrameCount >= maxWrongCountFrame) {
                        onEvent(CommonInstructionEvent.HandIsNotStraight)
                        wrongFrameCount = 0
                    }
                } else if (!rightHandStraight) {
                    wrongFrameCount++
                    if (wrongFrameCount >= maxWrongCountFrame) {
                        onEvent(CommonInstructionEvent.RightHandIsNotStraight)
                        wrongFrameCount = 0
                    }
                } else if (!leftHandStraight) {
                    wrongFrameCount++
                    if (wrongFrameCount >= maxWrongCountFrame) {
                        onEvent(CommonInstructionEvent.LeftHandIsNotStraight)
                        wrongFrameCount = 0
                    }
                }
            }
        }
    }

    override fun wrongExerciseCount(person: Person, canvasHeight: Int, canvasWidth: Int) {
        val leftElbowPoint = Point(
            person.keyPoints[7].coordinate.x,
            -person.keyPoints[7].coordinate.y
        )
        val leftShoulderPoint = Point(
            person.keyPoints[5].coordinate.x,
            -person.keyPoints[5].coordinate.y
        )
        val rightShoulderPoint = Point(
            person.keyPoints[6].coordinate.x,
            -person.keyPoints[6].coordinate.y
        )
        val leftHipPoint = Point(
            person.keyPoints[11].coordinate.x,
            -person.keyPoints[11].coordinate.y
        )
        val rightHipPoint = Point(
            person.keyPoints[12].coordinate.x,
            -person.keyPoints[12].coordinate.y
        )
        val shoulderMidPoint = Point(
            (leftShoulderPoint.x + rightShoulderPoint.x) / 2,
            -(rightShoulderPoint.y + rightShoulderPoint.y) / 2
        )
        val hipMidPoint = Point(
            (leftHipPoint.x + rightHipPoint.x) / 2,
            -(rightHipPoint.y + rightHipPoint.y) / 2
        )
        val shoulderDeviationPoint = Point(
            (leftHipPoint.x + rightHipPoint.x) / 2,
            -(rightShoulderPoint.y + rightShoulderPoint.y) / 2
        )

        val wrongCountStates: Array<FloatArray> = arrayOf(
            floatArrayOf(
                wrongShoulderAngleDownMin,
                wrongShoulderAngleDownMax,
                wrongDeviationAngleDownMin
            ),
            floatArrayOf(
                wrongShoulderAngleUpMin,
                wrongShoulderAngleUpMax,
                wrongDeviationAngleUpMin
            ),
            floatArrayOf(
                wrongShoulderAngleDownMin,
                wrongShoulderAngleDownMax,
                wrongDeviationAngleDownMin
            )
        )

        wrongShoulderAngleDownMin = shoulderAngleDownMin
        wrongShoulderAngleDownMax = shoulderAngleDownMax
        wrongShoulderAngleUpMin = shoulderAngleUpMin - 80
        wrongShoulderAngleUpMax = shoulderAngleUpMin

        val leftShoulderAngle =
            Utilities.angle(leftElbowPoint, leftShoulderPoint, leftHipPoint, false)

        val deviationAngle = Utilities.angle(shoulderMidPoint, hipMidPoint, shoulderDeviationPoint)
        val shoulderDeviationAngle = if (deviationAngle > 90) {
            360 - deviationAngle
        } else {
            deviationAngle
        }

        val insideBox = VisualizationUtils.isInsideBox(person, canvasHeight, canvasWidth)

        if (((leftShoulderAngle > wrongCountStates[wrongStateIndex][0]
                    && leftShoulderAngle < wrongCountStates[wrongStateIndex][1]
                    && shoulderDeviationAngle < wrongCountStates[wrongStateIndex][2])
                    || (leftShoulderAngle > wrongCountStates[wrongStateIndex][0]
                    && leftShoulderAngle < wrongCountStates[wrongStateIndex][1]))
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