package org.tensorflow.lite.examples.poseestimation.exercise

import android.content.Context
import org.tensorflow.lite.examples.poseestimation.core.Point
import org.tensorflow.lite.examples.poseestimation.core.Utilities
import org.tensorflow.lite.examples.poseestimation.domain.model.*

class CommonExercise(
    context: Context
) : ICommonExercise(
    context = context,
    id = 0
) {
    var phaseIndex = 0
    var constraintsIndex = 0
    override fun rightExerciseCount(
        person: Person,
        canvasHeight: Int,
        canvasWidth: Int,
        phases: List<Phase>
    ) {
        val constraintList = phases[phaseIndex].constraints
        if (phaseIndex < phases.size) {
            if (constraintsIndex < phases[phaseIndex].constraints.size){
                if (constraintList[constraintsIndex].type == ConstraintType.ANGLE){
                    var angleInDegree = Utilities.angle(
                        startPoint = Point(person.keyPoints[constraintList[constraintsIndex].startPointIndex].coordinate.x,
                            person.keyPoints[constraintList[constraintsIndex].startPointIndex].coordinate.y),
                        middlePoint = Point(person.keyPoints[constraintList[constraintsIndex].middlePointIndex].coordinate.x,
                            person.keyPoints[constraintList[constraintsIndex].middlePointIndex].coordinate.y),
                        endPoint = Point(person.keyPoints[constraintList[constraintsIndex].endPointIndex].coordinate.x,
                            person.keyPoints[constraintList[constraintsIndex].endPointIndex].coordinate.y),
                        clockWise = constraintList[constraintsIndex].clockWise)
                }
                constraintsIndex++
            }
            phaseIndex++
        }
    }

    override fun wrongExerciseCount(
        person: Person,
        canvasHeight: Int,
        canvasWidth: Int,
        phases: List<Phase>
    ) {
        TODO("Not yet implemented")
    }

    override fun drawingRules(person: Person, phases: List<Phase>): List<Rule> {
        TODO("Not yet implemented")
    }

}