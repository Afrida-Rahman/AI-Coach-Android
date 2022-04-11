package org.tensorflow.lite.examples.poseestimation.exercise.home.back

import android.content.Context
import android.media.MediaPlayer
import org.tensorflow.lite.examples.poseestimation.R
import org.tensorflow.lite.examples.poseestimation.domain.model.Instruction
import org.tensorflow.lite.examples.poseestimation.exercise.home.HomeExercise

class PlankOnElbowsInProne(context: Context) : HomeExercise(context = context, id = 352) {
    init {
        val instructions = listOf(
            Instruction(
                text = "PRONE ON ELBOWS",
                player = MediaPlayer.create(context, R.raw.prone_on_elbows)
            ),
            Instruction(
                text = "PLANK UP HOLD",
                player = MediaPlayer.create(context, R.raw.plank_up_hold)
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
