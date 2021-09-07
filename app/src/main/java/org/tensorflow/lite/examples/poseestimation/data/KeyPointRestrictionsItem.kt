package org.tensorflow.lite.examples.poseestimation.data

data class KeyPointRestrictionsItem(
    val ExerciseId: Int,
    val KeyPointsRestrictionGroup: List<KeyPointsRestrictionGroup>,
    val Tenant: String
)