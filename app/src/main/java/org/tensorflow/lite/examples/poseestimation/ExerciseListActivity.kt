package org.tensorflow.lite.examples.poseestimation

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import org.tensorflow.lite.examples.poseestimation.core.ExerciseListAdapter
import org.tensorflow.lite.examples.poseestimation.databinding.ActivityExerciseListBinding
import org.tensorflow.lite.examples.poseestimation.domain.model.LogInData

class ExerciseListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityExerciseListBinding
    private lateinit var menuToggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExerciseListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        menuToggle = ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            R.string.open,
            R.string.close
        )
        binding.drawerLayout.addDrawerListener(menuToggle)
        menuToggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.exerciseListContainer.adapter = ExerciseListAdapter(this)

        binding.navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.exercise_list_activity_button -> {
                    val intent = Intent(this, ExerciseListActivity::class.java)
                    startActivity(intent)
                }
                R.id.log_out_button -> {
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
            true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (menuToggle.onOptionsItemSelected(item)) return true
        return super.onOptionsItemSelected(item)
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
}