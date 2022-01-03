package org.tensorflow.lite.examples.poseestimation.domain.model

import org.tensorflow.lite.examples.poseestimation.exercise.home.HomeExercise

data class TestId(
    val id: String,
    val exercises: List<HomeExercise>
)
