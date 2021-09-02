package org.tensorflow.lite.examples.poseestimation

data class KeyPointsRestriction(
    val AngleArea: String,
    val CapturedImage: String,
    val Direction: String,
    val EndKeyPosition: String,
    val ExerciseId: Int,
    val IsPhaseFinished: Boolean,
    val LineType: String,
    val MaxValidationValue: Int,
    val MiddleKeyPosition: String,
    val MinValidationValue: Int,
    val NoOfKeyPoints: Int,
    val Phase: Int,
    val Scale: String,
    val StartKeyPosition: String,
    val Tenant: Any
)

data class PostedData(
    val Tetant : String
)