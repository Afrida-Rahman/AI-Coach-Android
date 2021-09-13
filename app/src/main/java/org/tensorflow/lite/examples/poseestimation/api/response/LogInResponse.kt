package org.tensorflow.lite.examples.poseestimation.api.response

data class LogInResponse(
    val FirstName: String,
    val LastName: String,
    val PatientId: String,
    val Success: Boolean
)