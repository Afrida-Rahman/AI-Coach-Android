package org.tensorflow.lite.examples.poseestimation.api.response

data class Restrictions(
    val Scale: String,
    val LineType: String,
    val NoOfKeyPoints: Int,
    val Direction: String,
    val StartKeyPosition: String,
    val MiddleKeyPosition: String,
    val EndKeyPosition: String,
    val MinValidationValue: Int,
    val MaxValidationValue: Int,
    val AngleArea: String,
    val DrawExtensionFlexion: Boolean
)
