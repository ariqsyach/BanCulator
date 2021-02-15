package com.example.banculator

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

import android.widget.Toast
import com.example.banculator.view.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        auth = FirebaseAuth.getInstance()
        val currentuser = auth.currentUser
        if (currentuser != null) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
        btn_login.setOnClickListener {
            login()
        }
        tv_login_to_sign.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }

    private fun login() {
        val email = et_email_login.text.toString()
        val pw = et_pw_login.text.toString()

        if (et_email_login.text.isEmpty()) {
            Toast.makeText(this, "Please Insert Email",Toast.LENGTH_LONG).show()
            return
        } else if (et_pw_login.text.isEmpty()) {
            Toast.makeText(this, "Please insert Password", Toast.LENGTH_LONG).show()
            return
        } else if (et_email_login.text.toString().isNotEmpty() || et_pw_login.text.toString()
                .isNotEmpty()
        ) {
            auth.signInWithEmailAndPassword(email, pw)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        updateUI(auth.currentUser)
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    } else {
                        Toast.makeText(this, "Login failed, please try again! ", Toast.LENGTH_LONG)
                            .show()
                    }
                }
            return
        } else {
            Toast.makeText(this, "Login Error", Toast.LENGTH_LONG).show()
        }

    }

    private fun updateUI(currentUser: FirebaseUser?) {
        if (currentUser != null) {
            Toast.makeText(this, "Login Berhasil", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this, "Login Gagal", Toast.LENGTH_LONG).show()
        }
    }
}
