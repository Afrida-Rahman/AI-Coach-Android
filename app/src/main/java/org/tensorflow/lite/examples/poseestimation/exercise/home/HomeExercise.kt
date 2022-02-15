package org.tensorflow.lite.examples.poseestimation.exercise.home

import android.content.Context
import android.widget.Toast
import androidx.annotation.RawRes
import org.tensorflow.lite.examples.poseestimation.R
import org.tensorflow.lite.examples.poseestimation.api.IExerciseService
import org.tensorflow.lite.examples.poseestimation.api.request.ExerciseData
import org.tensorflow.lite.examples.poseestimation.api.request.ExerciseRequestPayload
import org.tensorflow.lite.examples.poseestimation.api.response.KeyPointRestrictions
import org.tensorflow.lite.examples.poseestimation.core.AudioPlayer
import org.tensorflow.lite.examples.poseestimation.core.CountDownAudioPlayer
import org.tensorflow.lite.examples.poseestimation.core.Utilities
import org.tensorflow.lite.examples.poseestimation.core.Utilities.getIndex
import org.tensorflow.lite.examples.poseestimation.core.VisualizationUtils
import org.tensorflow.lite.examples.poseestimation.domain.model.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.math.pow
import kotlin.math.sqrt

abstract class HomeExercise(
    val context: Context,
    val id: Int,
    var name: String = "",
    val active: Boolean = true,
    var protocolId: Int = 0,
    var instruction: String? = "",
    var videoUrls: String = "",
    var imageUrls: List<String> = listOf(),
    var maxRepCount: Int = 0,
    var maxSetCount: Int = 0
) {
    open var phaseIndex = 0
    open var rightCountPhases = mutableListOf<Phase>()
    open var wrongStateIndex = 0
    private val audioPlayer = AudioPlayer(context)
    private var setCounter = 0
    private var wrongCounter = 0
    private var repetitionCounter = 0
    private var lastTimePlayed: Int = System.currentTimeMillis().toInt()
    private var focalLengths: FloatArray? = null
    private var previousCountDown = 0
    private var downTimeCounter = 0
    open var phaseEntered = false
    private var phaseEnterTime = System.currentTimeMillis()
    private lateinit var countDownAudioPlayerPlayer: CountDownAudioPlayer

    fun setExercise(
        exerciseName: String,
        exerciseInstruction: String?,
        exerciseImageUrls: List<String>,
        exerciseVideoUrls: String,
        repetitionLimit: Int,
        setLimit: Int,
        protoId: Int
    ) {
        name = exerciseName
        maxRepCount = repetitionLimit
        maxSetCount = setLimit
        protocolId = protoId
        instruction = exerciseInstruction
        imageUrls = exerciseImageUrls
        videoUrls = exerciseVideoUrls
    }

    fun initializeConstraint(tenant: String) {
        val service = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Utilities.getUrl(tenant).getKeyPointRestrictionURL)
            .build()
            .create(IExerciseService::class.java)
        val requestPayload = ExerciseRequestPayload(
            Tenant = tenant,
            KeyPointsRestrictions = listOf(
                ExerciseData(id)
            )
        )
        val response = service.getExerciseConstraint(requestPayload)
        response.enqueue(object : Callback<KeyPointRestrictions> {
            override fun onResponse(
                call: Call<KeyPointRestrictions>,
                response: Response<KeyPointRestrictions>
            ) {
                val responseBody = response.body()
                if (responseBody == null) {
                    Toast.makeText(
                        context,
                        "Failed to get necessary constraints for this exercise and got empty response. So, this exercise can't be performed now!",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    if (responseBody[0].KeyPointsRestrictionGroup.isNotEmpty()) {
                        responseBody[0].KeyPointsRestrictionGroup.forEach { group ->
                            val constraints = mutableListOf<Constraint>()
                            group.KeyPointsRestriction.sortedByDescending { it.Id }
                                .forEach { restriction ->
                                    val constraintType = if (restriction.Scale == "degree") {
                                        ConstraintType.ANGLE
                                    } else {
                                        ConstraintType.LINE
                                    }
                                    val startPointIndex = getIndex(restriction.StartKeyPosition)
                                    val middlePointIndex = getIndex(restriction.MiddleKeyPosition)
                                    val endPointIndex = getIndex(restriction.EndKeyPosition)
                                    when (constraintType) {
                                        ConstraintType.LINE -> {
                                            if (startPointIndex >= 0 && endPointIndex >= 0) {
                                                constraints.add(
                                                    Constraint(
                                                        minValue = restriction.MinValidationValue,
                                                        maxValue = restriction.MaxValidationValue,
                                                        uniqueId = restriction.Id,
                                                        type = constraintType,
                                                        startPointIndex = startPointIndex,
                                                        middlePointIndex = middlePointIndex,
                                                        endPointIndex = endPointIndex,
                                                        clockWise = false
                                                    )
                                                )
                                            }
                                        }
                                        ConstraintType.ANGLE -> {
                                            if (startPointIndex >= 0 && middlePointIndex >= 0 && endPointIndex >= 0) {
                                                constraints.add(
                                                    Constraint(
                                                        minValue = restriction.MinValidationValue,
                                                        maxValue = restriction.MaxValidationValue,
                                                        uniqueId = restriction.Id,
                                                        type = constraintType,
                                                        startPointIndex = startPointIndex,
                                                        middlePointIndex = middlePointIndex,
                                                        endPointIndex = endPointIndex,
                                                        clockWise = restriction.AngleArea == "clockwise"
                                                    )
                                                )
                                            }
                                        }
                                    }
                                }
                            rightCountPhases.add(
                                Phase(
                                    phaseNumber = group.Phase,
                                    constraints = constraints,
                                    holdTime = group.HoldInSeconds,
                                    phaseDialogue = group.PhaseDialogue
                                )
                            )
                        }
                    } else {
                        Toast.makeText(
                            context,
                            "Don't have enough data to perform this exercise. Please provide details of this exercise using EMMA LPT app!",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
                rightCountPhases = sortedPhaseList(rightCountPhases.toList()).toMutableList()
            }

            override fun onFailure(call: Call<KeyPointRestrictions>, t: Throwable) {
                Toast.makeText(
                    context,
                    "Failed to get exercise response from API !!!",
                    Toast.LENGTH_LONG
                ).show()
            }
        })
        countDownAudioPlayerPlayer = CountDownAudioPlayer(context)
    }

    fun getMaxHoldTime(): Int = rightCountPhases.map { it.holdTime }.maxOrNull() ?: 0

    fun getRepetitionCount() = repetitionCounter

    fun getWrongCount() = wrongCounter

    fun getSetCount() = setCounter

    fun getHoldTimeLimitCount(): Int = downTimeCounter

    fun getPhase(): Phase? {
        return if (phaseIndex < rightCountPhases.size) {
            rightCountPhases[phaseIndex]
        } else {
            null
        }
    }

    fun repetitionCount() {
        repetitionCounter++
        audioPlayer.playFromFile(R.raw.right_count)
        if (repetitionCounter >= maxRepCount) {
            repetitionCounter = 0
            setCounter++
        }
    }

    fun wrongCount() {
        wrongCounter++
        audioPlayer.playFromFile(R.raw.wrong_count)
    }

    fun getPersonDistance(person: Person): Float? {
        val pointA = person.keyPoints[BodyPart.LEFT_SHOULDER.position]
        val pointB = person.keyPoints[BodyPart.LEFT_ELBOW.position]
        val distanceInPx = sqrt(
            (pointA.coordinate.x - pointB.coordinate.x).toDouble()
                .pow(2) + (pointA.coordinate.y - pointB.coordinate.y).toDouble().pow(2)
        )
        var sum = 0f
        var distance: Float? = null
        focalLengths?.let {
            focalLengths?.forEach { value ->
                sum += value
            }
            val avgFocalLength = (sum / focalLengths!!.size) * 0.04f
            distance = (avgFocalLength / distanceInPx.toFloat()) * 12 * 3000f
        }
        return distance?.let { it / 12 }
    }

    fun setFocalLength(lengths: FloatArray?) {
        focalLengths = lengths
    }

    fun playAudio(@RawRes resource: Int) {
        val timestamp = System.currentTimeMillis().toInt()
        if (timestamp - lastTimePlayed >= 3500) {
            lastTimePlayed = timestamp
            audioPlayer.playFromFile(resource)
        }
    }

    open fun onEvent(event: CommonInstructionEvent) {
        when (event) {
            is CommonInstructionEvent.OutSideOfBox -> playAudio(R.raw.please_stay_inside_box)
            is CommonInstructionEvent.HandIsNotStraight -> playAudio(R.raw.keep_hand_straight)
            is CommonInstructionEvent.LeftHandIsNotStraight -> playAudio(R.raw.left_hand_straight)
            is CommonInstructionEvent.RightHandIsNotStraight -> playAudio(R.raw.right_hand_straight)
            is CommonInstructionEvent.TooFarFromCamera -> playAudio(R.raw.come_forward)
        }
    }

    open fun rightExerciseCount(
        person: Person,
        canvasHeight: Int,
        canvasWidth: Int
    ) {
        if (rightCountPhases.isNotEmpty() && phaseIndex < rightCountPhases.size) {
            val phase = rightCountPhases[phaseIndex]
            val constraintSatisfied = isConstraintSatisfied(
                person,
                phase.constraints
            )

            if (VisualizationUtils.isInsideBox(
                    person,
                    canvasHeight,
                    canvasWidth
                ) && constraintSatisfied
            ) {
                if (!phaseEntered) {
                    phaseEntered = true
                    phaseEnterTime = System.currentTimeMillis()
                }
                val elapsedTime = ((System.currentTimeMillis() - phaseEnterTime) / 1000).toInt()
                downTimeCounter = phase.holdTime - elapsedTime
                if (downTimeCounter <= 0) {
                    if (phaseIndex == rightCountPhases.size - 1) {
                        phaseIndex = 0
                        wrongStateIndex = 0
                        repetitionCount()
                        playCongratulationAudio()
                    } else {
                        phaseIndex++
                        downTimeCounter = 0
                    }
                } else {
                    countDownAudio(downTimeCounter)
                }
            } else {
                downTimeCounter = 0
                phaseEntered = false
            }
            commonInstruction(
                person,
                rightCountPhases[phaseIndex].constraints,
                canvasHeight,
                canvasWidth
            )
            instruction(person)
        }
    }

    open fun wrongExerciseCount(person: Person, canvasHeight: Int, canvasWidth: Int) {}

    open fun instruction(person: Person) {}

    private fun isConstraintSatisfied(person: Person, constraints: List<Constraint>): Boolean {
        var constraintSatisfied = true
        constraints.forEach {
            when (it.type) {
                ConstraintType.ANGLE -> {
                    val angle = Utilities.angle(
                        startPoint = person.keyPoints[it.startPointIndex].toRealPoint(),
                        middlePoint = person.keyPoints[it.middlePointIndex].toRealPoint(),
                        endPoint = person.keyPoints[it.endPointIndex].toRealPoint(),
                        clockWise = it.clockWise
                    )
                    if (angle < it.minValue || angle > it.maxValue) {
                        constraintSatisfied = false
                    }
                }
                ConstraintType.LINE -> {}
            }
        }
        return constraintSatisfied
    }

    private fun sortedPhaseList(phases: List<Phase>): List<Phase> {
        val phaseIndices = mutableListOf<Int>()
        return phases.sortedBy { it.phaseNumber }.filter {
            val shouldAdd = !phaseIndices.contains(it.phaseNumber)
            phaseIndices.add(it.phaseNumber)
            shouldAdd
        }
    }

    private fun commonInstruction(
        person: Person,
        constraints: List<Constraint>,
        canvasHeight: Int,
        canvasWidth: Int
    ) {
        constraints.forEach { _ ->
            if (!VisualizationUtils.isInsideBox(
                    person,
                    canvasHeight,
                    canvasWidth
                )
            ) onEvent(CommonInstructionEvent.OutSideOfBox)
//            else if (!isLeftHandStraight(
//                    person = person,
//                    constraint = it
//                )
//            ) onEvent(CommonInstructionEvent.LeftHandIsNotStraight)
//            else if (!isRightHandStraight(
//                    person = person,
//                    constraint = it
//                )
//            ) onEvent(CommonInstructionEvent.RightHandIsNotStraight)
        }
        getPersonDistance(person)?.let {
            if (it > 13) {
                onEvent(CommonInstructionEvent.TooFarFromCamera)
            }
        }
    }

    private fun countDownAudio(count: Int) {
        if (previousCountDown != count && count > 0) {
            previousCountDown = count
            countDownAudioPlayerPlayer.play(count)
        }
    }

    private fun playCongratulationAudio() {
        if (setCounter == maxSetCount) {
            audioPlayer.playFromFile(R.raw.congratulate_patient)
        }
    }

    sealed class CommonInstructionEvent {
        object OutSideOfBox : CommonInstructionEvent()
        object HandIsNotStraight : CommonInstructionEvent()
        object LeftHandIsNotStraight : CommonInstructionEvent()
        object RightHandIsNotStraight : CommonInstructionEvent()
        object TooFarFromCamera : CommonInstructionEvent()
    }

}