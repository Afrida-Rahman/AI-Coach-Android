package org.tensorflow.lite.examples.poseestimation.core


import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import org.tensorflow.lite.examples.poseestimation.data.Person
import org.tensorflow.lite.examples.poseestimation.data.Rule
import org.tensorflow.lite.examples.poseestimation.data.RuleType


object VisualizationUtils {
    private const val LINE_WIDTH = 3f
    private const val BORDER_WIDTH = 6f

    fun drawBodyKeypoints(
        input: Bitmap,
        drawingRules: List<Rule>,
        repCount: Int,
        wrongCount: Int,
        borderColor: Int = Color.GREEN
    ): Bitmap {
        val output = input.copy(Bitmap.Config.ARGB_8888, true)
        val draw = Draw(Canvas(output), Color.WHITE, LINE_WIDTH)
        val width = draw.canvas.width
        val height = draw.canvas.height

        for (rule in drawingRules) {
            if (rule.type == RuleType.ANGLE) {
                draw.angle(
                    rule.startPoint,
                    rule.middlePoint1,
                    rule.endPoint,
                    _clockWise = rule.clockWise
                )
            } else {
                draw.line(rule.startPoint, rule.endPoint, _color = rule.color)
            }
        }
        draw.writeText(
            repCount.toString(),
            Point(width * 1 / 3f, 60f),
            Color.rgb(19, 93, 148),//blue
            65f
        )
        draw.writeText(
            wrongCount.toString(),
            Point(width * 2 / 3f, 60f),
            Color.rgb(255, 0, 0),//green
            65f
        )
        if(borderColor != -1) {
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


