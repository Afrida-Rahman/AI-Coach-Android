package org.tensorflow.lite.examples.poseestimation.exercise

import android.graphics.Color
import org.tensorflow.lite.examples.poseestimation.MainActivity
import org.tensorflow.lite.examples.poseestimation.R
import org.tensorflow.lite.examples.poseestimation.core.AudioPlayer
import org.tensorflow.lite.examples.poseestimation.core.Point
import org.tensorflow.lite.examples.poseestimation.core.Utilities
import org.tensorflow.lite.examples.poseestimation.data.Person
import org.tensorflow.lite.examples.poseestimation.data.Rule
import org.tensorflow.lite.examples.poseestimation.data.RuleType

class ReachArmsOverHand(
    audioPlayer: AudioPlayer
) : Exercise(
    "Reach Arms Over Head",
    "Simple Reach Arms Over Head",
    R.drawable.ic_reach_arms_over_head,
    audioPlayer
) {
    private var shoulderAngleDownMin = 0f
    private var shoulderAngleDownMax = 10f
    private var shoulderAngleUpMin = 165f

    private var shoulderAngleUpMax = 195f
    private val straightHandAngleMin = 150f

    private val straightHandAngleMax = 210f
    private val totalStates = 3

    private var rightStateIndex = 0
    private var wrongStateIndex = 0
    private var wrongFrameCount = 0
    private val maxWrongCountFrame = 3
    private var receivedResponse = MainActivity.keyPointsRestrictionGroup?.sortedBy { it.Phase }
    private val wrongCountStates: Array<FloatArray> = arrayOf(
        floatArrayOf(
            0f, 30f,
            0f, 30f
        ),
        floatArrayOf(
            120f, 150f,
            120f, 150f
        ),
        floatArrayOf(
            0f, 30f,
            0f, 30f
        )
    )

    override fun exerciseCount(person: Person) {
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
        if (receivedResponse != null) {
            shoulderAngleDownMin =
                receivedResponse!![0].KeyPointsRestriction[0].MinValidationValue.toFloat()
            shoulderAngleDownMax =
                receivedResponse!![0].KeyPointsRestriction[0].MaxValidationValue.toFloat()
            shoulderAngleUpMin =
                receivedResponse!![1].KeyPointsRestriction[0].MinValidationValue.toFloat()
            shoulderAngleUpMax =
                receivedResponse!![1].KeyPointsRestriction[0].MaxValidationValue.toFloat()
        } else {
            shoulderAngleDownMin = 0f
            shoulderAngleDownMax = 30f
            shoulderAngleUpMin = 165f
            shoulderAngleUpMax = 195f
        }

        val leftShoulderAngle =
            Utilities().angle(leftElbowPoint, leftShoulderPoint, leftHipPoint, false)
        val rightShoulderAngle =
            Utilities().angle(rightElbowPoint, rightShoulderPoint, rightHipPoint, true)
        val leftStraightHandAngle =
            Utilities().angle(leftShoulderPoint, leftElbowPoint, leftWristPoint, true)
        val rightStraightHandAngle =
            Utilities().angle(rightShoulderPoint, rightElbowPoint, rightWristPoint, false)
        val isHandStraight =
            leftStraightHandAngle > straightHandAngleMin && leftStraightHandAngle < straightHandAngleMax &&
                    rightStraightHandAngle > straightHandAngleMin && rightStraightHandAngle < straightHandAngleMax
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
            isHandStraight
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
            if (!isHandStraight) {
                wrongFrameCount++
                if (wrongFrameCount >= maxWrongCountFrame) {
                    handNotStraight()
                    wrongFrameCount = 0
                }
            }
        }
    }

    override fun wrongExerciseCount(person: Person) {
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
        val leftShoulderAngle = Utilities().angle(leftWristPoint, leftShoulderPoint, leftHipPoint)
        val rightShoulderAngle =
            Utilities().angle(rightWristPoint, rightShoulderPoint, rightHipPoint, true)
        if (
            leftShoulderAngle > wrongCountStates[wrongStateIndex][0] && leftShoulderAngle < wrongCountStates[wrongStateIndex][1] &&
            rightShoulderAngle > wrongCountStates[wrongStateIndex][2] && rightShoulderAngle < wrongCountStates[wrongStateIndex][3]
        ) {
            wrongStateIndex += 1
            if (wrongStateIndex == wrongCountStates.size) {
                wrongStateIndex = 0
                wrongCount()
            }
        }
    }

    override fun drawingRules(person: Person): List<Rule> {
        val leftShoulderPoint = Point(
            person.keyPoints[5].coordinate.x,
            person.keyPoints[5].coordinate.y
        )
        val rightShoulderPoint = Point(
            person.keyPoints[6].coordinate.x,
            person.keyPoints[6].coordinate.y
        )
        val leftElbowPoint = Point(
            person.keyPoints[7].coordinate.x,
            person.keyPoints[7].coordinate.y
        )
        val rightElbowPoint = Point(
            person.keyPoints[8].coordinate.x,
            person.keyPoints[8].coordinate.y
        )
        val leftHipPoint = Point(
            person.keyPoints[11].coordinate.x,
            person.keyPoints[11].coordinate.y
        )
        val rightHipPoint = Point(
            person.keyPoints[12].coordinate.x,
            person.keyPoints[12].coordinate.y
        )
        val leftWristPoint = Point(
            person.keyPoints[9].coordinate.x,
            person.keyPoints[9].coordinate.y
        )
        val rightWristPoint = Point(
            person.keyPoints[10].coordinate.x,
            person.keyPoints[10].coordinate.y
        )

        val leftStraightHandAngle =
            Utilities().angle(leftShoulderPoint, leftElbowPoint, leftWristPoint, true)
        val rightStraightHandAngle =
            Utilities().angle(rightShoulderPoint, rightElbowPoint, rightWristPoint, false)

        val rules = mutableListOf(
            Rule(
                type = RuleType.ANGLE,
                startPoint = leftElbowPoint,
                middlePoint1 = leftShoulderPoint,
                endPoint = leftHipPoint,
                clockWise = false
            ),
            Rule(
                type = RuleType.ANGLE,
                startPoint = rightElbowPoint,
                middlePoint1 = rightShoulderPoint,
                endPoint = rightHipPoint,
                clockWise = true
            )
        )

        val isLeftHandStraight =
            leftStraightHandAngle > straightHandAngleMin && leftStraightHandAngle < straightHandAngleMax
        val isRightHandStraight =
            rightStraightHandAngle > straightHandAngleMin && rightStraightHandAngle < straightHandAngleMax

        if (!isLeftHandStraight) {
            rules.add(
                Rule(
                    type = RuleType.LINE,
                    startPoint = leftElbowPoint,
                    endPoint = leftWristPoint,
                    color = Color.RED
                )
            )
        }
        if (!isRightHandStraight) {
            rules.add(
                Rule(
                    type = RuleType.LINE,
                    startPoint = rightElbowPoint,
                    endPoint = rightWristPoint,
                    color = Color.RED
                )
            )
        }
        return rules
    }

    override fun getBorderColor(person: Person, canvasHeight: Int, canvasWidth: Int): Int {
        val left = canvasWidth * 4f / 20f
        val right = canvasWidth * 16f / 20f
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
        return if (rightPosition) {
            Color.GREEN
        } else {
            Color.RED
        }
    }
}
