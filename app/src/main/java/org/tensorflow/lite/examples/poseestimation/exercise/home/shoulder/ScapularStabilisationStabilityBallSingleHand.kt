package org.tensorflow.lite.examples.poseestimation.exercise.home.shoulder

import android.content.Context
import org.tensorflow.lite.examples.poseestimation.domain.model.Person
import org.tensorflow.lite.examples.poseestimation.exercise.home.HomeExercise

class ScapularStabilisationStabilityBallSingleHand(
    context: Context
) : HomeExercise(context = context, id = 6230) {
    override fun wrongExerciseCount(person: Person, canvasHeight: Int, canvasWidth: Int) {

    }
}