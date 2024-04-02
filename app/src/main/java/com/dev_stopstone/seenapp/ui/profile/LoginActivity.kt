package com.dev_stopstone.seenapp.ui.profile

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.dev_stopstone.seenapp.R
import com.dev_stopstone.seenapp.databinding.ActivityLoginBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class LoginActivity : AppCompatActivity() {
    private val binding by lazy { ActivityLoginBinding.inflate(layoutInflater) }
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)

        binding.btnGoogleLogin.setOnClickListener {
            googleSignIn()
        }
    }

    private fun googleSignIn() {
        val signInClient = googleSignInClient.signInIntent
        launcher.launch(signInClient)
    }

    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        result ->
        if (result.resultCode == RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            manageResult(task)
        } else {
            Toast.makeText(this, result.toString(), Toast.LENGTH_LONG).show()
        }
    }

    private fun manageResult(task: Task<GoogleSignInAccount>) {
        val account: GoogleSignInAccount? = task.result
        if (account != null) {
            val credential = GoogleAuthProvider.getCredential(account.idToken, null)
            auth.signInWithCredential(credential).addOnCompleteListener {
                if (task.isSuccessful) {
                    val intent = Intent(this, ProfileAddActivity::class.java)
                    startActivity(intent)
                    finish()
                    Toast.makeText(this, "${account.displayName} 님 안녕하세요.", Toast.LENGTH_SHORT).show()
                }
            }
        }
        else {
            Toast.makeText(this, task.exception.toString(), Toast.LENGTH_LONG).show()
        }
    }
}


