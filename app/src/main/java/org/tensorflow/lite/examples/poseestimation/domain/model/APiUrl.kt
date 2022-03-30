package org.tensorflow.lite.examples.poseestimation.domain.model

data class APiUrl(
    val getAssessmentUrl: String,
    val getExerciseUrl: String,
    val getExerciseConstraintsURL: String,
    val saveExerciseTrackingURL: String
)
