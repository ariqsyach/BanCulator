package com.example.banculator

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.banculator.model.HasilHitung
import com.example.banculator.model.Users
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {
    //    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var reference: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
//        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        reference = database.getReference("Users")
        btn_signup.setOnClickListener {
            if (et_uname_sign.text.toString().isNotEmpty() || et_email_login.text.toString()
                    .isNotEmpty() || et_pw_signup.text.toString().isNotEmpty()
            ) {
                signUp()
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Input Tidak Boleh Kosong", Toast.LENGTH_LONG).show()
            }
        }
    }

    fun signUp() {
//        auth.createUserWithEmailAndPassword(email,password)
        val uname = et_uname_sign.text.toString()
        val email = et_email_signup.text.toString()
        val pw = et_pw_signup.text.toString()
        var userData = Users(uname, email, pw)
        var id = reference.push().key
        reference.child(id!!).setValue(userData).addOnCompleteListener {
            Toast.makeText(this, "oke", Toast.LENGTH_LONG).show()
        }
//            .addOnCompleteListener(this) { task ->
//if(task.isSuccessful){
//    Log.e("Task Message", "Succesful")
//}else{
//    Log.e("Task Message", "Not Succesful")
//}

    }
}
