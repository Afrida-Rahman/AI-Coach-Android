package org.tensorflow.lite.examples.poseestimation.domain.model

import org.tensorflow.lite.examples.poseestimation.exercise.home.HomeExercise

data class TestId(
    val id: String,
    val bodyRegionId: Int,
    val bodyRegionName: String,
    val providerName: String?,
    val providerId: String,
    val testDate: String,
    val isReportReady: Boolean,
    val registrationType: String,
    val exercises: List<HomeExercise>
)
