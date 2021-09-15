package org.tensorflow.lite.examples.poseestimation

import android.os.Bundle
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
            ReachArmsOverHead(view.context)
        )
        exerciseList.forEach {
            if (it.TestId !in uniqueTestId) {
                uniqueTestId.add(it.TestId)
            }
        }
        uniqueTestId.forEach { testId ->
            val parsedExercises = mutableListOf<IExercise>()
            exerciseList.filter { it.TestId == testId }.forEach {
                var isAdded = false
                for (exercise in implementedExercise) {
                    if (it.Id == exercise.id && it.TestId == testId) {
                        parsedExercises.add(exercise)
                        isAdded = true
                        break
                    }
                }
                if (!isAdded) {
                    parsedExercises.add(
                        GeneralExercise(
                            view.context,
                            it.Id,
                            it.Exercise,
                            it.Exercise,
                            active = false
                        )
                    )
                }
            }
            testList.add(
                TestId(
                    id = testId,
                    exercises = parsedExercises
                )
            )
        }
        adapter.adapter = AssessmentListAdapter(testList, parentFragmentManager)
        return view
    }

}