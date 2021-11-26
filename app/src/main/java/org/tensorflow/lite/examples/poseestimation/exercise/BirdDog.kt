package org.tensorflow.lite.examples.poseestimation.exercise

import android.content.Context
import org.tensorflow.lite.examples.poseestimation.core.Point
import org.tensorflow.lite.examples.poseestimation.domain.model.Person
import org.tensorflow.lite.examples.poseestimation.domain.model.Phase
import org.tensorflow.lite.examples.poseestimation.domain.model.Rule
import org.tensorflow.lite.examples.poseestimation.domain.model.RuleType

class BirdDog(
    context: Context
) : IExercise(
    context = context,
    id = 332
) {
    override fun exerciseCount(
        person: Person,
        canvasHeight: Int,
        canvasWidth: Int,
        phases: List<Phase>
    ) {

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
        val rightHipPoint = Point(
            person.keyPoints[12].coordinate.x,
            person.keyPoints[12].coordinate.y
        )
        val leftKneePoint = Point(
            person.keyPoints[13].coordinate.x,
            person.keyPoints[13].coordinate.y
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
                endPoint = leftHipPoint
            ),
            Rule(
                type = RuleType.ANGLE,
                startPoint = leftShoulderPoint,
                middlePoint = leftHipPoint,
                endPoint = leftKneePoint,
                clockWise = true
            ),
            Rule(
                type = RuleType.ANGLE,
                startPoint = leftHipPoint,
                middlePoint = rightKneePoint,
                endPoint = rightAnklePoint
            )
        )
    }
}