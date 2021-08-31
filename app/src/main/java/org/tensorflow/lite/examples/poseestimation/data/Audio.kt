package org.tensorflow.lite.examples.poseestimation.data

data class Audio(
   val resourcePath: String,
   var alreadyPlayed : Boolean = false
)