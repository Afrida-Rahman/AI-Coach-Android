package org.tensorflow.lite.examples.poseestimation.exercise.home.back

import android.content.Context
import org.tensorflow.lite.examples.poseestimation.domain.model.Person
import org.tensorflow.lite.examples.poseestimation.exercise.home.HomeExercise

class DoubleKneeToChestInSupine(context: Context) : HomeExercise(context = context, id = 338) {
    override fun wrongExerciseCount(person: Person, canvasHeight: Int, canvasWidth: Int) {
    }
}