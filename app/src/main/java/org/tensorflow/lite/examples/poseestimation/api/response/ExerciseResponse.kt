package org.tensorflow.lite.examples.poseestimation.api.response

class ExerciseResponse(
    val IsPostureExercise: Boolean,
    val HideBodyJoint: Boolean,
    val ProtocolId: Int,
    val ReportName: String?,
    val IsActive: Boolean,
    val Id: Int,
    val ReportExerciseId: Int,
    val Exercise: String,
    val Instructions: String,
    val PhaseId: String,
    val FrequencyId: Int,
    val RepsId: Int,
    val ResistanceId: Int,
    val SetId: Int,
    val CreatedOnUtc: String,
    val UpdatedOnUtc: String?,
    val EvalExerciseTypeList: String?,
    val SelectedEvalExerciseTypeList: String?,
    val IsActiveList: Boolean?,
    val SelectedIsActiveList: String?,
    val ImageUrl: List<String>,
    val Image: String?,
    val BodySubRegionList: String?,
    val SelectedBodySubRegion: String?,
    val SavedFiles: String?,
    val EvalExerciseMediaList: String?,
    val ExercisesCategoriesList: String?,
    val SelectedExercisesCategoriesList: String?,
    val ExerciseCategory: String?,
    val ResistanceList: String?,
    val SelectedResistanceList: Int,
    val SetList: String?,
    val SelectedSetList: Int,
    val RepsList: String?,
    val SelectedRepsList: Int,
    val FrequencyList: String?,
    val SelectedFrequencyList: Int,
    val SetName: String?,
    val PhaseName: String,
    val RepsName: String,
    val FrequencyName: String,
    val ResistanceName: String,
    val CompletedReps: String?,
    val CompletedSets: String?,
    val StartDate: String?
)