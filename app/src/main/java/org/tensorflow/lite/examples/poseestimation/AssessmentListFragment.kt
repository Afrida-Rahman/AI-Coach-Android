package org.tensorflow.lite.examples.poseestimation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import org.tensorflow.lite.examples.poseestimation.core.AssessmentListAdapter
import org.tensorflow.lite.examples.poseestimation.domain.model.ExerciseItem
import org.tensorflow.lite.examples.poseestimation.domain.model.TestId
import org.tensorflow.lite.examples.poseestimation.exercise.GeneralExercise
import org.tensorflow.lite.examples.poseestimation.exercise.IExercise
import org.tensorflow.lite.examples.poseestimation.exercise.ReachArmsOverHead

class AssessmentListFragment(
    private val exerciseList: List<ExerciseItem>
) : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_assessment_list, container, false)
        val adapter = view.findViewById<RecyclerView>(R.id.assessment_list_container)
        val testList = mutableListOf<TestId>()
        val uniqueTestId = mutableListOf<String>()
        val implementedExercise = listOf<IExercise>(
            ReachArmsOverHead(view.context, "Arm Raise", "Arm Raise", 0)
        )
        exerciseList.forEach {
            if (it.TestId !in uniqueTestId) {
                uniqueTestId.add(it.TestId)
            }
        }
        uniqueTestId.forEach { testId ->
            val parsedExercises = mutableListOf<IExercise>()
            var armRaisedExercise: ExerciseItem? = null
            exerciseList.filter { it.TestId == testId }.forEach {
                var isAdded = false
                for (exercise in implementedExercise) {
                    if (it.Id == exercise.id && it.TestId == testId) {
                        // parsedExercises.add(exercise)
                        armRaisedExercise = it
                        Log.d("saveExerciseData", "$it")
                        isAdded = true
                        break
                    }
                }
                if (!isAdded) {
                    parsedExercises.add(
                        GeneralExercise(
                            context = view.context,
                            exerciseId = it.Id,
                            name = it.Exercise,
                            description = it.Exercise,
                            protocolId = it.ProtocolId,
                            active = false
                        )
                    )
                }
            }
            if (armRaisedExercise != null) {
                val exercise = ReachArmsOverHead(
                    context = view.context,
                    name = armRaisedExercise!!.Exercise,
                    description = armRaisedExercise!!.Exercise,
                    protocolId = armRaisedExercise!!.ProtocolId
                )
                Log.d("saveExerciseData", "${armRaisedExercise!!.ProtocolId}")
                exercise.setExercise(10, 1, armRaisedExercise!!.ProtocolId)
                parsedExercises.add(
                    exercise
                )
            } else {
                val protoId = parsedExercises[0].protocolId
                val exercise = ReachArmsOverHead(
                    context = view.context,
                    name = "Arm Raise",
                    description = "Arm Raise",
                    protocolId = protoId
                )
                exercise.setExercise(10, 1, protoId)
                parsedExercises.add(
                    exercise
                )
            }
            testList.add(
                TestId(
                    id = testId,
                    exercises = parsedExercises.sortedBy { it.active }.reversed()
                )
            )
        }
        adapter.adapter = AssessmentListAdapter(testList, parentFragmentManager)
        return view
    }

}