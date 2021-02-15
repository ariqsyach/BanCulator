package com.example.banculator


import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference
    private lateinit var database: FirebaseDatabase
    private lateinit var storage: FirebaseStorage
    private lateinit var storageReference: StorageReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference = database.reference.child("profile")
        storage = FirebaseStorage.getInstance()
        storageReference = storage.reference.child("userImages")


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
            val uri: Uri = Uri.parse(
                "android.resource://" + baseContext.packageName
                    .toString() + "/drawable/default_profile.png"
            )
            auth.createUserWithEmailAndPassword(email, pw)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        val currentUser = auth.currentUser
                        val currentUSerDb = databaseReference.child(currentUser?.uid!!)
                        currentUSerDb.child("username").setValue(uname)
                        currentUSerDb.child("uid").setValue(currentUser?.uid!!)
                        val currentUserPhoto = storageReference.child(currentUser?.uid!! + ".jpg")
                        currentUserPhoto.putFile(uri)
                        Toast.makeText(this, "Registration Success. ", Toast.LENGTH_LONG).show()
                        val intent = Intent(this, LoginActivity::class.java)
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
    }
}


