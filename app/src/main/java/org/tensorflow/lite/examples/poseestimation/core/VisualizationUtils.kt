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
        setCount: Int,
        repCount: Int,
        person: Person
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
            setCount.toString(),
            Point(width * 1 / 3f, 60f),
            Color.rgb(6, 122, 72),//green
            65f
        )
        draw.writeText(
            repCount.toString(),
            Point(width * 2 / 3f, 60f),
            Color.rgb(19, 93, 148),//blue
            65f
        )
        val nosePoint = Point(
            person.keyPoints[0].coordinate.x,
            person.keyPoints[0].coordinate.y
        )
        val leftAnklePoint = Point(
            person.keyPoints[15].coordinate.x,
            person.keyPoints[15].coordinate.y
        )
        val rightAnklePoint = Point(
            person.keyPoints[16].coordinate.x,
            person.keyPoints[16].coordinate.y
        )
        val middleBorderPoint = Point(
            (nosePoint.x + (leftAnklePoint.x + rightAnklePoint.x)) / 2,
            (nosePoint.y + (leftAnklePoint.y + rightAnklePoint.y)) / 2
        )
//        val rightPosition = middlePoint.x > 450f && middlePoint.x < 550f && middlePoint.y > 600f && middlePoint.y < 800f
        val rightPosition =
            middleBorderPoint.x > height * 4 / 9f && middleBorderPoint.x < height * 6 / 9f

        val color: Int = if (!rightPosition) {
            Color.RED
        } else {
            Color.GREEN
        }
        draw.border(
            Point(width * 4f / 20f, height * 2.5f / 20f),
            Point(width * 16f / 20f, height * 2.5f / 20f),
            Point(width * 16f / 20f, height * 18.5f / 20f),
            Point(width * 4f / 20f, height * 18.5f / 20f),
            _color = color,
            _thickness = BORDER_WIDTH
        )
        return output
    }
}


