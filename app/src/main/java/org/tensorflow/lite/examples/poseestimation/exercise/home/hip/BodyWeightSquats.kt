package org.tensorflow.lite.examples.poseestimation.exercise.home.hip

import android.content.Context
import org.tensorflow.lite.examples.poseestimation.core.Point
import org.tensorflow.lite.examples.poseestimation.core.Utilities
import org.tensorflow.lite.examples.poseestimation.core.VisualizationUtils
import org.tensorflow.lite.examples.poseestimation.domain.model.Person
import org.tensorflow.lite.examples.poseestimation.exercise.home.HomeExercise

class BodyWeightSquats(
    context: Context
) : HomeExercise(
    context = context,
    id = 458
) {
    private var upHipAngleMin = 160f
    private var upHipAngleMax = 190f
    private var upKneeAngleMin = 160f
    private var upKneeAngleMax = 190f

    private var downHipAngleMin = 60f
    private var downHipAngleMax = 90f
    private var downKneeAngleMin = 60f
    private var downKneeAngleMax = 90f

    private var wrongUpHipAngleMin = 160f
    private var wrongUpHipAngleMax = 190f
    private var wrongUpKneeAngleMin = 160f
    private var wrongUpKneeAngleMax = 190f

    private var wrongDownHipAngleMin = 100f
    private var wrongDownHipAngleMax = 160f
    private var wrongDownKneeAngleMin = 100f
    private var wrongDownKneeAngleMax = 160f

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
        val anklePoint = Point(
            person.keyPoints[15].coordinate.x,
            -person.keyPoints[15].coordinate.y
        )

        wrongUpHipAngleMin = upHipAngleMin
        wrongUpHipAngleMax = upHipAngleMax
        wrongUpKneeAngleMin = upKneeAngleMin
        wrongUpKneeAngleMax = upKneeAngleMax
        wrongDownHipAngleMin = downHipAngleMin + 40
        wrongDownHipAngleMax = downHipAngleMax + 70
        wrongDownKneeAngleMin = downKneeAngleMin + 40
        wrongDownKneeAngleMax = downKneeAngleMax + 70

        val wrongCountStates: Array<FloatArray> = arrayOf(
            floatArrayOf(
                wrongUpHipAngleMin,
                wrongUpHipAngleMax,
                wrongUpKneeAngleMin,
                wrongUpKneeAngleMax
            ),
            floatArrayOf(
                wrongDownHipAngleMin,
                wrongDownHipAngleMax,
                wrongDownKneeAngleMin,
                wrongDownKneeAngleMax
            ),
            floatArrayOf(
                wrongUpHipAngleMin,
                wrongUpHipAngleMax,
                wrongUpKneeAngleMin,
                wrongUpKneeAngleMax
            )
        )

        val insideBox = VisualizationUtils.isInsideBox(person, canvasHeight, canvasWidth)
        val hipAngle = Utilities.angle(shoulderPoint, hipPoint, kneePoint, true)
        val kneeAngle = Utilities.angle(hipPoint, kneePoint, anklePoint)


        if (hipAngle > wrongCountStates[wrongStateIndex][0] && hipAngle < wrongCountStates[wrongStateIndex][1] &&
            kneeAngle > wrongCountStates[wrongStateIndex][2] && kneeAngle < wrongCountStates[wrongStateIndex][3] &&
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
