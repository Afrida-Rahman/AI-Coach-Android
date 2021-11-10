package org.tensorflow.lite.examples.poseestimation.api.resp

data class PatientExerciseKeypointResponse(
    val Assessments: List<Assessment>,
    val Tenant: String
)