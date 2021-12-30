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
            PelvicBridgeInSupine(context),
            SitToStand(context),
            IsometricCervicalExtension(context),
            LateralBendingStretchInStanding(context),
            TrunkFlexionInStanding(context),
            BirdDog(context),
            LumberFlexionInSitting(context),
            SingleLegRaiseInQuadruped(context),
            SingleLegRaiseInProne(context),
            ProneOnElbows(context),
            SingleArmRaiseInProne(context),
            SingleArmRaiseInQuadruped(context),
            Quadruped(context),
            PronePressUpLumbar(context),
            PlankOnElbowsInProne(context),
            CommonExercise(context)
        )
    }

    fun get(context: Context, exerciseId: Int): IExercise {
        return get(context).filter { it.id == exerciseId }[0]
    }

}