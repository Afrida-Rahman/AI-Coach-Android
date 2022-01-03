package org.tensorflow.lite.examples.poseestimation.exercise.home.shoulder

import android.content.Context
import org.tensorflow.lite.examples.poseestimation.domain.model.Person
import org.tensorflow.lite.examples.poseestimation.exercise.home.HomeExercise

class IsometricShoulderAdductionInStanding(
    context: Context
) : HomeExercise(
    context = context,
    id = 560
) {
    override fun wrongExerciseCount(person: Person, canvasHeight: Int, canvasWidth: Int) {
    }
}