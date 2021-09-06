package org.tensorflow.lite.examples.poseestimation.data

data class KeyPointsRestriction(
    val AngleArea: String,
    val CapturedImage: String,
    val CreatedOnUtc: String,
    val Direction: String,
    val EndKeyPosition: String,
    val ExerciseId: Int,
    val Id: Int,
    val IsActive: Boolean,
    val IsActiveList: Any,
    val IsPhaseFinished: Boolean,
    val LineType: String,
    val MaxValidationValue: Int,
    val MiddleKeyPosition: String,
    val MinValidationValue: Int,
    val NoOfKeyPoints: Int,
    val Phase: Int,
    val Scale: String,
    val SelectedIsActiveList: Any,
    val StartKeyPosition: String,
    val Tenant: Any,
    val UpdatedOnUtc: Any
)

data class ExerciseData(
    val ExerciseId: Int
)

data class PostedData(
    val KeyPointsRestrictions: List<ExerciseData>,
    val Tenant : String
)