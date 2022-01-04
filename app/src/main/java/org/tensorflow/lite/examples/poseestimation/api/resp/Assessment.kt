package org.tensorflow.lite.examples.poseestimation.api.resp

data class Assessment(
    val Exercises: List<Exercise>,
    val TestId: String,
    val BodyRegionId: Int,
    val BodyRegionName: String,
    val ProviderName: String,
    val ProviderId: String,
    val CreatedOnUtc: String,
    val IsReportReady: Boolean,
    val RegistrationType: String
)