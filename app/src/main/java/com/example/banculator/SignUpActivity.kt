package com.example.banculator


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.banculator.view.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_sign_up.*
import java.util.*
import kotlin.collections.HashMap

class SignUpActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    private lateinit var databaseReference: DatabaseReference
    private lateinit var database: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference = database.reference.child("profile")
        firestore = FirebaseFirestore.getInstance()


        btn_signup.setOnClickListener {
            register()
        }
    }

    private fun register() {

        if (et_uname_sign.text.toString().isNotEmpty() || et_email_signup.text.toString()
                .isNotEmpty() || et_pw_signup.text.toString().isNotEmpty()
        ) {
            val uname = et_uname_sign.text.toString()
            val email = et_email_signup.text.toString()
            val pw = et_pw_signup.text.toString()

            auth.createUserWithEmailAndPassword(email, pw)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        val currentUser = auth.currentUser
                        val currentUSerDb = databaseReference.child((currentUser?.uid!!))
                        currentUSerDb.child("username").setValue(uname)
                        Toast.makeText(this, "Registration Success. ", Toast.LENGTH_LONG).show()
                        val intent = Intent(this,LoginActivity::class.java)
                        startActivity(intent)
                        finish()

                    } else {
                        Toast.makeText(
                            this,
                            "Registration failed, please try again! ",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
        } else {
            Toast.makeText(this, "Mohon Isi Form Yang Kosong", Toast.LENGTH_LONG).show()
        }

//    public override fun onStart() {
//        super.onStart()
//        val currentUser = auth.currentUser
//        updateUI(currentUser)
//    }
//
//    private fun updateUI(currentUser: FirebaseUser?) {
//        if (currentUser != null) {
//            val intent = Intent(this, MainActivity::class.java)
//            startActivity(intent)
//        } else {
//            Toast.makeText(this, "Login Gagal", Toast.LENGTH_LONG).show()
//        }
//    }
//
//    fun signUp() {
//        firestore = FirebaseFirestore.getInstance()
//        if (et_uname_sign.text.toString().isNotEmpty() || et_email_signup.text.toString()
//                .isNotEmpty() || et_pw_signup.text.toString().isNotEmpty()
//        ) {
//            val uname = et_uname_sign.text.toString()
//            val email = et_email_signup.text.toString()
//            val pw = et_pw_signup.text.toString()
//            auth.createUserWithEmailAndPassword(email, pw)
//                .addOnCompleteListener(this) { task ->
//                    if (task.isSuccessful) {
//                        auth.currentUser?.let {
////                            val userID = auth.currentUser?.uid!!
////                            var documentReference = firestore.collection("users").document(userID)
////                            Map<String, Objects> user = new HashMap<>()
////                                .put()
//                            Log.e("Task Message", "Succesful")
//                            Toast.makeText(this, "Register Berhasil", Toast.LENGTH_LONG).show()
//                            startActivity(Intent(this, LoginActivity::class.java))
//                            finish()
//                        }
//                    } else {
//                        Log.e("Task Message", "Not Succesful")
//                        updateUI(null)
//                    }
//
//                }
//
//        } else {
//            Toast.makeText(this, "Input Tidak Boleh Kosong", Toast.LENGTH_LONG).show()
//            return
//        }

    }

//
}

