/*
 * Copyright 2024 BlackOrbs (blackorbs@icloud.com)
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       https://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License
 */

package blackorbs.dev.payapp.ui.login

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import blackorbs.dev.payapp.R
import blackorbs.dev.payapp.databinding.LoginScreenBinding
import blackorbs.dev.payapp.ui.main.MainScreen
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

class LoginScreen : AppCompatActivity() {
    private lateinit var binding : LoginScreenBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        var keep = true
        installSplashScreen().setKeepOnScreenCondition{keep}
        super.onCreate(savedInstanceState)
        binding = LoginScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth = FirebaseAuth.getInstance()
        firebaseAuth.currentUser?.let { startActivity(Intent(this, MainScreen::class.java)) }
        binding.loginBtn.setOnClickListener { logInUser() }
        Handler(Looper.getMainLooper()).postDelayed({keep = false}, 1000)
    }

    private fun logInUser(){
        val email = binding.email.text
        val password = binding.password.text
        when {
            email.isNullOrBlank() -> binding.email.error = getString(R.string.text_input_error)
            password.isNullOrBlank() -> binding.password.error = getString(R.string.text_input_error)
            else -> {
                binding.loginBtn.isEnabled = false
                binding.loading.show()
                firebaseAuth.createUserWithEmailAndPassword(
                    email.toString(), password.toString()
                ).addOnCompleteListener {
                    if (it.isSuccessful) {
                        startActivity(Intent(this, MainScreen::class.java))
                    } else {
                        binding.loginBtn.isEnabled = true
                        binding.loading.hide()
                        Snackbar.make(
                            binding.root,
                            if(it.exception == null) getString(R.string.login_failed)
                            else it.exception!!.localizedMessage,
                            Snackbar.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }
    }

}