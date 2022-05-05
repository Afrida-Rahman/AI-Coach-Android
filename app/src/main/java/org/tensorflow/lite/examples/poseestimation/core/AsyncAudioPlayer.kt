package org.tensorflow.lite.examples.poseestimation.core

import android.content.Context
import android.media.MediaPlayer
import org.tensorflow.lite.examples.poseestimation.R
import org.tensorflow.lite.examples.poseestimation.domain.model.Instruction

class AsyncAudioPlayer(private val context: Context) {

    companion object {
        const val ONE = "1"
        const val TWO = "2"
        const val THREE = "3"
        const val FOUR = "4"
        const val FIVE = "5"
        const val SIX = "6"
        const val SEVEN = "7"
        const val EIGHT = "8"
        const val NINE = "9"
        const val TEN = "10"
        const val ELEVEN = "11"
        const val TWELVE = "12"
        const val THIRTEEN = "13"
        const val FOURTEEN = "14"
        const val FIFTEEN = "15"
        const val SIXTEEN = "16"
        const val SEVENTEEN = "17"
        const val EIGHTEEN = "18"
        const val NINETEEN = "19"
        const val TWENTY = "20"
        const val BEEP = "beep"
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
        const val BOTH_KNEES_ON_FLOOR = "both knees on floor"
        const val LEFT_SIDE_KICK = "left side kick"
        const val RIGHT_SIDE_KICK = "right side kick"
        const val LEFT_ARM_STEP_BACKWARD = "left arm step backward"
        const val LEFT_ARM_STEP_FORWARD = "left arm step forward"
        const val LEFT_KNEE_STEP_BACKWARD = "left knee step backward"
        const val LEFT_KNEE_STEP_FORWARD = "left knee step forward"
        const val RIGHT_ARM_STEP_BACKWARD = "right arm step backward"
        const val RIGHT_ARM_STEP_FORWARD = "right arm step forward"
        const val RIGHT_KNEE_STEP_BACKWARD = "right knee step backward"
        const val RIGHT_KNEE_STEP_FORWARD = "right knee step forward"
        const val PAUSE = "pause"
        const val BOTH_HEEL_SLIDE = "both heel slide"
        const val LEFT_ARM_RIGHT_LEG_DOWN = "left arm right leg down"
        const val LEFT_HEEL_SLIDE = "left heel slide"
        const val RIGHT_ARM_LEFT_LEG_DOWN = "right arm left leg down"
        const val RIGHT_HEEL_SLIDE = "right heel slide"
        const val PULL_IN = "pull in"
        const val PULL_OUT = "pull out"
        const val TURN_TO_THE_OTHER_SIDE = "turn to the other side"
        const val HANDS_ON_THE_BALL = "hands on the ball"
        const val LEFT_FOOT_ON_RIGHT_KNEE = "left foot on right knee"
        const val LEFT_HAND_ON_THE_BALL = "left hand on the ball"
        const val LEFT_HEEL_DOWN = "left heel down"
        const val PULL_LEGS_BACK_HOLD = "pull legs back hold"
        const val RIGHT_FOOT_ON_LEFT_KNEE = "right foot on left knee"
        const val RIGHT_HAND_ON_THE_BALL = "right hand on the ball"
        const val RIGHT_HEEL_DOWN = "right heel down"
        const val LAY_ON_YOUR_BACK = "lay on your back"
        const val KNEES_BENT = "knees bent"
        const val PROP_YOUR_ELBOWS = "prop your elbows"
        const val PROP_ONTO_YOUR_HANDS_AND_KNEES = "prop onto your hands and knees"
        const val LEFT_LEG_STRAIGHT = "left leg straight"
        const val RIGHT_LEG_STRAIGHT = "right leg straight"
        const val LEFT_KNEE_STRAIGHT = "left knee straight"
        const val RIGHT_KNEE_STRAIGHT = "right knee straight"
        const val LEFT_FOOT_STEP_FORWARD_RIGHT_KNEE_DOWN = "left foot step forward right knee down"
        const val RIGHT_FOOT_STEP_FORWARD_LEFT_KNEE_DOWN = "right foot step forward left knee down"
        const val HANDS_AT_THE_BACK_OF_HEAD_HOLD = "hands at the back of head hold"
        const val PUSH_TO_THE_SIDE_HOLD = "push to the side hold"
        const val LIFT_LEFT_KNEE_TO_CHEST = "lift left knee to chest"
        const val LIFT_RIGHT_KNEE_TO_CHEST = "lift right knee to chest"
        const val EXTEND_LEFT_KNEE_HOLD = "extend left knee hold"
        const val EXTEND_RIGHT_KNEE_HOLD = "extend right knee hold"
        const val LIFT_LEFT_LEG_UP = "lift left leg up"
        const val LIFT_RIGHT_LEG_UP = "lift right leg up"
    }

    fun playText(instruction: Instruction) {
        instruction.player?.start()
    }

    fun generateInstruction(text: String): Instruction = when (text.lowercase()) {
        ONE -> Instruction(text = text, player = MediaPlayer.create(context, R.raw.one))
        TWO -> Instruction(text = text, player = MediaPlayer.create(context, R.raw.two))
        THREE -> Instruction(text = text, player = MediaPlayer.create(context, R.raw.three))
        FOUR -> Instruction(text = text, player = MediaPlayer.create(context, R.raw.four))
        FIVE -> Instruction(text = text, player = MediaPlayer.create(context, R.raw.five))
        SIX -> Instruction(text = text, player = MediaPlayer.create(context, R.raw.six))
        SEVEN -> Instruction(text = text, player = MediaPlayer.create(context, R.raw.seven))
        EIGHT -> Instruction(text = text, player = MediaPlayer.create(context, R.raw.eight))
        NINE -> Instruction(text = text, player = MediaPlayer.create(context, R.raw.nine))
        TEN -> Instruction(text = text, player = MediaPlayer.create(context, R.raw.ten))
        ELEVEN -> Instruction(text = text, player = MediaPlayer.create(context, R.raw.eleven))
        TWELVE -> Instruction(text = text, player = MediaPlayer.create(context, R.raw.twelve))
        THIRTEEN -> Instruction(text = text, player = MediaPlayer.create(context, R.raw.thirteen))
        FOURTEEN -> Instruction(text = text, player = MediaPlayer.create(context, R.raw.fourteen))
        FIFTEEN -> Instruction(text = text, player = MediaPlayer.create(context, R.raw.fifteen))
        SIXTEEN -> Instruction(text = text, player = MediaPlayer.create(context, R.raw.sixteen))
        SEVENTEEN -> Instruction(text = text, player = MediaPlayer.create(context, R.raw.seventeen))
        EIGHTEEN -> Instruction(text = text, player = MediaPlayer.create(context, R.raw.eightteen))
        NINETEEN -> Instruction(text = text, player = MediaPlayer.create(context, R.raw.nineteen))
        TWENTY -> Instruction(text = text, player = MediaPlayer.create(context, R.raw.twenty))
        LEAN_LEFT -> Instruction(text = text, player = MediaPlayer.create(context, R.raw.lean_left))
        LEAN_RIGHT -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.lean_right)
        )
        RETURN -> Instruction(text = text, player = MediaPlayer.create(context, R.raw.return_audio))
        FINISH -> Instruction(text = text, player = MediaPlayer.create(context, R.raw.finish_audio))
        CONGRATS -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.congratulate_patient)
        )
        TAKE_REST -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.take_some_time_to_rest)
        )
        START -> Instruction(text = text, player = MediaPlayer.create(context, R.raw.start))
        START_AGAIN -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.start_again)
        )
        SET_1 -> Instruction(text = text, player = MediaPlayer.create(context, R.raw.first_set))
        SET_2 -> Instruction(text = text, player = MediaPlayer.create(context, R.raw.second_set))
        SET_3 -> Instruction(text = text, player = MediaPlayer.create(context, R.raw.third_set))
        SET_4 -> Instruction(text = text, player = MediaPlayer.create(context, R.raw.fourth_set))
        SET_5 -> Instruction(text = text, player = MediaPlayer.create(context, R.raw.fifth_set))
        SET_6 -> Instruction(text = text, player = MediaPlayer.create(context, R.raw.sixth_set))
        SET_7 -> Instruction(text = text, player = MediaPlayer.create(context, R.raw.seventh_set))
        SET_8 -> Instruction(text = text, player = MediaPlayer.create(context, R.raw.eighth_set))
        SET_9 -> Instruction(text = text, player = MediaPlayer.create(context, R.raw.ninth_set))
        SET_10 -> Instruction(text = text, player = MediaPlayer.create(context, R.raw.tenth_set))
        SET_COMPLETED -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.set_complete)
        )
        GET_READY -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.get_ready)
        )
        SQUAT_DOWN -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.squat_down)
        )
        BEND_LEFT_KNEE -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.bend_left_knee)
        )
        BEND_RIGHT_KNEE -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.bend_right_knee)
        )
        BEND_BOTH_KNEES -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.bend_both_knees)
        )
        RELAX -> Instruction(text = text, player = MediaPlayer.create(context, R.raw.relax))
        PUSH -> Instruction(text = text, player = MediaPlayer.create(context, R.raw.push))
        PULL_YOUR_ELBOWS_BACK -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.pull_your_elbows_back)
        )
        CRUNCH_UP -> Instruction(text = text, player = MediaPlayer.create(context, R.raw.crunch_up))
        BACK_DOWN -> Instruction(text = text, player = MediaPlayer.create(context, R.raw.back_down))
        PLANK_UP -> Instruction(text = text, player = MediaPlayer.create(context, R.raw.plank_up))
        ARM_RAISE -> Instruction(text = text, player = MediaPlayer.create(context, R.raw.arm_raise))
        RIGHT_LEG_KICK_BACKWARD -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.right_leg_kick_backward)
        )
        LEFT_LEG_KICK_BACKWARD -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.left_leg_kick_backward)
        )
        RIGHT_LEG_KICK_LATERAL -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.right_leg_kick_lateral)
        )
        LEFT_LEG_KICK_LATERAL -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.left_leg_kick_lateral)
        )
        LEAN_FORWARD -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.lean_forward)
        )
        LEAN_BACKWARD -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.lean_backward)
        )
        BOTH_LEGS_FALL_OUT -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.both_legs_fall_out)
        )
        EXTEND_RIGHT_KNEE -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.extend_right_knee)
        )
        EXTEND_LEFT_KNEE -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.extend_left_knee)
        )
        LIFT_HIP -> Instruction(text = text, player = MediaPlayer.create(context, R.raw.lift_hip))
        LIFT_RIGHT_ARM -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.lift_right_arm)
        )
        LIFT_LEFT_ARM -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.lift_left_arm)
        )
        LIFT_RIGHT_LEG -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.lift_right_leg)
        )
        LIFT_LEFT_LEG -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.lift_left_leg)
        )
        LIFT_RIGHT_KNEE -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.lift_right_knee)
        )
        LIFT_LEFT_KNEE -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.lift_left_knee)
        )
        LEAN_BACKWARD_HOLD -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.lean_backward_hold)
        )
        LEAN_FORWARD_HOLD -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.lean_forward_hold)
        )
        LEAN_LEFT_HOLD -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.lean_left_hold)
        )
        LEAN_RIGHT_HOLD -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.lean_right_hold)
        )
        SQUAT_DOWN_HOLD -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.squat_down_hold)
        )
        PLANK_UP_HOLD -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.plank_up_hold)
        )
        QUADRUPED_HOLD -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.quadruped_hold)
        )
        LIFT_HIP_HOLD -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.lift_hip_hold)
        )
        LIFT_RIGHT_LEG_HOLD -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.lift_right_leg_hold)
        )
        LIFT_LEFT_LEG_HOLD -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.lift_left_leg_hold)
        )
        BEND_BOTH_KNEES_HOLD -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.bend_both_knees_hold)
        )
        PRESS_UP -> Instruction(text = text, player = MediaPlayer.create(context, R.raw.press_up))
        PRESS_UP_HOLD -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.press_up_hold)
        )
        CURL_UP_RIGHT_ELBOW -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.curl_up_right_elbow)
        )
        CURL_UP_LEFT_ELBOW -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.curl_up_left_elbow)
        )
        CURL_UP -> Instruction(text = text, player = MediaPlayer.create(context, R.raw.curl_up))
        LUNGE_LEFT_LEG -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.lunge_left_leg)
        )
        LUNGE_RIGHT_LEG -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.lunge_right_leg)
        )
        ARMS_UP -> Instruction(text = text, player = MediaPlayer.create(context, R.raw.arms_up))
        ARMS_DOWN -> Instruction(text = text, player = MediaPlayer.create(context, R.raw.arms_down))
        LUNGE_LEFT_LEG_HOLD -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.lunge_left_leg_hold)
        )
        LUNGE_RIGHT_LEG_HOLD -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.lunge_right_leg_hold)
        )
        REACH_ANKLE_HOLD -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.reach_ankle_hold)
        )
        LEAN_FORWARD_RIGHT_HOLD -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.lean_forward_right_hold)
        )
        LEAN_FORWARD_LEFT_HOLD -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.lean_forward_left_hold)
        )
        STAND_UP -> Instruction(text = text, player = MediaPlayer.create(context, R.raw.stand_up))
        SIT_DOWN -> Instruction(text = text, player = MediaPlayer.create(context, R.raw.sit_down))
        SLIGHTLY_BEND_BOTH_KNEES -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.slightly_bend_both_knees)
        )
        PULL_UP -> Instruction(text = text, player = MediaPlayer.create(context, R.raw.pull_up))
        PUSH_UP -> Instruction(text = text, player = MediaPlayer.create(context, R.raw.push_up))
        PULL_DOWN -> Instruction(text = text, player = MediaPlayer.create(context, R.raw.pull_down))
        LEFT_ARM_RIGHT_LEG_UP -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.left_arm_right_leg_up)
        )
        RIGHT_ARM_LEFT_LEG_UP -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.right_arm_left_leg_up)
        )
        LEFT_ARM_RIGHT_LEG_UP_HOLD -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.left_arm_right_leg_up_hold)
        )
        RIGHT_ARM_LEFT_LEG_UP_HOLD -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.right_arm_left_leg_up_hold)
        )
        PRONE_ON_ELBOWS -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.prone_on_elbows)
        )
        PRONE_ON_ELBOWS_HOLD -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.prone_on_elbows_hold)
        )
        EXTEND_BOTH_KNEES -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.extend_both_knees)
        )
        LIFT_LEFT_ARM_FORWARD -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.lift_left_arm_forward)
        )
        LIFT_RIGHT_ARM_FORWARD -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.lift_right_arm_forward)
        )
        LIFT_LEFT_ARM_BACKWARD -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.lift_left_arm_backward)
        )
        LIFT_RIGHT_ARM_BACKWARD -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.lift_right_arm_backward)
        )
        LIFT_RIGHT_KNEE_TO_CHEST_HOLD -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.lift_right_knee_to_chest_hold)
        )
        LIFT_LEFT_KNEE_TO_CHEST_HOLD -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.lift_left_knee_to_chest_hold)
        )
        LIFT_BOTH_KNEES_TO_CHEST_HOLD -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.lift_both_knees_to_chest_hold)
        )
        THUMB_UP_LIFT_RIGHT_ARM_FORWARD -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.thumb_up_lift_right_arm_forward)
        )
        THUMB_UP_LIFT_LEFT_ARM_FORWARD -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.thumb_up_lift_left_arm_forward)
        )
        THUMBS_UP_ARMS_RAISE -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.thumbs_up_arms_raise)
        )
        STAND_AGAINST_THE_WALL -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.stand_against_the_wall)
        )
        LIFT_LEFT_HAND_HOLD -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.lift_left_hand_hold)
        )
        LIFT_RIGHT_HAND_HOLD -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.lift_right_hand_hold)
        )
        STEP_RIGHT_LEG_FORWARD_HOLD -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.step_right_leg_forward_hold)
        )
        STEP_LEFT_LEG_FORWARD_HOLD -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.step_left_leg_forward_hold)
        )
        STEP_LEFT_LEG_FORWARD -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.step_left_leg_forward)
        )
        STEP_RIGHT_LEG_FORWARD -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.step_right_leg_forward)
        )
        BEND_NECK_FORWARD -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.bend_neck_forward)
        )
        BEND_NECK_BACKWARD -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.bend_neck_backward)
        )
        JUMP_FORWARD -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.jump_forward)
        )
        JUMP_BACKWARD -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.jump_backward)
        )
        STEP_LEFT_LEG_BACKWARD_HOLD -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.step_left_leg_backward_hold)
        )
        STEP_RIGHT_LEG_BACKWARD_HOLD -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.step_right_leg_backward_hold)
        )
        STEP_LEFT_LEG_BACKWARD -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.step_left_leg_backward)
        )
        STEP_RIGHT_LEG_BACKWARD -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.step_right_leg_backward)
        )
        ROTATE_TO_LEFT -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.rotate_to_left)
        )
        ROTATE_TO_RIGHT -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.rotate_to_right)
        )
        ROTATE_TO_LEFT_HOLD -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.rotate_to_left_hold)
        )
        ROTATE_TO_RIGHT_HOLD -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.rotate_to_right_hold)
        )
        MOVE_HEAD_FORWARD -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.move_head_forward)
        )
        MOVE_HEAD_BACKWARD -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.move_head_backward)
        )
        BEND_FORWARD -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.bend_forward)
        )
        BEND_BACKWARD -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.bend_backward)
        )
        BEND_FORWARD_RESIST_HOLD -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.bend_forward_resist_hold)
        )
        BEND_BACKWARD_RESIST_HOLD -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.bend_backward_resist_hold)
        )
        BEND_LEFT_RESIST_HOLD -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.bend_left_resist_hold)
        )
        BEND_RIGHT_RESIST_HOLD -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.bend_right_resist_hold)
        )
        LEFT_HAND_ON_RIGHT_ARM_PUSH_HOLD -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.left_hand_on_right_arm_push_hold)
        )
        RIGHT_HAND_ON_LEFT_ARM_PUSH_HOLD -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.right_hand_on_left_arm_push_hold)
        )
        THUMBS_OUT_ARMS_RAISE -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.thumbs_out_arms_raise)
        )
        THUMBS_OUT_ARMS_RAISE_HOLD -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.thumbs_out_arms_raise_hold)
        )
        THUMB_OUT_MOVE_RIGHT_ARM_UP_LATERAL -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.thumb_out_move_right_arm_up_lateral)
        )
        THUMB_OUT_MOVE_LEFT_ARM_LATERAL -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.thumb_out_move_left_arm_lateral)
        )
        LEFT_HAND_PUSH_HEAD_BACKWARD_RESIST_HOLD -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.left_hand_push_head_backward_resist_hold)
        )
        BOTH_HANDS_PUSH_HEAD_FOWARD_RESIST_HOLD -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.both_hands_push_head_foward_resist_hold)
        )
        LEFT_HAND_PUSH_HEAD_TO_THE_RIGHT_RESIST_HOLD -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.left_hand_push_head_to_the_right_resist_hold)
        )
        RIGHT_HAND_PUSH_HEAD_TO_THE_LEFT_RESIST_HOLD -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.right_hand_push_head_to_the_left_resist_hold)
        )
        BEND_RIGHT -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.bend_right)
        )
        BEND_LEFT -> Instruction(text = text, player = MediaPlayer.create(context, R.raw.bend_left))
        HANDS_BACK_ON_HEAD_HOLD -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.hands_back_on_head_hold)
        )
        HANDS_BACK_ON_HEAD_RESIST_HOLD -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.hands_back_on_head_resist_hold)
        )
        BOTH_KNEES_FALL_OUT_TO_THE_LEFT -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.both_knees_fall_out_to_the_left)
        )
        BOTH_KNEES_FALL_OUT_TO_THE_RIGHT -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.both_knees_fall_out_to_the_right)
        )
        LOOK_DOWN -> Instruction(text = text, player = MediaPlayer.create(context, R.raw.look_down))
        LOOK_UP -> Instruction(text = text, player = MediaPlayer.create(context, R.raw.look_up))
        BEND_NECK_BACKWARD_HOLD -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.bend_neck_backward_hold)
        )
        BEND_NECK_FORWARD_HOLD -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.bend_neck_forward_hold)
        )
        BEND_NECK_TO_THE_LEFT -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.bend_neck_to_the_left)
        )
        BEND_NECK_TO_THE_LEFT_HOLD -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.bend_neck_to_the_left_hold)
        )
        BEND_NECK_TO_THE_RIGHT -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.bend_neck_to_the_right)
        )
        BEND_NECK_TO_THE_RIGHT_HOLD -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.bend_neck_to_the_right_hold)
        )
        BOTH_HANDS_ON_BACK_OF_HEAD_HOLD -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.both_hands_on_back_of_head_hold)
        )
        LEFT_LEG_KICK_TO_YOUR_SIDE -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.left_leg_kick_to_your_side)
        )
        LOOK_DOWN_HOLD -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.look_down_hold)
        )
        LOOK_TO_THE_LEFT -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.look_to_the_left)
        )
        LOOK_TO_THE_LEFT_HOLD -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.look_to_the_left_hold)
        )
        LOOK_TO_THE_RIGHT -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.look_to_the_right)
        )
        LOOK_TO_THE_RIGHT_HOLD -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.look_to_the_right_hold)
        )
        LOOK_UP_HOLD -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.look_up_hold)
        )
        PUSH_FORWARD_HOLD -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.push_forward_hold)
        )
        RIGHT_LEG_KICK_TO_YOUR_SIDE -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.right_leg_kick_to_your_side)
        )
        EXTEND_LEFT_ARM -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.extend_left_arm)
        )
        EXTEND_LEFT_ARM_HOLD -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.extend_left_arm_hold)
        )
        EXTEND_RIGHT_ARM -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.extend_right_arm)
        )
        EXTEND_RIGHT_ARM_HOLD -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.extend_right_arm_hold)
        )
        HALF_KNEELING_ON_LEFT -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.half_kneeling_on_left)
        )
        HALF_KNEELING_ON_RIGHT -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.half_kneeling_on_right)
        )
        KNEEL_UPRIGHT -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.kneel_upright)
        )
        LEFT_KNEE_DOWN -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.left_knee_down)
        )
        PLACE_LEFT_FOOT_ON_FLOOR -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.place_left_foot_on_floor)
        )
        PLACE_RIGHT_FOOT_ON_FLOOR -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.place_right_foot_on_floor)
        )
        RIGHT_KNEE_DOWN -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.right_knee_down)
        )
        SQUAT_KNEELING -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.squat_kneeling)
        )
        BOTH_KNEES_ON_FLOOR -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.both_knees_on_floor)
        )
        LEFT_SIDE_KICK -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.left_side_kick)
        )
        RIGHT_SIDE_KICK -> Instruction(
            text = text, player = MediaPlayer.create(context, R.raw.right_side_kick)
        )
        LEFT_ARM_STEP_BACKWARD -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.left_arm_step_backward)
        )
        LEFT_ARM_STEP_FORWARD -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.left_arm_step_forward)
        )
        LEFT_KNEE_STEP_BACKWARD -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.left_knee_step_backward)
        )
        LEFT_KNEE_STEP_FORWARD -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.left_knee_step_forward)
        )
        RIGHT_ARM_STEP_BACKWARD -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.right_arm_step_backward)
        )
        RIGHT_ARM_STEP_FORWARD -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.right_arm_step_forward)
        )
        RIGHT_KNEE_STEP_BACKWARD -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.right_knee_step_backward)
        )
        RIGHT_KNEE_STEP_FORWARD -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.right_knee_step_forward)
        )
        BEEP -> Instruction(text = text, player = MediaPlayer.create(context, R.raw.right_count))
        PAUSE -> Instruction(text = text, MediaPlayer.create(context, R.raw.pause))
        BOTH_HEEL_SLIDE -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.both_heel_slide)
        )
        LEFT_ARM_RIGHT_LEG_DOWN -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.left_arm_right_leg_down)
        )
        LEFT_HEEL_SLIDE -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.left_heel_slide)
        )
        RIGHT_ARM_LEFT_LEG_DOWN -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.right_arm_left_leg_down)
        )
        RIGHT_HEEL_SLIDE -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.right_heel_slide)
        )
        PULL_IN -> Instruction(text = text, player = MediaPlayer.create(context, R.raw.pull_in))
        PULL_OUT -> Instruction(text = text, player = MediaPlayer.create(context, R.raw.pull_out))
        TURN_TO_THE_OTHER_SIDE -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.turn_to_the_other_side)
        )
        HANDS_ON_THE_BALL -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.hands_on_the_ball)
        )
        LEFT_FOOT_ON_RIGHT_KNEE -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.left_foot_on_right_knee)
        )
        LEFT_HAND_ON_THE_BALL -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.left_hand_on_the_ball)
        )
        LEFT_HEEL_DOWN -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.left_heel_down)
        )
        PULL_LEGS_BACK_HOLD -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.pull_legs_back_hold)
        )
        RIGHT_FOOT_ON_LEFT_KNEE -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.right_foot_on_left_knee)
        )
        RIGHT_HAND_ON_THE_BALL -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.right_hand_on_the_ball)
        )
        RIGHT_HEEL_DOWN -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.right_heel_down)
        )
        LAY_ON_YOUR_BACK -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.lay_on_your_back)
        )
        KNEES_BENT -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.knees_bent)
        )
        PROP_YOUR_ELBOWS -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.prop_your_elbows)
        )
        PROP_ONTO_YOUR_HANDS_AND_KNEES -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.prop_onto_your_hands_and_knees)
        )
        LEFT_LEG_STRAIGHT -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.left_leg_straight)
        )
        RIGHT_LEG_STRAIGHT -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.right_leg_straight)
        )
        LEFT_KNEE_STRAIGHT -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.left_knee_straight)
        )
        RIGHT_KNEE_STRAIGHT -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.right_knee_straight)
        )
        LEFT_FOOT_STEP_FORWARD_RIGHT_KNEE_DOWN -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.left_foot_step_forward_right_knee_down)
        )
        RIGHT_FOOT_STEP_FORWARD_LEFT_KNEE_DOWN -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.right_foot_step_forward_left_knee_down)
        )
        HANDS_AT_THE_BACK_OF_HEAD_HOLD -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.hands_at_the_back_of_head_hold)
        )
        PUSH_TO_THE_SIDE_HOLD -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.push_to_the_side_hold)
        )
        LIFT_LEFT_KNEE_TO_CHEST -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.lift_left_knee_to_chest)
        )
        LIFT_RIGHT_KNEE_TO_CHEST -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.lift_right_knee_to_chest)
        )
        EXTEND_LEFT_KNEE_HOLD -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.extend_left_knee_hold)
        )
        EXTEND_RIGHT_KNEE_HOLD -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.extend_right_knee_hold)
        )
        LIFT_LEFT_LEG_UP -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.lift_left_leg_up)
        )
        LIFT_RIGHT_LEG_UP -> Instruction(
            text = text,
            player = MediaPlayer.create(context, R.raw.lift_right_leg_up)
        )
        else -> Instruction(text = text, player = null)
    }
}