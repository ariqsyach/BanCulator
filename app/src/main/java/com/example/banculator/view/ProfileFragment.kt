package com.example.banculator.view

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.banculator.LoginActivity
import com.example.banculator.R
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.*
import kotlinx.android.synthetic.main.fragment_database.*

class ProfileFragment : Fragment() {
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference
    private lateinit var database: FirebaseDatabase
    private var storageRef: StorageReference? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference = database?.reference!!.child("profile")
        storageRef = FirebaseStorage.getInstance().reference.child("userImages")
        return inflater.inflate(R.layout.fragment_database, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadProfile()
        iv_photo_profile.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 111)
        }
            btn_signout.setOnClickListener {
            auth.signOut()
                val intent = Intent(this.context!!,LoginActivity::class.java)
                startActivity(intent)
        }
    }
    @SuppressLint("SetTextI18n")
    private fun loadProfile() {
        val user = auth.currentUser
        val userreference = databaseReference?.child(user?.uid!!)
        tv_email_profile.text = user?.email.toString()
        userreference?.addValueEventListener(object : ValueEventListener {
            @SuppressLint("SetTextI18n")
            override fun onDataChange(snapshot: DataSnapshot) {

                tv_username_profile.text = snapshot.child("username").value.toString()
               val fotoProfileUrl = snapshot.child("imageUrl").value.toString()
                downloadPhoto(fotoProfileUrl)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val fileUri = data?.data
        if (requestCode == 111 && data != null) {
            Log.d("DatabaseFragment", "Sabi")
            uploadImageDatabaseStorage(fileUri!!)
        }
    }
    private fun uploadImageDatabaseStorage(fileUri: Uri) {
        val fileName = "profile.jpg"
        val nama = auth.currentUser?.uid
        val database = FirebaseDatabase.getInstance()
        val refStorage = FirebaseStorage.getInstance().reference.child("userImages/$nama/$fileName")

        refStorage.putFile(fileUri)
            .addOnSuccessListener(
                OnSuccessListener<UploadTask.TaskSnapshot> { taskSnapshot ->
                    taskSnapshot.storage.downloadUrl.addOnSuccessListener {
                        val imageUrl = it.toString()
                        downloadPhoto(imageUrl)
                        val currentUSerDb = databaseReference.child(nama!!)
                        currentUSerDb.child("imageUrl").setValue(imageUrl)
                    }
                })

            .addOnFailureListener(OnFailureListener { e ->
                print(e.message)
            })

    }
    private fun downloadPhoto(url: String) {
        Glide.with(context!!)
            .load(url)
            .into(iv_photo_profile)
    }
}


