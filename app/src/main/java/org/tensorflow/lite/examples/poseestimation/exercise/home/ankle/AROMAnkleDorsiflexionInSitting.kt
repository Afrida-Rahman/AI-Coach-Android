package org.tensorflow.lite.examples.poseestimation.exercise.home.ankle

import android.content.Context
import org.tensorflow.lite.examples.poseestimation.domain.model.Person
import org.tensorflow.lite.examples.poseestimation.exercise.home.HomeExercise

class AROMAnkleDorsiflexionInSitting(
    context: Context
) : HomeExercise(context = context, id = 50) {
    override fun wrongExerciseCount(person: Person, canvasHeight: Int, canvasWidth: Int) {

    }
}