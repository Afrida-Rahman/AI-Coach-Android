package org.tensorflow.lite.examples.poseestimation.core


import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import org.tensorflow.lite.examples.poseestimation.data.Rule
import org.tensorflow.lite.examples.poseestimation.data.RuleType


object VisualizationUtils {
    private const val LINE_WIDTH = 3f

    fun drawBodyKeypoints(
        input: Bitmap,
        drawingRules: List<Rule>,
        setCount: Int,
        repCount: Int
    ): Bitmap {
        val output = input.copy(Bitmap.Config.ARGB_8888, true)
        val draw = Draw(Canvas(output), Color.WHITE, LINE_WIDTH)

        for (rule in drawingRules) {
            if (rule.type == RuleType.ANGLE) {
                draw.angle(
                    rule.startPoint,
                    rule.middlePoint,
                    rule.endPoint,
                    _clockWise = rule.clockWise
                )
            } else {
                draw.line(rule.startPoint, rule.endPoint, _color = rule.color)
            }
        }
        draw.writeText(
            setCount.toString(),
            Point(draw.canvas.width * 1 / 3f, 60f),
            Color.rgb(6, 122, 72),//green
            65f
        )
        draw.writeText(
            repCount.toString(),
            Point(draw.canvas.width * 2 / 3f, 60f),
            Color.rgb(19, 93, 148),//blue
            65f
        )
        return output
    }

}
