package org.tensorflow.lite.examples.poseestimation.api.response

data class KeyPointsRestrictionGroup(
    val Id: Int,
    val Phase: Int,
    val PhaseDialogue: String,
    val HoldInSeconds: Int,
    val CapturedImage: String,
    val ResistanceId: Int,
    val KeyPointsRestriction: List<KeyPointsRestriction>,
)