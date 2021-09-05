package org.tensorflow.lite.examples.poseestimation.data

data class ExerciseConstraint(
    val KeyPointsRestrictions: List<KeyPointsRestriction>,
    val Tenant: String
)