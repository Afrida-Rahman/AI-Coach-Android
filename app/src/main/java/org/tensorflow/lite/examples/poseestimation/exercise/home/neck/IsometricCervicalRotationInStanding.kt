package org.tensorflow.lite.examples.poseestimation.exercise.home.neck

import android.content.Context
import org.tensorflow.lite.examples.poseestimation.domain.model.Person
import org.tensorflow.lite.examples.poseestimation.exercise.home.HomeExercise

class IsometricCervicalRotationInStanding(
    context: Context
) : HomeExercise(context = context, id = 532) {
    override fun wrongExerciseCount(person: Person, canvasHeight: Int, canvasWidth: Int) {
    }
}