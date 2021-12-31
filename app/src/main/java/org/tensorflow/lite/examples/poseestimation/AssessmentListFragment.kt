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
    private val assessments: List<Assessment>,
    private val patientId: String,
    private val tenant: String
) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_assessment_list, container, false)
        val adapter = view.findViewById<RecyclerView>(R.id.assessment_list_container)
        val testList = mutableListOf<TestId>()
        assessments.forEach { assessment ->
            val implementedExerciseList = listOf(
                ReachArmsOverHead(view.context),
                KneeSquat(view.context),
                HalfSquat(view.context),
                SeatedKneeExtension(view.context),
                PelvicBridge(view.context),
                SitToStand(view.context),
                IsometricCervicalExtension(view.context),
                LateralTrunkStretch(view.context),
                TrunkFlexionInStanding(view.context),
                BirdDog(view.context),
                LumberFlexionSitting(view.context),
                SingleLegRaiseInQuadruped(view.context),
                SingleLegRaiseInProne(view.context),
                ProneOnElbows(view.context),
                SingleArmRaiseInProne(view.context),
                SingleArmRaiseInQuadruped(view.context),
                Quadruped(view.context),
                PronePressUpLumbar(view.context),
                Plank(view.context),
                CommonExercise(view.context),
                SingleLegFallOutInSupine(view.context),
                TrunkRotationInSitting(view.context),
                TrunkRotationInStanding(view.context),
                PlankOnKneesInProne(view.context),
                IsometricShoulderAdductionInStanding(view.context),
                IsometricCervicalExtensionInStanding(view.context)
            )
            val parsedExercises = mutableListOf<IExercise>()
            assessment.Exercises.forEach { exercise ->
                val implementedExercise =
                    implementedExerciseList.find { it.id == exercise.ExerciseId }
                if (implementedExercise != null) {
                    implementedExercise.setExercise(
                        exerciseName = exercise.ExerciseName,
                        exerciseInstruction = exercise.Instructions,
                        exerciseImageUrls = exercise.ImageURLs,
                        repetitionLimit = exercise.RepetitionInCount,
                        setLimit = exercise.SetInCount,
                        protoId = exercise.ProtocolId,
                        holdLimit = exercise.HoldInSeconds.toLong()
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
                        exerciseInstruction = exercise.Instructions,
                        exerciseImageUrls = exercise.ImageURLs,
                        repetitionLimit = exercise.RepetitionInCount,
                        setLimit = exercise.SetInCount,
                        protoId = exercise.ProtocolId,
                        holdLimit = exercise.HoldInSeconds.toLong()
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
        adapter.adapter = AssessmentListAdapter(testList, parentFragmentManager, patientId, tenant)
        return view
    }

}