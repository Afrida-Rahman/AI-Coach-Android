package org.tensorflow.lite.examples.poseestimation.exercise

import android.content.Context
import org.tensorflow.lite.examples.poseestimation.domain.model.Person
import org.tensorflow.lite.examples.poseestimation.domain.model.Phase
import org.tensorflow.lite.examples.poseestimation.domain.model.Rule

class GeneralExercise(
    context: Context,
    exerciseId: Int,
    active: Boolean = false
) : IExercise(
    context = context,
    id = exerciseId,
    active = active
) {
    override fun exerciseCount(
        person: Person,
        canvasHeight: Int,
        canvasWidth: Int,
        phases: List<Phase>
    ) {
        TODO("Not yet implemented")
    }

    override fun wrongExerciseCount(person: Person, canvasHeight: Int, canvasWidth: Int) {
        TODO("Not yet implemented")
    }

    override fun drawingRules(person: Person, phases: List<Phase>): List<Rule> {
        TODO("Not yet implemented")
    }
}