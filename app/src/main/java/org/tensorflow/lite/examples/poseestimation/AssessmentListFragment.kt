package org.tensorflow.lite.examples.poseestimation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import org.tensorflow.lite.examples.poseestimation.api.resp.Assessment
import org.tensorflow.lite.examples.poseestimation.core.AssessmentListAdapter
import org.tensorflow.lite.examples.poseestimation.domain.model.TestId
import org.tensorflow.lite.examples.poseestimation.exercise.*

class AssessmentListFragment(
    private val assessmentList: List<Assessment>
) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_assessment_list, container, false)
        val adapter = view.findViewById<RecyclerView>(R.id.assessment_list_container)
        val testList = mutableListOf<TestId>()
        val implementedExerciseList = listOf(
            ReachArmsOverHead(view.context),
            KneeSquat(view.context),
            HalfSquat(view.context),
            SeatedKneeExtension(view.context),
            PelvicBridge(view.context),
            SitToStand(view.context),
            IsometricCervicalExtension(view.context),
            LateralTrunkStretch(view.context),
            AROMStandingTrunkFlexion(view.context),
            BirdDog(view.context),
            LumberFlexionSitting(view.context),
            SingleLegRaiseInQuadruped(view.context),
            ProneOnElbows(view.context),
            SingleArmRaiseInProne(view.context),
            SingleArmRaiseInQuadruped(view.context)
        )
        assessmentList.forEach { assessment ->
            val parsedExercises = mutableListOf<IExercise>()
            assessment.Exercises.forEach { exercise ->
                val implementedExercise =
                    implementedExerciseList.find { it.id == exercise.ExerciseId }
                if (implementedExercise != null) {
                    implementedExercise.setExercise(
                        exerciseName = exercise.ExerciseName,
                        exerciseDescription = exercise.ExerciseName,
                        exerciseInstruction = exercise.Instructions,
                        exerciseImageUrls = exercise.ImageURLs,
                        repetitionLimit = exercise.RepetitionInCount,
                        setLimit = exercise.SetInCount,
                        protoId = exercise.ProtocolId,
                    )
                    parsedExercises.add(implementedExercise)
                } else {
                    val notImplementedExercise = GeneralExercise(
                        context = view.context,
                        exerciseId = exercise.ExerciseId,
                        active = false
                    )
                    notImplementedExercise.setExercise(
                        exerciseName = exercise.ExerciseName,
                        exerciseDescription = exercise.ExerciseName,
                        exerciseInstruction = exercise.Instructions,
                        exerciseImageUrls = exercise.ImageURLs,
                        repetitionLimit = exercise.RepetitionInCount,
                        setLimit = exercise.SetInCount,
                        protoId = exercise.ProtocolId,
                    )
                    parsedExercises.add(notImplementedExercise)
                }
            }
            testList.add(
                TestId(
                    id = assessment.TestId,
                    exercises = parsedExercises.sortedBy { it.active }.reversed()
                )
            )
        }
        adapter.adapter = AssessmentListAdapter(testList, parentFragmentManager)
        return view
    }

}