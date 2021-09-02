package org.tensorflow.lite.examples.poseestimation

data class ExerciseConstraint(
    val KeyPointsRestrictions: List<KeyPointsRestriction>,
    val Tenant: String
)