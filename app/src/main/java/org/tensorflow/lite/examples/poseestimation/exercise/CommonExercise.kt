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
    private var phaseIndex = 0
    private var constraintsIndex = 0
    private var rightCountIndex = 0

    override fun rightExerciseCount(
        person: Person,
        canvasHeight: Int,
        canvasWidth: Int,
        phases: List<Phase>
    ) {
        val phaseList = phases.sortedBy { it.phase }
        val constraintList = phaseList[phaseIndex].constraints
        val insideBox = isInsideBox(person, canvasHeight, canvasWidth)
        if (phaseList.isNotEmpty()) {
            val rightCountStatus = mutableListOf<CountStatus>()
            if (phaseIndex < phaseList.size) {
                val rightCountState = mutableListOf<CountState>()
                if (constraintsIndex < phaseList[phaseIndex].constraints.size &&
                    constraintList[constraintsIndex].type == ConstraintType.ANGLE
                ) {
                    rightCountState.add(
                        CountState(
                            calculatedAngle = Utilities.angle(
                                startPoint = Point(
                                    person.keyPoints[constraintList[constraintsIndex].startPointIndex].coordinate.x,
                                    person.keyPoints[constraintList[constraintsIndex].startPointIndex].coordinate.y
                                ),
                                middlePoint = Point(
                                    person.keyPoints[constraintList[constraintsIndex].middlePointIndex].coordinate.x,
                                    person.keyPoints[constraintList[constraintsIndex].middlePointIndex].coordinate.y
                                ),
                                endPoint = Point(
                                    person.keyPoints[constraintList[constraintsIndex].endPointIndex].coordinate.x,
                                    person.keyPoints[constraintList[constraintsIndex].endPointIndex].coordinate.y
                                ),
                                clockWise = constraintList[constraintsIndex].clockWise
                            ),
                            angleMin = constraintList[constraintsIndex].minValue,
                            angleMax = constraintList[constraintsIndex].maxValue,
                            uniqueId = constraintList[constraintsIndex].uniqueId
                        )
                    )
                    constraintsIndex++
                }
                rightCountState.forEach { _ ->
                    rightCountStatus.add(
                        CountStatus(
                            isRight = (rightCountState[rightCountIndex].calculatedAngle > rightCountState[rightCountIndex].angleMin
                                    && rightCountState[rightCountIndex].calculatedAngle < rightCountState[rightCountIndex].angleMax
                                    && insideBox)
                        )
                    )
                }
                if (!(rightCountStatus.contains(CountStatus(isRight = false)))){
                    phaseIndex++
                    if (phaseIndex == phaseList.size - 1) {
                        phaseIndex = 0
                        repetitionCount()
                    }
                }else {
                    if (!insideBox) {
                        standInside()
                    }
                }
            }
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