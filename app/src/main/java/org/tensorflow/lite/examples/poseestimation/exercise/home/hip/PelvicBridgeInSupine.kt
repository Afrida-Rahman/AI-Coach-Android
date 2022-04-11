package org.tensorflow.lite.examples.poseestimation.exercise.home.hip

import android.content.Context
import org.tensorflow.lite.examples.poseestimation.R
import org.tensorflow.lite.examples.poseestimation.core.Utilities
import org.tensorflow.lite.examples.poseestimation.domain.model.BodyPart
import org.tensorflow.lite.examples.poseestimation.domain.model.Person
import org.tensorflow.lite.examples.poseestimation.exercise.home.HomeExercise

class PelvicBridgeInSupine(
    context: Context
) : HomeExercise(
    context = context, id = 122
) {
    override fun exerciseInstruction(person: Person) {
        this.getPhase()?.let {
            if (it.phaseNumber == 2) {
                it.constraints.forEach { constraint ->
                    if (constraint.startPointIndex == BodyPart.LEFT_SHOULDER.position && constraint.middlePointIndex == BodyPart.LEFT_HIP.position && constraint.endPointIndex == BodyPart.LEFT_KNEE.position) {
                        val angle = Utilities.angle(
                            startPoint = person.keyPoints[constraint.startPointIndex].toRealPoint(),
                            middlePoint = person.keyPoints[constraint.middlePointIndex].toRealPoint(),
                            endPoint = person.keyPoints[constraint.endPointIndex].toRealPoint(),
                            clockWise = constraint.clockWise
                        )
                        if (angle < constraint.minValue) {
                            this.playAudio(R.raw.raise_your_hip)
                        }
                    }
                }
            }
        }
    }
}