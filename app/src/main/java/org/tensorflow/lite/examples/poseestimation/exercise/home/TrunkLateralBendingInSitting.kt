package org.tensorflow.lite.examples.poseestimation.exercise.home

import android.content.Context
import org.tensorflow.lite.examples.poseestimation.domain.model.Person

class TrunkLateralBendingInSitting(context: Context): HomeExercise(context = context, id = 198) {
    override fun wrongExerciseCount(person: Person, canvasHeight: Int, canvasWidth: Int) {
    }
}