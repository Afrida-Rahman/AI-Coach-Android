package org.tensorflow.lite.examples.poseestimation.exercise

import android.content.Context
import org.tensorflow.lite.examples.poseestimation.domain.model.Person

class GeneralExercise(
    context: Context,
    exerciseId: Int,
    active: Boolean = false
) : IExercise(
    context = context,
    id = exerciseId,
    active = active
) {
    override fun wrongExerciseCount(person: Person, canvasHeight: Int, canvasWidth: Int) {
        TODO("Not yet implemented")
    }
}