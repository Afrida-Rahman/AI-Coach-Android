package org.tensorflow.lite.examples.poseestimation.api.request

data class AssessmentListRequestPayload(
    val Tenant: String,
    val PatientId: String
)