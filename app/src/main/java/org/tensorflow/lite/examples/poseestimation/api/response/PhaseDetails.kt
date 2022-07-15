package org.tensorflow.lite.examples.poseestimation.api.response

data class PhaseDetails(
    val HoldInSeconds: Int,
    val Restrictions: List<Restrictions>,
    val PhaseNumber: Int,
    val PhaseDialogue: String,
    val CapturedImage: String
)
