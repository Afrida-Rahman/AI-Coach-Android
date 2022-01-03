package org.tensorflow.lite.examples.poseestimation.exercise

import android.content.Context
import org.tensorflow.lite.examples.poseestimation.domain.model.Person

class HamstringCurlsInProne(
    context: Context
) : IExercise(context = context, id = 255) {
    override fun wrongExerciseCount(person: Person, canvasHeight: Int, canvasWidth: Int) {
    }
}