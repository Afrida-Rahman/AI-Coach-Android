package org.tensorflow.lite.examples.poseestimation.shared

import android.content.Context
import org.tensorflow.lite.examples.poseestimation.exercise.IExercise
import org.tensorflow.lite.examples.poseestimation.exercise.ReachArmsOverHand

object Exercises {

    fun get(context: Context): List<IExercise> {
        return listOf(
            ReachArmsOverHand(context)
        )
    }

    fun get(context: Context, id: Int): IExercise {
        return get(context).filter { it.id == id }[0]
    }
}