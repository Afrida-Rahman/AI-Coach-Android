package org.tensorflow.lite.examples.poseestimation.exercise.home.back

import android.content.Context
import android.media.MediaPlayer
import org.tensorflow.lite.examples.poseestimation.R
import org.tensorflow.lite.examples.poseestimation.domain.model.Instruction
import org.tensorflow.lite.examples.poseestimation.exercise.home.HomeExercise

class DoubleLegFallOutInSupine(context: Context) : HomeExercise(context = context, id = 563) {
    init {
        val instructions = listOf(
            Instruction(
                text = "START",
                player = MediaPlayer.create(context, R.raw.start)
            ),
            Instruction(
                text = "BOTH KNEES FALL OUT TO LEFT",
                player = MediaPlayer.create(context, R.raw.both_knees_fall_out_to_the_left)
            ),
            Instruction(
                text = "RETURN",
                player = MediaPlayer.create(context, R.raw.return_audio)
            ),
            Instruction(
                text = "BOTH KNEES FALL OUT TO RIGHT",
                player = MediaPlayer.create(context, R.raw.both_knees_fall_out_to_the_right)
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