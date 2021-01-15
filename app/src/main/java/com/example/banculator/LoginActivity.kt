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
//        val currentuser = auth.currentUser
//        if (currentuser != null) {
//            startActivity(Intent(this, MainActivity::class.java))
//            finish()
//        }
        btn_login.setOnClickListener {
//            doLogin()
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
//
//        if (TextUtils.isEmpty(et_email_login.text.toString())) {
//            et_email_login.setError("Please enter username")
//            return
//        } else if (TextUtils.isEmpty(et_pw_login.text.toString())) {
//            et_pw_login.setError("Please enter password")
//            return
//        }
        if (et_email_login.text.toString().isNotEmpty() || et_pw_login.text.toString().isNotEmpty()
        ) {
            auth.signInWithEmailAndPassword(email, pw)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    } else {
                        Toast.makeText(this, "Login failed, please try again! ", Toast.LENGTH_LONG)
                            .show()
                    }
                }

        }


//    override fun onStart() {
//        super.onStart()
//        val currentUser = auth.currentUser
//        updateUI(currentUser)
//    }
//
//    private fun updateUI(currentUser: FirebaseUser?) {
//        if (currentUser != null) {
//            Toast.makeText(this, "Login Berhasil", Toast.LENGTH_LONG).show()
//        } else {
//            Toast.makeText(this, "Login Gagal", Toast.LENGTH_LONG).show()
//        }
//    }
//
//    private fun doLogin() {
//        if (et_email_login.text.toString().isNotEmpty() || et_pw_login.text.toString().isNotEmpty()
//        ) {
//            val currentUser = auth.currentUser
//            updateUI(currentUser)
//            val intent = Intent(this, MainActivity::class.java)
//            startActivity(intent)
//            Log.e("Task Message", "Succesful")
//
//        } else {
//            Toast.makeText(this, "Input Tidak Boleh Kosong", Toast.LENGTH_LONG).show()
//        }
//
    }

}
