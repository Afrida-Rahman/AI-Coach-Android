package org.tensorflow.lite.examples.poseestimation.exercise

import android.content.Context
import android.graphics.Color
import org.tensorflow.lite.examples.poseestimation.R
import org.tensorflow.lite.examples.poseestimation.core.Point
import org.tensorflow.lite.examples.poseestimation.core.Utilities
import org.tensorflow.lite.examples.poseestimation.domain.model.Person
import org.tensorflow.lite.examples.poseestimation.domain.model.Rule
import org.tensorflow.lite.examples.poseestimation.domain.model.RuleType

class SeatedKneeExtension(
    context: Context
) : IExercise(
    context,
    3,
    "Seated Knee Extension",
    "Simple Seated Knee Extension",
    R.drawable.seated_legs_riase
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
        val kneeAngle = Utilities.angle(hipPoint, kneePoint, anklePoint, false)
        if (kneeAngle > states[currentIndex][0] && kneeAngle < states[currentIndex][1]) {
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
                middlePoint = kneePoint,
                endPoint = anklePoint,
                clockWise = false
            )
        )
    }
}
