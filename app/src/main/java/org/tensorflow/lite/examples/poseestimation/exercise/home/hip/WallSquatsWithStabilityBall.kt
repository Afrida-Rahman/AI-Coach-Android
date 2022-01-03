package org.tensorflow.lite.examples.poseestimation.exercise.home.hip

import android.content.Context
import org.tensorflow.lite.examples.poseestimation.domain.model.Person
import org.tensorflow.lite.examples.poseestimation.exercise.home.HomeExercise

class WallSquatsWithStabilityBall(
    context: Context
) : HomeExercise(context = context, id = 150) {
    override fun wrongExerciseCount(person: Person, canvasHeight: Int, canvasWidth: Int) {

    }
}