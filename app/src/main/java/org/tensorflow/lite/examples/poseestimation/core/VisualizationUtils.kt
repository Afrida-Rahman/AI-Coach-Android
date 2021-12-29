package org.tensorflow.lite.examples.poseestimation.core


import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.util.Log
import org.tensorflow.lite.examples.poseestimation.domain.model.*


object VisualizationUtils {
    private const val LINE_WIDTH = 3f
    private const val BORDER_WIDTH = 6f

    fun drawBodyKeyPoints(
        input: Bitmap,
        person: Person,
        drawingRules: List<Constraint>,
        repCount: Int,
        setCount: Int,
        wrongCount: Int,
        holdTime: Long,
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


        for (rule in drawingRules) {
            val startPoint = person.keyPoints[rule.startPointIndex].toCanvasPoint()
            val middlePoint = person.keyPoints[rule.middlePointIndex].toCanvasPoint()
            val endPoint = person.keyPoints[rule.endPointIndex].toCanvasPoint()
            if (rule.type == ConstraintType.ANGLE) {
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
                        _clockWise = !rule.clockWise
                    )
                } else {
                    draw.angle(
                        startPoint,
                        middlePoint,
                        endPoint,
                        _clockWise = rule.clockWise
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
                        _color = rule.color
                    )
                } else {
                    draw.line(
                        startPoint,
                        endPoint,
                        _color = rule.color
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
            "$holdTime",
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
                Point(width * 2f / 20f, height * 2.5f / 20f),
                Point(width * 18.5f / 20f, height * 2.5f / 20f),
                Point(width * 18.5f / 20f, height * 18.5f / 20f),
                Point(width * 2f / 20f, height * 18.5f / 20f),
                _color = borderColor,
                _thickness = BORDER_WIDTH
            )
        }
        return output
    }
}


