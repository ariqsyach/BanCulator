package com.example.banculator.view

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.banculator.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_database.*


class DatabaseFragment : Fragment() {
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseReference :  DatabaseReference
    private lateinit var database: FirebaseDatabase
    // TODO: Rename and change types of parameters
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadProfile()
//        logoutButton.setOnClickListener {
//            auth.signOut()
//            startActivity(Intent(this@ProfileActivity, LoginActivity::class.java))
//            finish()
//        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference = database?.reference!!.child("profile")
        return inflater.inflate(R.layout.fragment_database, container, false)
    }
    @SuppressLint("SetTextI18n")
    private fun loadProfile() {

        val user = auth.currentUser
        val userreference = databaseReference?.child(user?.uid!!)

        tv_email_profile.text = "Email  -- > "+user?.email

        userreference?.addValueEventListener(object: ValueEventListener {
            @SuppressLint("SetTextI18n")
            override fun onDataChange(snapshot: DataSnapshot) {

                tv_username_profile.text = "Firstname - - > "+snapshot.child("username").value.toString()
//                lastnameText.text = "Last name - -> "+snapshot.child("lastname").value.toString()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })



    }
}
