package org.tensorflow.lite.examples.poseestimation.api.request

data class PatientDataPayload(
    val Tenant: String,
    val PatientId: String,
    val IsSummaryView: Boolean = false
)