package org.tensorflow.lite.examples.poseestimation.exercise

import android.content.Context
import org.tensorflow.lite.examples.poseestimation.domain.model.Person

class ScapularStabilizationStabilityBallBothHands(
    context: Context
) : IExercise(context = context, id = 210) {
    override fun wrongExerciseCount(person: Person, canvasHeight: Int, canvasWidth: Int) {
        TODO("Not yet implemented")
    }
}