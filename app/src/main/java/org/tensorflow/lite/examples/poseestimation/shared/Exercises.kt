package org.tensorflow.lite.examples.poseestimation.shared

import android.content.Context
import org.tensorflow.lite.examples.poseestimation.exercise.*

object Exercises {

    fun get(context: Context): List<IExercise> {
        return listOf(
            ReachArmsOverHead(context),
            KneeSquat(context),
            HalfSquat(context),
            SeatedKneeExtension(context),
            PelvicBridge(context),
            SitToStand(context),
            IsometricCervicalExtension(context),
            LateralTrunkStretch(context),
            AROMStandingTrunkFlexion(context),
            BirdDog(context),
            LumberFlexionSitting(context),
            SingleLegRaiseInQuadruped(context),
            ProneOnElbows(context)
        )
    }

    fun get(context: Context, exerciseId: Int): IExercise {
        return get(context).filter { it.id == exerciseId }[0]
    }

}