package org.tensorflow.lite.examples.poseestimation.exercise

import org.tensorflow.lite.examples.poseestimation.R
import org.tensorflow.lite.examples.poseestimation.core.AudioPlayer
import org.tensorflow.lite.examples.poseestimation.core.Point
import org.tensorflow.lite.examples.poseestimation.core.Utilities
import org.tensorflow.lite.examples.poseestimation.data.Person
import org.tensorflow.lite.examples.poseestimation.data.Rule
import org.tensorflow.lite.examples.poseestimation.data.RuleType

class SeatedKneeExtension(
    audioPlayer: AudioPlayer
) : Exercise(
    "Seated Knee Extension",
    "Simple Seated Knee Extension",
    R.drawable.ic_seated_knee_extension,
    audioPlayer
) {
    private val kneeAngleMin = 90f
    private val kneeAngleMax = 165f

    private val totalStates = 2
    private var currentIndex = 0

    private val states: Array<FloatArray> = arrayOf(
        floatArrayOf(
            kneeAngleMin - deltaValue,
            kneeAngleMin + deltaValue
        ),
        floatArrayOf(
            kneeAngleMax - deltaValue,
            kneeAngleMax + deltaValue
        )
    )

    override fun exerciseCount(person: Person) {
        val hipPoint = Point(
            person.keyPoints[12].coordinate.x,
            -person.keyPoints[12].coordinate.y
        )
        val kneePoint = Point(
            person.keyPoints[14].coordinate.x,
            -person.keyPoints[14].coordinate.y
        )
        val anklePoint = Point(
            person.keyPoints[16].coordinate.x,
            -person.keyPoints[16].coordinate.y
        )
        val kneeAngle = Utilities().angle(hipPoint, kneePoint, anklePoint, false)
        if (kneeAngle > states[currentIndex][0] && kneeAngle < states[currentIndex][1]) {
            currentIndex += 1
            if (currentIndex == totalStates) {
                currentIndex = 0
                repetitionCount()
            }
        }
    }

    override fun drawingRules(person: Person): List<Rule> {
        val hipPoint = Point(
            person.keyPoints[12].coordinate.x,
            person.keyPoints[12].coordinate.y
        )
        val kneePoint = Point(
            person.keyPoints[14].coordinate.x,
            person.keyPoints[14].coordinate.y
        )
        val anklePoint = Point(
            person.keyPoints[16].coordinate.x,
            person.keyPoints[16].coordinate.y
        )
        return mutableListOf(
            Rule(
                type = RuleType.ANGLE,
                startPoint = hipPoint,
                middlePoint1 = kneePoint,
                endPoint = anklePoint,
                clockWise = false
            )
        )
    }
}
