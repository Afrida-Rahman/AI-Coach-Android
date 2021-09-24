package org.tensorflow.lite.examples.poseestimation.shared

import android.content.Context
import org.tensorflow.lite.examples.poseestimation.exercise.IExercise
import org.tensorflow.lite.examples.poseestimation.exercise.PelvicBridge
import org.tensorflow.lite.examples.poseestimation.exercise.ReachArmsOverHead

object Exercises {

    fun get(context: Context): List<IExercise> {
        return listOf(
            ReachArmsOverHead(context),
            PelvicBridge(context)
        )
    }

    fun get(context: Context, exerciseId: Int): IExercise {
        return get(context).filter { it.id == exerciseId }[0]
    }

}