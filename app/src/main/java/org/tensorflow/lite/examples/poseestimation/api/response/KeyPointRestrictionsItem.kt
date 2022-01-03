package org.tensorflow.lite.examples.poseestimation.api.response

data class KeyPointRestrictionsItem(
    val ExerciseMedia: String,
    val IsPhaseFinished: Boolean,
    val ExerciseId: Int,
    val KeyPointsRestrictionGroup: List<KeyPointsRestrictionGroup>,
    val Tenant: String
)