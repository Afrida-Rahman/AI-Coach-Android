package org.tensorflow.lite.examples.poseestimation.domain.model

data class APiUrl(
    val getPatientExerciseURL: String,
    val getKeyPointRestrictionURL: String,
    val saveExerciseTrackingURL: String
)
