package org.tensorflow.lite.examples.poseestimation.api.request


data class ExerciseRequestPayload(
    val KeyPointsRestrictions: List<ExerciseData>,
    val Tenant : String
)

data class ExerciseRequestPayload1(
    val Tenant : String,
    val PatientId: String
)

