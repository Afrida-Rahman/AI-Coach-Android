package org.tensorflow.lite.examples.poseestimation.core

import android.content.Context
import org.tensorflow.lite.examples.poseestimation.exercise.home.*
import org.tensorflow.lite.examples.poseestimation.exercise.home.ankle.AROMAnkleDorsiflexionInSitting
import org.tensorflow.lite.examples.poseestimation.exercise.home.back.*
import org.tensorflow.lite.examples.poseestimation.exercise.home.elbow.*
import org.tensorflow.lite.examples.poseestimation.exercise.home.fitness.KneelingSideKick
import org.tensorflow.lite.examples.poseestimation.exercise.home.functional.*
import org.tensorflow.lite.examples.poseestimation.exercise.home.golf.AdvancedBirdDog
import org.tensorflow.lite.examples.poseestimation.exercise.home.golf.OneLeggedHingeInStanding
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
            HamstringCurlsWithWeightsInProne(context),
            HamstringCurlsWithResistanceBandInProne(context),
            KneeFlexionWithResistanceBandInSitting(context),
            KneeFlexionWithResistanceBandInLongSitting(context),
            KneeFlexionWithResistanceBandInStanding(context),
            KneeExtensionWithResistanceBandInStanding(context),
            LungesWithWeights(context),
            PelvicBridgeWithStraightLegRaiseInSupine(context),
            SingleArmRaiseWithWeightsInQuadruped(context),
            HipAbductionWithWeightsInStanding(context),
            HipExtensionWithWeightsInStanding(context),
            HipFlexionWithWeightsInSitting(context),
            HipFlexionWithWeightsInStanding(context),
            WallSquats(context),
            ShoulderOverheadPressWithWeightsInSitting(context),
            ShoulderFlexionWithResistanceBandInStanding(context),
            SeatedRowsWithResistanceBandInLongSitting(context),
            PelvicBridgeWithBallSqueezeInSupine(context),
            SitToStand(context),
            SingleArmRaiseWithWeightsInProne(context),
            AROMCervicalLateralBendingInSitting(context),
            WallAngelsInStanding(context),
            ShoulderAbductionWithWeightsInStanding(context),
            ResistedElbowFlexionWithResistanceBandInSitting(context),
            ResistedElbowFlexionWithWeightsInSitting(context),
            AROMHipFlexionInSitting(context),
            SquatsWithWeights(context),
            ButterflyStretchInSitting(context),
            ModifiedFencerStretch(context),
            ActiveKneeFlexionInLongSitting(context),
            HamstringCurlsWithWeightsInStanding(context),
            ShortArcQuadsInLongSitting(context),
            JumpingForwardAndBackward(context),
            ShoulderFlexionWithDowelAndWeightsInSitting(context),
            ElbowIRWithResistanceBandInStanding(context),
            ElbowERWithResistanceBandInStanding(context),
            ElbowExtensionWithResistanceBandInSitting(context),
            SitToStandAdvance(context),
            StraightLegRaiseInSupine(context),
            HipFlexionWithWeightsInSupine(context),
            HamstringStretchWithChairInSitting(context),
            HamstringStretchWithChairInStanding(context),
            HamstringStretchInSitting(context),
            DeadBugInSupine(context),
            QuadSetsWithTowelInSupine(context),
            IsometricShoulderExtensionInStanding(context),
            IsometricShoulderFlexionInStanding(context),
            AROMShoulderAbductionInStanding(context),
            IsometricShoulderAbductionInStanding(context),
            AAROMShoulderFlexionWithStickInStanding(context),
            MedianNerveGlideInSitting(context),
            SingleKneeToChestHandsFrontKneeInSupine(context),
            RadialNerveGlideInSitting(context),
            ThoracicRotationWithStickInStanding(context),
            PushUpsInProne(context),
            PushUpsFromKnees(context),
            IsometricElbowExtensionInSitting(context),
            IsometricElbowFlexionInSitting(context),
            AROMHipExtensionInStanding(context),
            AROMHipFlexionInStanding(context),
            AROMHipFlexionInSupine(context),
            PassiveHamstringStretchInSitting(context),
            QuadrupedToHalfKneeling(context),
            KneelingToSquatKneeling(context),
            KneelingToHalfKneelingSupported(context),
            KneelingToHalfKneelingUnsupported(context),
            HalfKneelingToStandingSupported(context),
            HalfKneelingToStandingUnsupported(context),
            StandingToHalfKneelingSupported(context),
            StandingToHalfKneelingUnsupported(context),
            OneLeggedHingeInStanding(context),
            AdvancedBirdDog(context),
            BeginnerWallSquats(context),
            IntermediateWallSquats(context),
            KneelingSideKick(context)
        )
    }

    fun get(context: Context, exerciseId: Int): HomeExercise {
        return get(context).find { it.id == exerciseId }!!
    }
}