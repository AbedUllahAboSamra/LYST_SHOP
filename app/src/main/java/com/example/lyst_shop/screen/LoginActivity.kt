package com.example.lyst_shop.screen

import android.content.Intent
import android.content.IntentSender
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import com.example.lyst_shop.R
import com.example.lyst_shop.databinding.ActivityLoginBinding
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GithubAuthProvider

class LoginActivity : AppCompatActivity() {
    private val RC_SIGN_IN = 100     // Can be any integer unique to the Activity
    private lateinit var googleSignInClient: GoogleSignInClient
    lateinit var binding: ActivityLoginBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var googleSignInOption = GoogleSignInOptions.Builder(
            GoogleSignInOptions.DEFAULT_SIGN_IN
        )
            .requestIdToken(getString(R.string.your_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOption)
        // firebaseAuth = FirebaseAuth.getInstance()


        binding.btnGoogleSign.setOnClickListener {
            var intent = googleSignInClient.signInIntent
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivityForResult(intent, RC_SIGN_IN)
        }

        binding.btnContuneWithEmail.setOnClickListener {
            var i = Intent(this, LoginWithEmailActivity::class.java)
            i.putExtra("opera", 0)
            startActivity(i)
        }


    }

    override fun onStart() {
        super.onStart()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            RC_SIGN_IN -> {
                try {
                    var account = GoogleSignIn.getSignedInAccountFromIntent(data)
                    var credtial =
                        GithubAuthProvider.getCredential(account.result.idToken.toString())
                    FirebaseAuth.getInstance().signInWithCredential(credtial)
                        .addOnSuccessListener {
                            Log.e("ASD", "SSCCCEESS")
                        }.addOnFailureListener {
                            Log.e("ASD", "FFAALLUUEERR")

                        }
                } catch (e: Exception) {
                }
            }
        }
    }

}