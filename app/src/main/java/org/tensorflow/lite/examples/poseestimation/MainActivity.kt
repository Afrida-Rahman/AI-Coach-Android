package org.tensorflow.lite.examples.poseestimation

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.tensorflow.lite.examples.poseestimation.core.ExerciseListAdapter
import org.tensorflow.lite.examples.poseestimation.databinding.ActivityMainBinding
import org.tensorflow.lite.examples.poseestimation.domain.model.LogInData

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val logInData = loadLogInData()

        binding.patientName.text = getString(R.string.hello_patient_name_i_m_emma).format(
            logInData.firstName,
            logInData.lastName
        )
        binding.exerciseListContainer.adapter = ExerciseListAdapter(this)

        binding.logOutButton.setOnClickListener {
            saveLogInData(
                LogInData(
                    firstName = "",
                    lastName = "",
                    patientId = "",
                    tenant = ""
                )
            )
            Toast.makeText(this, "Successfully logged out", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, SignInActivity::class.java))
            finish()
        }
    }

    private fun saveLogInData(logInData: LogInData) {
        val preferences = getSharedPreferences(
            SignInActivity.LOGIN_PREFERENCE,
            SignInActivity.PREFERENCE_MODE
        )
        preferences.edit().apply {
            putString(SignInActivity.FIRST_NAME, logInData.firstName)
            putString(SignInActivity.LAST_NAME, logInData.lastName)
            putString(SignInActivity.PATIENT_ID, logInData.patientId)
            putString(SignInActivity.TENANT, logInData.tenant)
            apply()
        }
    }

    private fun loadLogInData(): LogInData {
        val preferences = getSharedPreferences(
            SignInActivity.LOGIN_PREFERENCE,
            SignInActivity.PREFERENCE_MODE
        )
        return LogInData(
            firstName = preferences.getString(SignInActivity.FIRST_NAME, "") ?: "",
            lastName = preferences.getString(SignInActivity.LAST_NAME, "") ?: "",
            patientId = preferences.getString(SignInActivity.PATIENT_ID, "") ?: "",
            tenant = preferences.getString(SignInActivity.TENANT, "") ?: ""
        )
    }
}