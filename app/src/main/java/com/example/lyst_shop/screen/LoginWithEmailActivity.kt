package com.example.lyst_shop.screen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.lyst_shop.MainActivity
import com.example.lyst_shop.R
import com.example.lyst_shop.databinding.ActivityLoginWithEmailBinding
import com.example.lyst_shop.model.AccountModel
import com.example.lyst_shop.model.CustomProgressDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import java.time.LocalDate

class LoginWithEmailActivity : AppCompatActivity() {
    var btnTrue = true
    private val progressDialog = CustomProgressDialog()
    var opra = MediatorLiveData<Int>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var binding = ActivityLoginWithEmailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        opra.value = 0
        //start initialization
        opra.value = intent.getIntExtra("opera", 0)
        opra.observe(this) { it ->
            if (it == 0) {
                binding.tvDontHaveEmail.visibility = View.VISIBLE
                binding.tvScreenText.text =
                    "Welcome back! You're just a tap away from something delicious."
                binding.layDisplayName.visibility = View.GONE
                binding.layConfirmPassword.visibility = View.GONE
                binding.layout.visibility = View.GONE
                binding.tvBtn.text = "START !"
                binding.tvScreenName.text = "Log In"


            } else {
                binding.tvDontHaveEmail.visibility = View.GONE
                binding.tvScreenText.text = "Save delicious recipes and get personalized content."
                binding.tvScreenName.text = "Sign Up"
                binding.layDisplayName.visibility = View.VISIBLE
                binding.layConfirmPassword.visibility = View.VISIBLE
                binding.layout.visibility = View.VISIBLE
                binding.tvBtn.text = "FINISH"

            }

        }

        //end initialization


        // back to login Activity
        binding.btnBack.setOnClickListener {
            if (opra.value == 1) {
                opra.value = 0
            } else {
                this.onBackPressed()
            }
        }

        //start btn Save receive
        binding.btnTrue.setOnClickListener {
            if (btnTrue) {
                btnTrue = false
                binding.imgDone.setImageResource(R.drawable.ic_outline_brightness_1_24)
            } else {
                binding.imgDone.setImageResource(R.drawable.ic_outline_done_24)
                btnTrue = true
            }
        }
        //end btn Save receive

        binding.btnCreate.setOnClickListener {
            var email = binding.edtEmail.text.toString()
            var password = binding.edtPassword.text.toString()

            if (opra.value == 1) {
                var confrmPassword = binding.edtConfirmPassword.text.toString()
                var name = binding.edtDisplayName.text.toString()

                if (email.isNotEmpty() && (password.length >= 6) && password == confrmPassword && name.isNotEmpty()) {
                    progressDialog.show(this, "LOADING...")

                    Firebase.auth.createUserWithEmailAndPassword(email, password)
                        .addOnSuccessListener { it ->
                            var map = HashMap<String, Any>()
                            map.put("id", it.user!!.uid)
                            map.put("name", name)
                            map.put(
                                "imageUri",
                                "https://lh3.googleusercontent.com/-cXXaVVq8nMM/AAAAAAAAAAI/AAAAAAAAAKI/_Y1WfBiSnRI/photo.jpg?sz=500"
                            )
                            map.put("bio", "")
                            map.put("description", "")
                            map.put("password", password)
                            map.put("numberOfFollower", "")
                            map.put("numberOfRecipes", "")
                            map.put("accountType", "user")

                            var accountModel = AccountModel(
                                id = it.user!!.uid, name = name,
                                imageUri = "https://lh3.googleusercontent.com/-cXXaVVq8nMM/AAAAAAAAAAI/AAAAAAAAAKI/_Y1WfBiSnRI/photo.jpg?sz=500",
                                bio = "",
                                description = "",
                                numberOfFollower = "",
                                numberOfRecipes = "",
                                accountType = "user",

                                )

                            FirebaseFirestore.getInstance().collection("users")
                                .document(it.user!!.uid)
                                .set(map)
                                .addOnSuccessListener {
//                                    SplachActivity.currentAccount = accountModel
                                    progressDialog.dialog.dismiss()
                                    startActivity(Intent(this, MainActivity::class.java))
                                    finish()
                                }.addOnFailureListener {
                                    progressDialog.dialog.dismiss()
                                    Log.e("ASD", it.toString())
                                    Toast.makeText(
                                        this,
                                        "Conform your phone connect with internet ",
                                        Toast.LENGTH_SHORT
                                    ).show()

                                }


                        }
                        .addOnFailureListener {
                            progressDialog.dialog.dismiss()
                            Toast.makeText(
                                this,
                                "Conform your phone connect with internet ",
                                Toast.LENGTH_SHORT
                            ).show()

                        }

                } else {
                    Toast.makeText(this, "Invalid Date", Toast.LENGTH_SHORT).show()
                }

            } else {
                if (email.isNotEmpty() && (password.length >= 6)) {

                    progressDialog.show(this, "LOADING...")
                    FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)

                        .addOnSuccessListener { it ->
//                            for (item in SplachActivity.arrAllAccounts) {
//                                if (item.id == it.user!!.uid) {
//                                    SplachActivity.currentAccount = item
//                                }
                        }
                    progressDialog.dialog.dismiss()


                } else {
                    Toast.makeText(this, "email or password is incorrect ", Toast.LENGTH_SHORT)
                        .show()

                }

            }

        }
        binding.tvDontHaveEmail.setOnClickListener {
            Log.e("ASD", "ASDASD")
            if (opra.value == 0) {
                opra.value = 1

            } else {
                opra.value = 0
            }
        }

    }
}