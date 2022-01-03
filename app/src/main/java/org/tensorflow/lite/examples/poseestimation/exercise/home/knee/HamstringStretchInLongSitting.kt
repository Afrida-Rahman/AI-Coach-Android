package org.tensorflow.lite.examples.poseestimation.exercise.home.knee

import android.content.Context
import org.tensorflow.lite.examples.poseestimation.domain.model.Person
import org.tensorflow.lite.examples.poseestimation.exercise.home.HomeExercise

class HamstringStretchInLongSitting(context: Context) : HomeExercise(context = context, id = 339) {
    override fun wrongExerciseCount(person: Person, canvasHeight: Int, canvasWidth: Int) {
    }
}