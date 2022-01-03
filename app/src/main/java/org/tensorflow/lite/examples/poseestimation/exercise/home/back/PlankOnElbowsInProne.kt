package org.tensorflow.lite.examples.poseestimation.exercise.home.back

import android.content.Context
import org.tensorflow.lite.examples.poseestimation.core.Point
import org.tensorflow.lite.examples.poseestimation.core.Utilities
import org.tensorflow.lite.examples.poseestimation.domain.model.Person
import org.tensorflow.lite.examples.poseestimation.exercise.home.HomeExercise

class PlankOnElbowsInProne(
    context: Context
) : HomeExercise(
    context = context,
    id = 352
) {
    private var downHipAngleMin = 200f
    private var downHipAngleMax = 220f

    private var upHipAngleMin = 170f
    private var upHipAngleMax = 185f

    private var wrongUpHipAngleMin = 0f
    private var wrongUpHipAngleMax = 0f

    private var wrongDownHipAngleMin = 0f
    private var wrongDownHipAngleMax = 0f

    override var wrongStateIndex = 0

    override fun wrongExerciseCount(person: Person, canvasHeight: Int, canvasWidth: Int) {
        val shoulderPoint = Point(
            person.keyPoints[5].coordinate.x,
            -person.keyPoints[5].coordinate.y
        )
        val hipPoint = Point(
            person.keyPoints[11].coordinate.x,
            -person.keyPoints[11].coordinate.y
        )
        val kneePoint = Point(
            person.keyPoints[13].coordinate.x,
            -person.keyPoints[13].coordinate.y
        )

        wrongUpHipAngleMin = upHipAngleMin
        wrongUpHipAngleMax = upHipAngleMax
        wrongDownHipAngleMin = downHipAngleMin + 40
        wrongDownHipAngleMax = downHipAngleMax + 70

        val wrongCountStates: Array<FloatArray> = arrayOf(
            floatArrayOf(
                wrongUpHipAngleMin,
                wrongUpHipAngleMax,
            ),
            floatArrayOf(
                wrongDownHipAngleMin,
                wrongDownHipAngleMax,
            ),
            floatArrayOf(
                wrongUpHipAngleMin,
                wrongUpHipAngleMax,
            )
        )

        val insideBox = isInsideBox(person, canvasHeight, canvasWidth)
        val hipAngle = Utilities.angle(shoulderPoint, hipPoint, kneePoint, true)


        if (hipAngle > wrongCountStates[wrongStateIndex][0] && hipAngle < wrongCountStates[wrongStateIndex][1] &&
            insideBox
        ) {
            if (insideBox) {
                wrongStateIndex += 1
                if (wrongStateIndex == wrongCountStates.size) {
                    wrongStateIndex = 0
                    wrongCount()
                }
            }
        }
    }
}
