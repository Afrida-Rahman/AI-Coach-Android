package org.tensorflow.lite.examples.poseestimation.core

import android.content.Context
import android.media.MediaPlayer
import org.tensorflow.lite.examples.poseestimation.R

class AsyncAudioPlayer(context: Context) {

    companion object {
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
    }

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
    private val leanLeft = MediaPlayer.create(context, R.raw.lean_left)
    private val leanRight = MediaPlayer.create(context, R.raw.lean_right)
    private val returnAudio = MediaPlayer.create(context, R.raw.return_audio)
    private val finishAudio = MediaPlayer.create(context, R.raw.finish_audio)
    private val congrats = MediaPlayer.create(context, R.raw.congratulate_patient)
    private val takeRest = MediaPlayer.create(context, R.raw.take_some_time_to_rest)
    private val startAudio = MediaPlayer.create(context, R.raw.start)
    private val startAgain = MediaPlayer.create(context, R.raw.start_again)
    private val firstSet = MediaPlayer.create(context, R.raw.first_set)
    private val secondSet = MediaPlayer.create(context, R.raw.second_set)
    private val thirdSet = MediaPlayer.create(context, R.raw.third_set)
    private val fourthSet = MediaPlayer.create(context, R.raw.fourth_set)
    private val fifthSet = MediaPlayer.create(context, R.raw.fifth_set)
    private val sixthSet = MediaPlayer.create(context, R.raw.sixth_set)
    private val seventhSet = MediaPlayer.create(context, R.raw.seventh_set)
    private val eighthSet = MediaPlayer.create(context, R.raw.eighth_set)
    private val ninthSet = MediaPlayer.create(context, R.raw.ninth_set)
    private val tenthSet = MediaPlayer.create(context, R.raw.tenth_set)
    private val setCompleted = MediaPlayer.create(context, R.raw.set_complete)
    private val getReady = MediaPlayer.create(context, R.raw.get_ready)
    private val squatDown = MediaPlayer.create(context, R.raw.squat_down)
    private val bendLeftKnee = MediaPlayer.create(context, R.raw.bend_left_knee)
    private val bendRightKnee = MediaPlayer.create(context, R.raw.bend_right_knee)
    private val bendBothKnees = MediaPlayer.create(context, R.raw.bend_both_knees)
    private val relax = MediaPlayer.create(context, R.raw.relax)
    private val push = MediaPlayer.create(context, R.raw.push)
    private val pullYourElbowsBack = MediaPlayer.create(context, R.raw.pull_your_elbows_back)
    private val crunchUp = MediaPlayer.create(context, R.raw.crunch_up)
    private val backDown = MediaPlayer.create(context, R.raw.back_down)
    private val plankUp = MediaPlayer.create(context, R.raw.plank_up)
    private val armRaise = MediaPlayer.create(context, R.raw.arm_raise)
    private val rightLegKickBackward = MediaPlayer.create(context, R.raw.right_leg_kick_backward)
    private val leftLegKickBackward = MediaPlayer.create(context, R.raw.left_leg_kick_backward)
    private val rightLegKickLateral = MediaPlayer.create(context, R.raw.right_leg_kick_lateral)
    private val leftLegKickLateral = MediaPlayer.create(context, R.raw.left_leg_kick_lateral)
    private val leanForward = MediaPlayer.create(context, R.raw.lean_forward)
    private val leanBackward = MediaPlayer.create(context, R.raw.lean_backward)
    private val bothLegsFallOut = MediaPlayer.create(context, R.raw.both_legs_fall_out)
    private val extendRightKnee = MediaPlayer.create(context, R.raw.extend_right_knee)
    private val extendLeftKnee = MediaPlayer.create(context, R.raw.extend_left_knee)
    private val liftHip = MediaPlayer.create(context, R.raw.lift_hip)
    private val liftRightArm = MediaPlayer.create(context, R.raw.lift_right_arm)
    private val liftLeftArm = MediaPlayer.create(context, R.raw.lift_left_arm)
    private val liftRightLeg = MediaPlayer.create(context, R.raw.lift_right_leg)
    private val liftLeftLeg = MediaPlayer.create(context, R.raw.lift_left_leg)
    private val liftRightKnee = MediaPlayer.create(context, R.raw.lift_right_knee)
    private val liftLeftKnee = MediaPlayer.create(context, R.raw.lift_left_knee)
    private val leanBackwardHold = MediaPlayer.create(context, R.raw.lean_backward_hold)
    private val leanForwardHold = MediaPlayer.create(context, R.raw.lean_forward_hold)
    private val leanLeftHold = MediaPlayer.create(context, R.raw.lean_left_hold)
    private val leanRightHold = MediaPlayer.create(context, R.raw.lean_right_hold)
    private val squatDownHold = MediaPlayer.create(context, R.raw.squat_down_hold)
    private val plankUpHold = MediaPlayer.create(context, R.raw.plank_up_hold)
    private val quadrupedHold = MediaPlayer.create(context, R.raw.quadruped_hold)
    private val liftHipHold = MediaPlayer.create(context, R.raw.lift_hip_hold)
    private val liftRightLegHold = MediaPlayer.create(context, R.raw.lift_right_leg_hold)
    private val liftLeftLegHold = MediaPlayer.create(context, R.raw.lift_left_leg_hold)
    private val bendBothKneesHold = MediaPlayer.create(context, R.raw.bend_both_knees_hold)
    private val pressUp = MediaPlayer.create(context, R.raw.press_up)
    private val pressUpHold = MediaPlayer.create(context, R.raw.press_up_hold)
    private val curlUpRightElbow = MediaPlayer.create(context, R.raw.curl_up_right_elbow)
    private val curlUpLeftElbow = MediaPlayer.create(context, R.raw.curl_up_left_elbow)
    private val curlUp = MediaPlayer.create(context, R.raw.curl_up)
    private val lungeLeftLeg = MediaPlayer.create(context, R.raw.lunge_left_leg)
    private val lungeRightLeg = MediaPlayer.create(context, R.raw.lunge_right_leg)
    private val armsUp = MediaPlayer.create(context, R.raw.arms_up)
    private val armsDown = MediaPlayer.create(context, R.raw.arms_down)
    private val lungeLeftLegHold = MediaPlayer.create(context, R.raw.lunge_left_leg_hold)
    private val lungeRightLegHold = MediaPlayer.create(context, R.raw.lunge_right_leg_hold)
    private val reachAnkleHold = MediaPlayer.create(context, R.raw.reach_ankle_hold)
    private val leanForwardRightHold = MediaPlayer.create(context, R.raw.lean_forward_right_hold)
    private val leanForwardLeftHold = MediaPlayer.create(context, R.raw.lean_forward_left_hold)
    private val standUp = MediaPlayer.create(context, R.raw.stand_up)
    private val sitDown = MediaPlayer.create(context, R.raw.sit_down)
    private val slightlyBendBothKnees = MediaPlayer.create(context, R.raw.slightly_bend_both_knees)
    private val pullUp = MediaPlayer.create(context, R.raw.pull_up)
    private val pushUp = MediaPlayer.create(context, R.raw.push_up)
    private val pullDown = MediaPlayer.create(context, R.raw.pull_down)
    private val leftArmRightLegUp = MediaPlayer.create(context, R.raw.left_arm_right_leg_up)
    private val rightArmLeftLegUp = MediaPlayer.create(context, R.raw.right_arm_left_leg_up)
    private val leftArmRightLegUpHold =
        MediaPlayer.create(context, R.raw.left_arm_right_leg_up_hold)
    private val rightArmLeftLegUpHold =
        MediaPlayer.create(context, R.raw.right_arm_left_leg_up_hold)
    private val proneOnElbows = MediaPlayer.create(context, R.raw.prone_on_elbows)
    private val proneOnElbowsHold = MediaPlayer.create(context, R.raw.prone_on_elbows_hold)
    private val extendBothKnees = MediaPlayer.create(context, R.raw.extend_both_knees)
    private val liftLeftArmForward = MediaPlayer.create(context, R.raw.lift_left_arm_forward)
    private val liftRightArmForward = MediaPlayer.create(context, R.raw.lift_right_arm_forward)
    private val liftLeftArmBackward = MediaPlayer.create(context, R.raw.lift_left_arm_backward)
    private val liftRightArmBackward = MediaPlayer.create(context, R.raw.lift_right_arm_backward)
    private val liftRightKneeToChestHold =
        MediaPlayer.create(context, R.raw.lift_right_knee_to_chest_hold)
    private val liftLeftKneeToChestHold =
        MediaPlayer.create(context, R.raw.lift_left_knee_to_chest_hold)
    private val liftBothKneesToChestHold =
        MediaPlayer.create(context, R.raw.lift_both_knees_to_chest_hold)
    private val thumbUpLiftRightArmForward =
        MediaPlayer.create(context, R.raw.thumb_up_lift_right_arm_forward)
    private val thumbUpLiftLeftArmForward =
        MediaPlayer.create(context, R.raw.thumb_up_lift_left_arm_forward)
    private val thumbsUpArmsRaise = MediaPlayer.create(context, R.raw.thumbs_up_arms_raise)
    private val standAgainstTheWall = MediaPlayer.create(context, R.raw.stand_against_the_wall)

    fun playText(text: String) {
        when (text.lowercase()) {
            LEAN_LEFT -> leanLeft.start()
            LEAN_RIGHT -> leanRight.start()
            RETURN -> returnAudio.start()
            FINISH -> finishAudio.start()
            CONGRATS -> congrats.start()
            TAKE_REST -> takeRest.start()
            START -> startAudio.start()
            SET_1 -> firstSet.start()
            SET_2 -> secondSet.start()
            SET_3 -> thirdSet.start()
            SET_4 -> fourthSet.start()
            SET_5 -> fifthSet.start()
            SET_6 -> sixthSet.start()
            SET_7 -> seventhSet.start()
            SET_8 -> eighthSet.start()
            SET_9 -> ninthSet.start()
            SET_10 -> tenthSet.start()
            SET_COMPLETED -> setCompleted.start()
            GET_READY -> getReady.start()
            START_AGAIN -> startAgain.start()
            SQUAT_DOWN -> squatDown.start()
            BEND_LEFT_KNEE -> bendLeftKnee.start()
            BEND_RIGHT_KNEE -> bendRightKnee.start()
            BEND_BOTH_KNEES -> bendBothKnees.start()
            RELAX -> relax.start()
            PUSH -> push.start()
            PULL_YOUR_ELBOWS_BACK -> pullYourElbowsBack.start()
            CRUNCH_UP -> crunchUp.start()
            BACK_DOWN -> backDown.start()
            PLANK_UP -> plankUp.start()
            ARM_RAISE -> armRaise.start()
            RIGHT_LEG_KICK_BACKWARD -> rightLegKickBackward.start()
            LEFT_LEG_KICK_BACKWARD -> leftLegKickBackward.start()
            RIGHT_LEG_KICK_LATERAL -> rightLegKickLateral.start()
            LEFT_LEG_KICK_LATERAL -> leftLegKickLateral.start()
            LEAN_FORWARD -> leanForward.start()
            LEAN_BACKWARD -> leanBackward.start()
            BOTH_LEGS_FALL_OUT -> bothLegsFallOut.start()
            EXTEND_RIGHT_KNEE -> extendRightKnee.start()
            EXTEND_LEFT_KNEE -> extendLeftKnee.start()
            LIFT_HIP -> liftHip.start()
            LIFT_RIGHT_ARM -> liftRightArm.start()
            LIFT_LEFT_ARM -> liftLeftArm.start()
            LIFT_RIGHT_LEG -> liftRightLeg.start()
            LIFT_LEFT_LEG -> liftLeftLeg.start()
            LIFT_RIGHT_KNEE -> liftRightKnee.start()
            LIFT_LEFT_KNEE -> liftLeftKnee.start()
            LEAN_BACKWARD_HOLD -> leanBackwardHold.start()
            LEAN_FORWARD_HOLD -> leanForwardHold.start()
            LEAN_LEFT_HOLD -> leanLeftHold.start()
            LEAN_RIGHT_HOLD -> leanRightHold.start()
            SQUAT_DOWN_HOLD -> squatDownHold.start()
            PLANK_UP_HOLD -> plankUpHold.start()
            QUADRUPED_HOLD -> quadrupedHold.start()
            LIFT_HIP_HOLD -> liftHipHold.start()
            LIFT_RIGHT_LEG_HOLD -> liftRightLegHold.start()
            LIFT_LEFT_LEG_HOLD -> liftLeftLegHold.start()
            BEND_BOTH_KNEES_HOLD -> bendBothKneesHold.start()
            PRESS_UP -> pressUp.start()
            PRESS_UP_HOLD -> pressUpHold.start()
            CURL_UP_RIGHT_ELBOW -> curlUpRightElbow.start()
            CURL_UP_LEFT_ELBOW -> curlUpLeftElbow.start()
            CURL_UP -> curlUp.start()
            LUNGE_LEFT_LEG -> lungeLeftLeg.start()
            LUNGE_RIGHT_LEG -> lungeRightLeg.start()
            ARMS_UP -> armsUp.start()
            ARMS_DOWN -> armsDown.start()
            LUNGE_LEFT_LEG_HOLD -> lungeLeftLegHold.start()
            LUNGE_RIGHT_LEG_HOLD -> lungeRightLegHold.start()
            REACH_ANKLE_HOLD -> reachAnkleHold.start()
            LEAN_FORWARD_RIGHT_HOLD -> leanForwardRightHold.start()
            LEAN_FORWARD_LEFT_HOLD -> leanForwardLeftHold.start()
            STAND_UP -> standUp.start()
            SIT_DOWN -> sitDown.start()
            SLIGHTLY_BEND_BOTH_KNEES -> slightlyBendBothKnees.start()
            PULL_UP -> pullUp.start()
            PUSH_UP -> pushUp.start()
            PULL_DOWN -> pullDown.start()
            LEFT_ARM_RIGHT_LEG_UP -> leftArmRightLegUp.start()
            RIGHT_ARM_LEFT_LEG_UP -> rightArmLeftLegUp.start()
            LEFT_ARM_RIGHT_LEG_UP_HOLD -> leftArmRightLegUpHold.start()
            RIGHT_ARM_LEFT_LEG_UP_HOLD -> rightArmLeftLegUpHold.start()
            PRONE_ON_ELBOWS -> proneOnElbows.start()
            PRONE_ON_ELBOWS_HOLD -> proneOnElbowsHold.start()
            EXTEND_BOTH_KNEES -> extendBothKnees.start()
            LIFT_LEFT_ARM_FORWARD -> liftLeftArmForward.start()
            LIFT_RIGHT_ARM_FORWARD -> liftRightArmForward.start()
            LIFT_LEFT_ARM_BACKWARD -> liftLeftArmBackward.start()
            LIFT_RIGHT_ARM_BACKWARD -> liftRightArmBackward.start()
            LIFT_RIGHT_KNEE_TO_CHEST_HOLD -> liftRightKneeToChestHold.start()
            LIFT_LEFT_KNEE_TO_CHEST_HOLD -> liftLeftKneeToChestHold.start()
            LIFT_BOTH_KNEES_TO_CHEST_HOLD -> liftBothKneesToChestHold.start()
            THUMB_UP_LIFT_RIGHT_ARM_FORWARD -> thumbUpLiftRightArmForward.start()
            THUMB_UP_LIFT_LEFT_ARM_FORWARD -> thumbUpLiftLeftArmForward.start()
            THUMBS_UP_ARMS_RAISE -> thumbsUpArmsRaise.start()
            STAND_AGAINST_THE_WALL -> standAgainstTheWall.start()
            else -> {}
        }
    }

    fun playNumber(number: Int) {
        when (number) {
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