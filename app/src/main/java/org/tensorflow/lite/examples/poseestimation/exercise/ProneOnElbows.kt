package org.tensorflow.lite.examples.poseestimation.exercise

import android.content.Context
import org.tensorflow.lite.examples.poseestimation.domain.model.Person

class ProneOnElbows(
    context: Context
) : IExercise(
    context = context,
    id = 167
) {
    override var wrongStateIndex = 0
    override fun wrongExerciseCount(person: Person, canvasHeight: Int, canvasWidth: Int) {}
}