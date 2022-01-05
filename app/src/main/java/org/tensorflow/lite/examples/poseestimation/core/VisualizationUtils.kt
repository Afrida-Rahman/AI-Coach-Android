package org.tensorflow.lite.examples.poseestimation.core


import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import org.tensorflow.lite.examples.poseestimation.domain.model.ConstraintType
import org.tensorflow.lite.examples.poseestimation.domain.model.Person
import org.tensorflow.lite.examples.poseestimation.domain.model.Phase

object VisualizationUtils {
    private const val LINE_WIDTH = 3f
    private const val BORDER_WIDTH = 6f

    fun drawBodyKeyPoints(
        input: Bitmap,
        person: Person,
        phase: Phase?,
        repCount: Int,
        setCount: Int,
        wrongCount: Int,
        holdTime: Int,
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
            draw.writeText(
                "$repCount / $setCount",
                Point(width * 1 / 7f, 60f),
                Color.rgb(19, 93, 148),//blue
                65f
            )
            draw.writeText(
                "$holdTime/${it.holdTime}",
                Point(width * 1 / 2f, 60f),
                Color.rgb(19, 93, 148),//blue
                35f
            )
            draw.writeText(
                wrongCount.toString(),
                Point(width * 2.4f / 3f, 60f),
                Color.rgb(255, 0, 0),//green
                65f
            )
            if (borderColor != -1) {
                draw.rectangle(
                    Point(width * 1f / 20f, height * 2.5f / 20f),
                    Point(width * 19f / 20f, height * 2.5f / 20f),
                    Point(width * 19f / 20f, height * 18.5f / 20f),
                    Point(width * 1f / 20f, height * 18.5f / 20f),
                    _color = borderColor,
                    _thickness = BORDER_WIDTH
                )
            }
        }
        return output
    }
}
