package org.tensorflow.lite.examples.poseestimation.core


import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import org.tensorflow.lite.examples.poseestimation.domain.model.ConstraintType
import org.tensorflow.lite.examples.poseestimation.domain.model.Person
import org.tensorflow.lite.examples.poseestimation.domain.model.Phase

object VisualizationUtils {
    private const val LINE_WIDTH = 3f
    private const val BORDER_WIDTH = 4f

    fun drawBodyKeyPoints(
        input: Bitmap,
        person: Person,
        phase: Phase?,
        repCount: Int,
        setCount: Int,
        wrongCount: Int,
        holdTime: Int,
        personDistance: Float?,
        borderColor: Int = Color.GREEN,
        isFrontCamera: Boolean = false
    ): Bitmap {
        val output = input.copy(Bitmap.Config.ARGB_8888, true)
        val canvas = Canvas(output)
        if (isFrontCamera) {
            canvas.scale(-1f, 1f, canvas.width.toFloat() / 2, canvas.height.toFloat() / 2)
        }
        val draw = Draw(canvas, Color.WHITE, LINE_WIDTH)
        val width = draw.canvas.width
        val height = draw.canvas.height

        phase?.let {
            for (constraint in it.constraints) {
                val startPoint = person.keyPoints[constraint.startPointIndex].toCanvasPoint()
                val middlePoint = person.keyPoints[constraint.middlePointIndex].toCanvasPoint()
                val endPoint = person.keyPoints[constraint.endPointIndex].toCanvasPoint()
                if (constraint.type == ConstraintType.ANGLE) {
                    if (isFrontCamera) {
                        draw.angle(
                            Point(
                                output.width - startPoint.x,
                                startPoint.y
                            ),
                            Point(
                                output.width - middlePoint.x,
                                middlePoint.y
                            ),
                            Point(
                                output.width - endPoint.x,
                                endPoint.y
                            ),
                            _clockWise = !constraint.clockWise
                        )
                    } else {
                        draw.angle(
                            startPoint,
                            middlePoint,
                            endPoint,
                            _clockWise = constraint.clockWise
                        )
                    }
                } else {
                    if (isFrontCamera) {
                        draw.line(
                            Point(
                                output.width - startPoint.x,
                                startPoint.y
                            ),
                            Point(
                                output.width - endPoint.x,
                                endPoint.y
                            ),
                            _color = constraint.color
                        )
                    } else {
                        draw.line(
                            startPoint,
                            endPoint,
                            _color = constraint.color
                        )
                    }
                }
            }
            it.phaseDialogue?.let { dialogue ->
                draw.writeText(
                    dialogue,
                    Point((width * 1 / 20f) + 20f, (height * 2f / 20f) + 50f),
                    Color.rgb(255, 255, 255),//blue
                    30f,
                    true
                )
            }
            draw.writeText(
                "$repCount / $setCount",
                Point(width * 1 / 7f, 55f),
                Color.rgb(19, 93, 148),//blue
                55f
            )
            personDistance?.let {
                draw.writeText(
                    "%.1f".format(personDistance),
                    Point(width * 1 / 2f, 70f),
                    Color.rgb(200, 0, 0),
                    50f
                )
            }
            val timeToDisplay = it.holdTime - holdTime
            if (timeToDisplay > 0) {
                draw.writeText(
                    timeToDisplay.toString(),
                    Point(width * 3 / 7f, height * 1.2f / 2f),
                    Color.rgb(0, 255, 0),//blue
                    150f
                )
            }
            draw.writeText(
                wrongCount.toString(),
                Point(width * 2.4f / 3f, 55f),
                Color.rgb(255, 0, 0),//green
                55f
            )
            if (borderColor != -1) {
                draw.tetragonal(
                    Point(width * 1f / 20f, height * 2f / 20f),
                    Point(width * 19f / 20f, height * 2f / 20f),
                    Point(width * 19f / 20f, height * 19.5f / 20f),
                    Point(width * 1f / 20f, height * 19.5f / 20f),
                    _color = borderColor,
                    _thickness = BORDER_WIDTH
                )
            }
        }
        return output
    }
}
