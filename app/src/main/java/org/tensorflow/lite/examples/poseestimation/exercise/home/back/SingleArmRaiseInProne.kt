package org.tensorflow.lite.examples.poseestimation.exercise.home.back

import android.content.Context
import android.media.MediaPlayer
import org.tensorflow.lite.examples.poseestimation.R
import org.tensorflow.lite.examples.poseestimation.domain.model.Instruction
import org.tensorflow.lite.examples.poseestimation.exercise.home.HomeExercise

class SingleArmRaiseInProne(
    context: Context
) : HomeExercise(
    context = context,
    id = 500
) {
    init {
        val instructions = listOf(
            Instruction(
                text = "START",
                player = MediaPlayer.create(context, R.raw.start)
            ),
            Instruction(
                text = "LIFT LEFT ARM HOLD",
                player = MediaPlayer.create(context, R.raw.lift_left_arm)
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