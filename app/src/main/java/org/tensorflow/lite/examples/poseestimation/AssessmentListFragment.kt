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
import org.tensorflow.lite.examples.poseestimation.exercise.home.GeneralExercise
import org.tensorflow.lite.examples.poseestimation.exercise.home.HomeExercise
import org.tensorflow.lite.examples.poseestimation.shared.Exercises

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
            val implementedExerciseList = Exercises.get(view.context)
            val parsedExercises = mutableListOf<HomeExercise>()
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
                        protoId = exercise.ProtocolId
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
                        protoId = exercise.ProtocolId
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