package org.tensorflow.lite.examples.poseestimation.exercise

import android.content.Context
import android.util.Log
import org.tensorflow.lite.examples.poseestimation.core.Point
import org.tensorflow.lite.examples.poseestimation.core.Utilities
import org.tensorflow.lite.examples.poseestimation.domain.model.*

class KneeSquat2(
    context: Context
) : IExercise(
    context = context,
    id = 178
) {
    private var phaseIndex = 0

    override fun rightExerciseCount(
        person: Person,
        canvasHeight: Int,
        canvasWidth: Int,
        phases: List<Phase>
    ) {
        val phaseIndices = mutableListOf<Int>()
        val phaseList = phases.sortedBy { it.phaseNumber }.filter {
            val shouldAdd = !phaseIndices.contains(it.phaseNumber)
            phaseIndices.add(it.phaseNumber)
            shouldAdd
        }
        if (phaseList.isNotEmpty() && phaseIndex < phaseList.size) {
            var isConstraintsSatisfied = true

            phaseList[phaseIndex].constraints.forEach {
                when (it.type) {
                    ConstraintType.ANGLE -> {
                        val angle = Utilities.angle(
                            startPoint = person.keyPoints[it.startPointIndex].toRealPoint(),
                            middlePoint = person.keyPoints[it.middlePointIndex].toRealPoint(),
                            endPoint = person.keyPoints[it.endPointIndex].toRealPoint(),
                            clockWise = it.clockWise
                        )
                        Log.d(
                            "KneeSquat2",
                            "$phaseIndex -> ${it.minValue} : $angle : ${it.maxValue}"
                        )
                        if (angle < it.minValue || angle > it.maxValue) {
                            isConstraintsSatisfied = false
                        }
                    }
                    ConstraintType.LINE -> {}
                }
            }

            if (isInsideBox(person, canvasHeight, canvasWidth) && isConstraintsSatisfied) {
                phaseIndex++
                if (phaseIndex == phaseList.size) {
                    phaseIndex = 0
                    repetitionCount()
                }
            }
        }
    }

    override fun wrongExerciseCount(person: Person, canvasHeight: Int, canvasWidth: Int) {
    }

    override fun drawingRules(person: Person, phases: List<Phase>): List<Rule> {
        val shoulderPoint = Point(
            person.keyPoints[5].coordinate.x,
            person.keyPoints[5].coordinate.y
        )
        val hipPoint = Point(
            person.keyPoints[11].coordinate.x,
            person.keyPoints[11].coordinate.y
        )
        val kneePoint = Point(
            person.keyPoints[13].coordinate.x,
            person.keyPoints[13].coordinate.y
        )
        val anklePoint = Point(
            person.keyPoints[15].coordinate.x,
            person.keyPoints[15].coordinate.y
        )
        return mutableListOf(
            Rule(
                type = RuleType.ANGLE,
                startPoint = shoulderPoint,
                middlePoint = hipPoint,
                endPoint = kneePoint,
                clockWise = true
            ),
            Rule(
                type = RuleType.ANGLE,
                startPoint = hipPoint,
                middlePoint = kneePoint,
                endPoint = anklePoint,
                clockWise = false
            )
        )
    }
}
