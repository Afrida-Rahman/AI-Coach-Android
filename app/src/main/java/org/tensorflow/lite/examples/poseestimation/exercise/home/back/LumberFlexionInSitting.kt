package org.tensorflow.lite.examples.poseestimation.exercise.home.back

import android.content.Context
import android.media.MediaPlayer
import org.tensorflow.lite.examples.poseestimation.R
import org.tensorflow.lite.examples.poseestimation.domain.model.Instruction
import org.tensorflow.lite.examples.poseestimation.exercise.home.HomeExercise

class LumberFlexionInSitting(
    context: Context
) : HomeExercise(
    context = context,
    id = 341
) {
    init {
        val instructions = listOf(
            Instruction(
                text = "START",
                player = MediaPlayer.create(context, R.raw.start)
            ),
            Instruction(
                text = "LEAN FORWARD HOLD",
                player = MediaPlayer.create(context, R.raw.lean_forward_hold)
            ),
            Instruction(
                text = "RETURN",
                player = MediaPlayer.create(context, R.raw.return_audio)
            )
        )
        instructions.forEach { instruction ->
            addInstruction(instruction)
        }
    }
}
