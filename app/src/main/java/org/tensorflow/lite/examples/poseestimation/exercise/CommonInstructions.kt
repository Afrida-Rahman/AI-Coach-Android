package org.tensorflow.lite.examples.poseestimation.exercise

import org.tensorflow.lite.examples.poseestimation.core.Utilities
import org.tensorflow.lite.examples.poseestimation.domain.model.Constraint
import org.tensorflow.lite.examples.poseestimation.domain.model.Person
import org.tensorflow.lite.examples.poseestimation.shared.PoseIndex

object CommonInstructions {

    private const val LEFT_ELBOW_MIN = 150f
    private const val LEFT_ELBOW_MAX = 190f
    private const val RIGHT_ELBOW_MIN = 150f
    private const val RIGHT_ELBOW_MAX = 190f

    private val LEFT_HAND_KEY_POINTS = setOf(
        PoseIndex.LEFT_WRIST,
        PoseIndex.LEFT_ELBOW,
        PoseIndex.LEFT_SHOULDER
    )
    private val RIGHT_HAND_KEY_POINTS = setOf(
        PoseIndex.RIGHT_WRIST,
        PoseIndex.RIGHT_ELBOW,
        PoseIndex.RIGHT_SHOULDER
    )

    fun isBothHandStraight(person: Person, constraints: List<Constraint>): Boolean {
        return true
    }

    fun isLeftHandStraight(person: Person, constraint: Constraint): Boolean {
        val keyPoints = setOf(
            constraint.startPointIndex,
            constraint.middlePointIndex,
            constraint.endPointIndex
        )
        if (keyPoints == LEFT_HAND_KEY_POINTS) {
            if (constraint.minValue >= LEFT_ELBOW_MIN && constraint.maxValue <= LEFT_ELBOW_MAX) {
                val angle = Utilities.angle(
                    startPoint = person.keyPoints[constraint.startPointIndex].toRealPoint(),
                    middlePoint = person.keyPoints[constraint.middlePointIndex].toRealPoint(),
                    endPoint = person.keyPoints[constraint.endPointIndex].toRealPoint(),
                    clockWise = constraint.clockWise
                )
                if (angle < constraint.minValue || angle > constraint.maxValue) {
                    return false
                }
            }
        }
        return true
    }

    fun isRightHandStraight(person: Person, constraint: Constraint): Boolean {
        val keyPoints = setOf(
            constraint.startPointIndex,
            constraint.middlePointIndex,
            constraint.endPointIndex
        )
        if (keyPoints == RIGHT_HAND_KEY_POINTS) {
            if (constraint.minValue >= RIGHT_ELBOW_MIN && constraint.maxValue <= RIGHT_ELBOW_MAX) {
                val angle = Utilities.angle(
                    startPoint = person.keyPoints[constraint.startPointIndex].toRealPoint(),
                    middlePoint = person.keyPoints[constraint.middlePointIndex].toRealPoint(),
                    endPoint = person.keyPoints[constraint.endPointIndex].toRealPoint(),
                    clockWise = constraint.clockWise
                )
                if (angle < constraint.minValue || angle > constraint.maxValue) {
                    return false
                }
            }
        }
        return true
    }
}