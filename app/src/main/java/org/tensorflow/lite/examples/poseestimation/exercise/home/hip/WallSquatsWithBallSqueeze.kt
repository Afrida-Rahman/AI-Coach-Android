package org.tensorflow.lite.examples.poseestimation.exercise.home.hip

import android.content.Context
import org.tensorflow.lite.examples.poseestimation.domain.model.Person
import org.tensorflow.lite.examples.poseestimation.exercise.home.HomeExercise

class WallSquatsWithBallSqueeze(
    context: Context
) : HomeExercise(context = context, id = 397) {
    override fun wrongExerciseCount(person: Person, canvasHeight: Int, canvasWidth: Int) {
    }
}