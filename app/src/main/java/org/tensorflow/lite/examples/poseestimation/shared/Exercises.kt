package org.tensorflow.lite.examples.poseestimation.shared

import android.content.Context
import org.tensorflow.lite.examples.poseestimation.exercise.*

object Exercises {

    fun get(context: Context): List<IExercise> {
        return listOf(
            ReachArmsOverHead(context),
            BodyWeightSquat(context),
            HalfSquat(context),
            SeatedKneeExtension(context),
            PelvicBridgeInSupine(context),
            SitToStand(context),
            IsometricCervicalExtension(context),
            LateralBendingStretchInStanding(context),
            TrunkFlexionInStanding(context),
            BirdDogExerciseInQuadruped(context),
            LumberFlexionInSitting(context),
            SingleLegRaiseInQuadruped(context),
            SingleLegRaiseInProne(context),
            ProneOnElbows(context),
            SingleArmRaiseInProne(context),
            SingleArmRaiseInQuadruped(context),
            Quadruped(context),
            PronePressUpLumbar(context),
            PlankOnElbowsInProne(context),
            CommonExercise(context),
            SingleLegFallOutInSupine(context),
            TrunkRotationInSitting(context),
            TrunkRotationInStanding(context),
            PlankOnKneesInProne(context),
            IsometricShoulderAdductionInStanding(context),
            IsometricCervicalExtensionInStanding(context),
            HamstringCurlsInProne(context),
            IsometricCervicalFlexionInStanding(context),
            PosteriorPelvicTiltInSupine(context),
            HamstringCurlsInStanding(context),
            BirdDogInProne(context),
            PlankOnHands(context),
            HamstringStretchInLongSitting(context),
            SeatedRowsTheraband(context),
            IsometricShoulderAdductionInSitting(context),
            IsometricCervicalFlexionInSitting(context),
            IsometricCervicalRotationInStanding(context),
            ShoulderFlexionWithWeightsInStanding(context),
            ShoulderExtensionWithWeightsInStanding(context),
            AROMAnkleDorsiflexionInSitting(context),
            WallSquatsWithBallSqueeze(context),
            WallSquatsWithStabilityBall(context),
            ScapularStabilisationStabilityBallSingleHand(context),
            ScapularStabilizationStabilityBallBothHands(context)
        )
    }

    fun get(context: Context, exerciseId: Int): IExercise {
        return get(context).filter { it.id == exerciseId }[0]
    }

}