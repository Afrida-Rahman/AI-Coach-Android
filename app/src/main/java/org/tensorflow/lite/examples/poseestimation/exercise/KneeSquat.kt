package org.tensorflow.lite.examples.poseestimation.exercise

import android.graphics.Color
import org.tensorflow.lite.examples.poseestimation.R
import org.tensorflow.lite.examples.poseestimation.core.AudioPlayer
import org.tensorflow.lite.examples.poseestimation.core.Point
import org.tensorflow.lite.examples.poseestimation.core.Utilities
import org.tensorflow.lite.examples.poseestimation.data.Person
import org.tensorflow.lite.examples.poseestimation.data.Rule
import org.tensorflow.lite.examples.poseestimation.data.RuleType

class KneeSquat(
    audioPlayer: AudioPlayer
) : Exercise("Knee Squat", "Simple knee squat", R.drawable.ic_knee_squat, audioPlayer) {
    private val hipAngleMin = 65f
    private val hipAngleMax = 180f

    private val kneeAngleMin = 65f
    private val kneeAngleMax = 180f

    private val totalStates = 3
    private var currentIndex = 0


    private val states: Array<FloatArray> = arrayOf(
        floatArrayOf(
            hipAngleMax - deltaValue,
            hipAngleMax + deltaValue,
            kneeAngleMax - deltaValue,
            kneeAngleMax + deltaValue
        ),
        floatArrayOf(
            hipAngleMin - deltaValue,
            hipAngleMin + deltaValue,
            kneeAngleMin - deltaValue,
            kneeAngleMin + deltaValue
        ),
        floatArrayOf(
            hipAngleMax - deltaValue,
            hipAngleMax + deltaValue,
            kneeAngleMax - deltaValue,
            kneeAngleMax + deltaValue
        )
    )

    override fun exerciseCount(person: Person) {
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
        val anklePoint = Point(
            person.keyPoints[15].coordinate.x,
            -person.keyPoints[15].coordinate.y
        )

        val hipAngle = Utilities().angle(shoulderPoint, hipPoint, kneePoint, true)
        val kneeAngle = Utilities().angle(hipPoint, kneePoint, anklePoint)
        if (
            hipAngle > states[currentIndex][0] && hipAngle < states[currentIndex][1] &&
            kneeAngle > states[currentIndex][2] && kneeAngle < states[currentIndex][3]
        ) {
            currentIndex += 1
            if (currentIndex == totalStates) {
                currentIndex = 0
                repetitionCount()
            }
        }
    }

    override fun wrongExerciseCount(person: Person) {
        TODO("Not yet implemented")
    }

    override fun getBorderColor(person: Person, canvasHeight: Int, canvasWidth: Int): Int {
        return Color.GREEN
    }

    override fun drawingRules(person: Person): List<Rule> {
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
        val anklePoint = Point(
            person.keyPoints[15].coordinate.x,
            person.keyPoints[15].coordinate.y
        )
        return mutableListOf(
            Rule(
                type = RuleType.ANGLE,
                startPoint = shoulderPoint,
                middlePoint1 = hipPoint,
                endPoint = kneePoint,
                clockWise = true
            ),
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