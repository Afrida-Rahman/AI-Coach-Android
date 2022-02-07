package org.tensorflow.lite.examples.poseestimation.core

import android.content.Context
import org.tensorflow.lite.examples.poseestimation.exercise.home.Crunches
import org.tensorflow.lite.examples.poseestimation.exercise.home.HomeExercise
import org.tensorflow.lite.examples.poseestimation.exercise.home.TrunkExtensionOnHandInProne
import org.tensorflow.lite.examples.poseestimation.exercise.home.TrunkLateralBendingInSitting
import org.tensorflow.lite.examples.poseestimation.exercise.home.ankle.AROMAnkleDorsiflexionInSitting
import org.tensorflow.lite.examples.poseestimation.exercise.home.back.*
import org.tensorflow.lite.examples.poseestimation.exercise.home.hip.*
import org.tensorflow.lite.examples.poseestimation.exercise.home.knee.*
import org.tensorflow.lite.examples.poseestimation.exercise.home.neck.*
import org.tensorflow.lite.examples.poseestimation.exercise.home.shoulder.*

object Exercises {

    fun get(context: Context): List<HomeExercise> {
        return listOf(
            ArmRaiseInStanding(context),
            BodyWeightSquats(context),
//            HalfSquat(context),
            KneeExtensionInSitting(context),
            PelvicBridgeInSupine(context),
//            SitToStand(context),
            IsometricCervicalExtensionInSitting(context),
            LateralBendingStretchInStanding(context),
            TrunkFlexionInStanding(context),
            BirdDogInQuadruped(context),
            LumberFlexionInSitting(context),
            SingleLegRaiseInQuadruped(context),
            SingleLegRaiseInProne(context),
            ProneOnElbows(context),
            SingleArmRaiseInProne(context),
            SingleArmRaiseInQuadruped(context),
            Quadruped(context),
            PronePressUpLumbar(context),
            PlankOnElbowsInProne(context),
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
            PlankOnHandsInProne(context),
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
            ScapularStabilizationStabilityBallBothHands(context),
            TrunkExtensionOnHandInProne(context),
            Crunches(context),
            SingleKneeToChestInSupine(context),
            AROMHipAbductionInStanding(context),
            IsometricCervicalRotationInSitting(context),
            SingleArmAndLegRaiseInProne(context),
            TrunkFlexionInSitting(context),
            DoubleKneeToChestInSupine(context),
            TrunkLateralBendingInSitting(context),
            SingleLegRaiseWithWeightsInProne(context),
            CervicalFlexionStretchInSitting(context),
            CervicalExtensionStretchInSitting(context),
            CervicalLateralBendingStretchInSitting(context),
            TrunkFlexionStretchingInStanding(context),
            TrunkExtensionStretchingInStanding(context),
            TrunkLateralBendingInStanding(context),
            TrunkRotationInSupine(context),
            DoubleLegFallOutInSupine(context),
            IsometricCervicalLateralBendingInStanding(context),
            IsometricCervicalLateralBendingInSitting(context),
            CervicalLateralBendingWithResistanceBandInSitting(context),
            TrunkExtensionWithHandsOnHipsInStanding(context),
            AROMCervicalExtensionInSitting(context),
            AROMCervicalFlexionInSitting(context),
            AROMCervicalFlexionInSupine(context),
            KneeExtensionWithResistanceBandInSitting(context),
            HamstringCurlsWithWeightsInProne(context)
        )
    }

    fun get(context: Context, exerciseId: Int): HomeExercise {
        return get(context).filter { it.id == exerciseId }[0]
    }

}