package org.tensorflow.lite.examples.poseestimation.exercise.home

import android.content.Context
import android.media.MediaPlayer
import android.util.Log
import android.widget.Toast
import androidx.annotation.RawRes
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.tensorflow.lite.examples.poseestimation.R
import org.tensorflow.lite.examples.poseestimation.api.IExerciseService
import org.tensorflow.lite.examples.poseestimation.api.request.ExerciseData
import org.tensorflow.lite.examples.poseestimation.api.request.ExerciseRequestPayload
import org.tensorflow.lite.examples.poseestimation.api.response.KeyPointRestrictions
import org.tensorflow.lite.examples.poseestimation.core.AudioPlayer
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

    companion object {
        private const val SET_INTERVAL = 7000L
        const val LEAN_LEFT = "lean left"
        const val LEAN_RIGHT = "lean right"
        const val RETURN = "return"
        const val FINISH = "finish"
        const val CONGRATS = "congrats"
        const val TAKE_REST = "take rest"
        const val START = "start"
        const val START_AGAIN = "start again"
        const val SET_1 = "set 1"
        const val SET_2 = "set 2"
        const val SET_3 = "set 3"
        const val SET_4 = "set 4"
        const val SET_5 = "set 5"
        const val SET_6 = "set 6"
        const val SET_7 = "set 7"
        const val SET_8 = "set 8"
        const val SET_9 = "set 9"
        const val SET_10 = "set 10"
        const val SET_COMPLETED = "set completed"
        const val GET_READY = "get ready"
        const val SQUAT_DOWN = "squat down"
        const val BEND_LEFT_KNEE = "bend left knee"
        const val BEND_RIGHT_KNEE = "bend right knee"
        const val BEND_BOTH_KNEES = "bend both knees"
        const val RELAX = "relax"
        const val PUSH = "push"
        const val PULL_YOUR_ELBOWS_BACK = "pull your elbows back"
        const val CRUNCH_UP = "crunch up"
        const val BACK_DOWN = "back down"
        const val PLANK_UP = "plank up"
        const val ARM_RAISE = "arm raise"
        const val RIGHT_LEG_KICK_BACKWARD = "right leg kick backward"
        const val LEFT_LEG_KICK_BACKWARD = "left leg kick backward"
        const val RIGHT_LEG_KICK_LATERAL = "right leg kick lateral"
        const val LEFT_LEG_KICK_LATERAL = "left leg kick lateral"
        const val LEAN_FORWARD = "lean forward"
        const val LEAN_BACKWARD = "lean backward"
        const val BOTH_LEGS_FALL_OUT = "both legs fall out"
        const val EXTEND_RIGHT_KNEE = "extend right knee"
        const val EXTEND_LEFT_KNEE = "extend left knee"
        const val LIFT_HIP = "lift hip"
        const val LIFT_RIGHT_ARM = "lift right arm"
        const val LIFT_LEFT_ARM = "lift left arm"
        const val LIFT_RIGHT_LEG = "lift right leg"
        const val LIFT_LEFT_LEG = "lift left leg"
        const val LIFT_RIGHT_KNEE = "lift right knee"
        const val LIFT_LEFT_KNEE = "lift left knee"
        const val LEAN_BACKWARD_HOLD = "lean backward hold"
        const val LEAN_FORWARD_HOLD = "lean forward hold"
        const val LEAN_LEFT_HOLD = "lean left hold"
        const val LEAN_RIGHT_HOLD = "lean right hold"
        const val SQUAT_DOWN_HOLD = "squat down hold"
        const val PLANK_UP_HOLD = "plank up hold"
        const val QUADRUPED_HOLD = "quadruped hold"
        const val LIFT_HIP_HOLD = "lift hip hold"
        const val LIFT_RIGHT_LEG_HOLD = "lift right leg hold"
        const val LIFT_LEFT_LEG_HOLD = "lift left leg hold"
        const val BEND_BOTH_KNEES_HOLD = "bend both knees hold"
        const val PRESS_UP = "press up"
        const val PRESS_UP_HOLD = "press up hold"
        const val CURL_UP_RIGHT_ELBOW = "curl up right elbow"
        const val CURL_UP_LEFT_ELBOW = "curl up left elbow"
        const val CURL_UP = "curl up"
        const val LUNGE_LEFT_LEG = "lunge left leg"
        const val LUNGE_RIGHT_LEG = "lunge right leg"
        const val ARMS_UP = "arms up"
        const val ARMS_DOWN = "arms down"
        const val LUNGE_LEFT_LEG_HOLD = "lunge left leg hold"
        const val LUNGE_RIGHT_LEG_HOLD = "lunge right leg hold"
        const val REACH_ANKLE_HOLD = "reach ankle hold"
        const val LEAN_FORWARD_RIGHT_HOLD = "lean forward right hold"
        const val LEAN_FORWARD_LEFT_HOLD = "lean forward left hold"
        const val STAND_UP = "stand up"
        const val SIT_DOWN = "sit down"
        const val SLIGHTLY_BEND_BOTH_KNEES = "slightly bend both knees"
        const val PULL_UP = "pull up"
        const val PUSH_UP = "push up"
        const val PULL_DOWN = "pull down"
        const val LEFT_ARM_RIGHT_LEG_UP = "left arm right leg up"
        const val RIGHT_ARM_LEFT_LEG_UP = "right arm left leg up"
        const val LEFT_ARM_RIGHT_LEG_UP_HOLD = "left arm right leg up hold"
        const val RIGHT_ARM_LEFT_LEG_UP_HOLD = "right arm left leg up hold"
        const val PRONE_ON_ELBOWS = "prone on elbows"
        const val PRONE_ON_ELBOWS_HOLD = "prone on elbows hold"
        const val EXTEND_BOTH_KNEES = "extend both knees"
        const val LIFT_LEFT_ARM_FORWARD = "lift left arm forward"
        const val LIFT_RIGHT_ARM_FORWARD = "lift right arm forward"
        const val LIFT_LEFT_ARM_BACKWARD = "lift left arm backward"
        const val LIFT_RIGHT_ARM_BACKWARD = "lift right arm backward"
        const val LIFT_RIGHT_KNEE_TO_CHEST_HOLD = "lift right knee to chest hold"
        const val LIFT_LEFT_KNEE_TO_CHEST_HOLD = "lift left knee to chest hold"
        const val LIFT_BOTH_KNEES_TO_CHEST_HOLD = "lift both knees to chest hold"
        const val THUMB_UP_LIFT_RIGHT_ARM_FORWARD = "thumb up lift right arm forward"
        const val THUMB_UP_LIFT_LEFT_ARM_FORWARD = "thumb up lift left arm forward"
        const val THUMBS_UP_ARMS_RAISE = "thumbs up arms raise"
        const val STAND_AGAINST_THE_WALL = "stand against the wall"
        const val LIFT_LEFT_HAND_HOLD = "lift left hand hold"
        const val LIFT_RIGHT_HAND_HOLD = "lift right hand hold"
        const val STEP_RIGHT_LEG_FORWARD_HOLD = "step right leg forward hold"
        const val STEP_LEFT_LEG_FORWARD_HOLD = "step left leg forward hold"
        const val STEP_LEFT_LEG_FORWARD = "step left leg forward"
        const val STEP_RIGHT_LEG_FORWARD = "step right leg forward"
        const val BEND_NECK_FORWARD = "bend neck forward"
        const val BEND_NECK_BACKWARD = "bend neck backward"
        const val JUMP_FORWARD = "jump forward"
        const val JUMP_BACKWARD = "jump backward"
        const val STEP_LEFT_LEG_BACKWARD_HOLD = "step left leg backward hold"
        const val STEP_RIGHT_LEG_BACKWARD_HOLD = "step right leg backward hold"
        const val STEP_LEFT_LEG_BACKWARD = "step left leg backward"
        const val STEP_RIGHT_LEG_BACKWARD = "step right leg backward"
        const val ROTATE_TO_LEFT = "rotate to left"
        const val ROTATE_TO_RIGHT = "rotate to right"
        const val ROTATE_TO_LEFT_HOLD = "rotate to left hold"
        const val ROTATE_TO_RIGHT_HOLD = "rotate to right hold"
        const val MOVE_HEAD_FORWARD = "move head forward"
        const val MOVE_HEAD_BACKWARD = "move head backward"
        const val BEND_FORWARD = "bend forward"
        const val BEND_BACKWARD = "bend backward"
        const val BEND_FORWARD_RESIST_HOLD = "bend forward resist hold"
        const val BEND_BACKWARD_RESIST_HOLD = "bend backward resist hold"
        const val BEND_LEFT_RESIST_HOLD = "bend left resist hold"
        const val BEND_RIGHT_RESIST_HOLD = "bend right resist hold"
        const val LEFT_HAND_ON_RIGHT_ARM_PUSH_HOLD = "left hand on right arm push hold"
        const val RIGHT_HAND_ON_LEFT_ARM_PUSH_HOLD = "right hand on left arm push hold"
        const val THUMBS_OUT_ARMS_RAISE = "thumbs out arms raise"
        const val THUMBS_OUT_ARMS_RAISE_HOLD = "thumbs out arms raise hold"
        const val THUMB_OUT_MOVE_RIGHT_ARM_UP_LATERAL = "thumb out move right arm up lateral"
        const val THUMB_OUT_MOVE_LEFT_ARM_LATERAL = "thumb out move left arm lateral"
        const val LEFT_HAND_PUSH_HEAD_BACKWARD_RESIST_HOLD =
            "left hand push head backward resist hold"
        const val BOTH_HANDS_PUSH_HEAD_FOWARD_RESIST_HOLD =
            "both hands push head foward resist hold"
        const val LEFT_HAND_PUSH_HEAD_TO_THE_RIGHT_RESIST_HOLD =
            "left hand push head to the right resist hold"
        const val RIGHT_HAND_PUSH_HEAD_TO_THE_LEFT_RESIST_HOLD =
            "right hand push head to the left resist hold"
        const val BEND_RIGHT = "bend right"
        const val BEND_LEFT = "bend left"
        const val HANDS_BACK_ON_HEAD_HOLD = "hands back on head hold"
        const val HANDS_BACK_ON_HEAD_RESIST_HOLD = "hands back on head resist hold"
        const val BOTH_KNEES_FALL_OUT_TO_THE_LEFT = "both knees fall out to the left"
        const val BOTH_KNEES_FALL_OUT_TO_THE_RIGHT = "both knees fall out to the right"
        const val LOOK_DOWN = "look down"
        const val LOOK_UP = "look up"
        const val BEND_NECK_BACKWARD_HOLD = "bend neck backward hold"
        const val BEND_NECK_FORWARD_HOLD = "bend neck forward hold"
        const val BEND_NECK_TO_THE_LEFT = "bend neck to the left"
        const val BEND_NECK_TO_THE_LEFT_HOLD = "bend neck to the left hold"
        const val BEND_NECK_TO_THE_RIGHT = "bend neck to the right"
        const val BEND_NECK_TO_THE_RIGHT_HOLD = "bend neck to the right hold"
        const val BOTH_HANDS_ON_BACK_OF_HEAD_HOLD = "both hands on back of head hold"
        const val LEFT_LEG_KICK_TO_YOUR_SIDE = "left leg kick to your side"
        const val LOOK_DOWN_HOLD = "look down hold"
        const val LOOK_TO_THE_LEFT = "look to the left"
        const val LOOK_TO_THE_LEFT_HOLD = "look to the left hold"
        const val LOOK_TO_THE_RIGHT = "look to the right"
        const val LOOK_TO_THE_RIGHT_HOLD = "look to the right hold"
        const val LOOK_UP_HOLD = "look up hold"
        const val PUSH_FORWARD_HOLD = "push forward hold"
        const val RIGHT_LEG_KICK_TO_YOUR_SIDE = "right leg kick to your side"
        const val EXTEND_LEFT_ARM = "extend left arm"
        const val EXTEND_LEFT_ARM_HOLD = "extend left arm hold"
        const val EXTEND_RIGHT_ARM = "extend right arm"
        const val EXTEND_RIGHT_ARM_HOLD = "extend right arm hold"
        const val HALF_KNEELING_ON_LEFT = "half kneeling on left"
        const val HALF_KNEELING_ON_RIGHT = "half kneeling on right"
        const val KNEEL_UPRIGHT = "kneel upright"
        const val LEFT_KNEE_DOWN = "left knee down"
        const val PLACE_LEFT_FOOT_ON_FLOOR = "place left foot on floor"
        const val PLACE_RIGHT_FOOT_ON_FLOOR = "place right foot on floor"
        const val RIGHT_KNEE_DOWN = "right knee down"
        const val SQUAT_KNEELING = "squat kneeling"
    }

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
    private var takingRest = false
    private val one = MediaPlayer.create(context, R.raw.one)
    private val two = MediaPlayer.create(context, R.raw.two)
    private val three = MediaPlayer.create(context, R.raw.three)
    private val four = MediaPlayer.create(context, R.raw.four)
    private val five = MediaPlayer.create(context, R.raw.five)
    private val six = MediaPlayer.create(context, R.raw.six)
    private val seven = MediaPlayer.create(context, R.raw.seven)
    private val eight = MediaPlayer.create(context, R.raw.eight)
    private val nine = MediaPlayer.create(context, R.raw.nine)
    private val ten = MediaPlayer.create(context, R.raw.ten)
    private val eleven = MediaPlayer.create(context, R.raw.eleven)
    private val twelve = MediaPlayer.create(context, R.raw.twelve)
    private val thirteen = MediaPlayer.create(context, R.raw.thirteen)
    private val fourteen = MediaPlayer.create(context, R.raw.fourteen)
    private val fifteen = MediaPlayer.create(context, R.raw.fifteen)
    private val sixteen = MediaPlayer.create(context, R.raw.sixteen)
    private val seventeen = MediaPlayer.create(context, R.raw.seventeen)
    private val eighteen = MediaPlayer.create(context, R.raw.eightteen)
    private val nineteen = MediaPlayer.create(context, R.raw.nineteen)
    private val twenty = MediaPlayer.create(context, R.raw.twenty)
    private val rightCount = MediaPlayer.create(context, R.raw.right_count)
    private val instructions = mutableListOf(
        Instruction(
            text = "start",
            player = MediaPlayer.create(context, R.raw.start)
        ),
        Instruction(
            text = "return",
            player = MediaPlayer.create(context, R.raw.return_audio)
        )
    )

    fun addInstruction(instruction: Instruction) {
        val alreadyExist = instructions.find {
            it.text.lowercase() == instruction.text.lowercase()
        } == null
        if (!alreadyExist) {
            instructions.add(instruction)
        }
    }

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
            .baseUrl(Utilities.getUrl(tenant).getExerciseConstraintsURL)
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
                if (responseBody == null || responseBody.isEmpty()) {
                    Toast.makeText(
                        context,
                        "Failed to get necessary constraints for this exercise and got empty response. So, this exercise can't be performed now!",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    if (responseBody[0].KeyPointsRestrictionGroup.isNotEmpty()) {
                        playInstruction(
                            firstDelay = 5000L,
                            firstInstruction = GET_READY,
                            secondDelay = 5000L,
                            secondInstruction = START,
                            shouldTakeRest = true
                        )
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

    fun playInstruction(
        firstDelay: Long,
        firstInstruction: String,
        secondDelay: Long = 0L,
        secondInstruction: String? = null,
        shouldTakeRest: Boolean = false
    ) {
        if (shouldTakeRest) takingRest = true
        CoroutineScope(Dispatchers.Main).launch {
            delay(firstDelay)
            playText(firstInstruction)
            delay(secondDelay)
            secondInstruction?.let {
                playText(it)
            }
            if (shouldTakeRest) takingRest = false
        }
    }

    fun repetitionCount() {
        repetitionCounter++
        audioPlayer.playFromFile(R.raw.right_count)
        if (repetitionCounter >= maxRepCount) {
            repetitionCounter = 0
            setCounter++
            if (setCounter == maxSetCount) {
                playText(FINISH)
                CoroutineScope(Dispatchers.Main).launch {
                    playInstruction(
                        firstDelay = 1000L,
                        firstInstruction = CONGRATS
                    )
                }
            } else {
                playInstruction(
                    firstDelay = 0L,
                    firstInstruction = setCountText(setCounter),
                    secondDelay = SET_INTERVAL,
                    secondInstruction = START_AGAIN,
                    shouldTakeRest = true
                )
            }
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
        if (rightCountPhases.isNotEmpty() && phaseIndex < rightCountPhases.size && !takingRest) {
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
                    } else {
                        phaseIndex++
                        rightCountPhases[phaseIndex].phaseDialogue?.let {
                            playInstruction(firstDelay = 500L, firstInstruction = it)
                        }
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
            exerciseInstruction(person)
        }
    }

    open fun wrongExerciseCount(person: Person, canvasHeight: Int, canvasWidth: Int) {}

    open fun exerciseInstruction(person: Person) {}

    private fun playText(text: String) {
        val instruction = instructions.find {
            it.text.lowercase() == text.lowercase()
        }
        instruction?.player?.start()
    }

    private fun setCountText(count: Int): String = when (count) {
        1 -> SET_1
        2 -> SET_2
        3 -> SET_3
        4 -> SET_4
        5 -> SET_5
        6 -> SET_6
        7 -> SET_7
        8 -> SET_8
        9 -> SET_9
        10 -> SET_10
        else -> SET_COMPLETED
    }

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
                        Log.d("CountingIssue", "${it.minValue}< $angle < ${it.maxValue}")
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
            when (count) {
                1 -> one.start()
                2 -> two.start()
                3 -> three.start()
                4 -> four.start()
                5 -> five.start()
                6 -> six.start()
                7 -> seven.start()
                8 -> eight.start()
                9 -> nine.start()
                10 -> ten.start()
                11 -> eleven.start()
                12 -> twelve.start()
                13 -> thirteen.start()
                14 -> fourteen.start()
                15 -> fifteen.start()
                16 -> sixteen.start()
                17 -> seventeen.start()
                18 -> eighteen.start()
                19 -> nineteen.start()
                20 -> twenty.start()
                else -> rightCount.start()
            }
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