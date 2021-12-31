package org.tensorflow.lite.examples.poseestimation.exercise

import android.content.Context
import org.tensorflow.lite.examples.poseestimation.domain.model.Person

class IsometricShoulderAdductionInSitting(
    context: Context
) : IExercise(context = context, id = 526) {
    override fun wrongExerciseCount(person: Person, canvasHeight: Int, canvasWidth: Int) {
        TODO("Not yet implemented")
    }
}