package org.tensorflow.lite.examples.poseestimation.api.response

data class Assessment(
    val TestId: String,
    val BodyRegionId: Int,
    val BodyRegionName: String,
    val ProviderName: String,
    val ProviderId: String,
    val CreatedOnUtc: String,
    val IsReportReady: Boolean,
    val RegistrationType: String,
    val TotalExercise: Int
)