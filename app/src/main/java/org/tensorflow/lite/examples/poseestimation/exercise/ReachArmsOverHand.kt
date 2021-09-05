package org.tensorflow.lite.examples.poseestimation.exercise

import android.graphics.Color
import android.util.Log
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
    var receivedResponse = MainActivity.keyPointsRestriction
    private var leftShoulderAngleMin = 15f
    private val leftShoulderAngleMax = 180f

    private val rightShoulderAngleMin = 15f
    private val rightShoulderAngleMax = 180f

    private val straightHandAngleMin = 150f
    private val straightHandAngleMax = 210f

    private val totalStates = 3
    private var currentIndex = 0
    private var wrongFrameCount = 0
    private val maxWrongCountFrame = 3

    private val states: Array<FloatArray> = arrayOf(
        floatArrayOf(
            leftShoulderAngleMin - deltaValue,
            leftShoulderAngleMin + deltaValue,
            rightShoulderAngleMin - deltaValue,
            rightShoulderAngleMin + deltaValue
        ),
        floatArrayOf(
            leftShoulderAngleMax - deltaValue,
            leftShoulderAngleMax + deltaValue,
            rightShoulderAngleMax - deltaValue,
            rightShoulderAngleMax + deltaValue
        ),
        floatArrayOf(
            leftShoulderAngleMin - deltaValue,
            leftShoulderAngleMin + deltaValue,
            rightShoulderAngleMin - deltaValue,
            rightShoulderAngleMin + deltaValue
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
        if (
            leftShoulderAngle > states[currentIndex][0] && leftShoulderAngle < states[currentIndex][1] &&
            rightShoulderAngle > states[currentIndex][2] && rightShoulderAngle < states[currentIndex][3] &&
            isHandStraight
        ) {
            currentIndex += 1
            if (currentIndex == totalStates) {
                currentIndex = 0
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
}

//leftShoulderAngle > states[0][2] && leftShoulderAngle < states[0][3]) || (leftShoulderAngle > states[1][2] && leftShoulderAngle < states[1][3]) ||
//rightShoulderAngle > states[0][2] && rightShoulderAngle < states[0][3]) || (rightShoulderAngle > states[1][2] && rightShoulderAngle < states[1][3])
