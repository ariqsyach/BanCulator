package com.example.banculator.view

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ProgressDialog
import android.content.ContentResolver
import android.content.Intent
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat.startActivityForResult
import com.bumptech.glide.Glide
import com.example.banculator.LoginActivity

import com.example.banculator.R
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.storage.*
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_database.*
import kotlinx.android.synthetic.main.item_feeds.*
import kotlinx.coroutines.tasks.await
import java.util.*


class DatabaseFragment : Fragment() {
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference
    private lateinit var database: FirebaseDatabase

    private val RequestCode = 438

    //    private var firebaseUser: FirebaseUser? = null
    private var imageUri: Uri? = null
    private var storageRef: StorageReference? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        auth = FirebaseAuth.getInstance()
//        firebaseUser = FirebaseAuth.getInstance().currentUser
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
//        downloadPhoto()
            btn_signout.setOnClickListener {
            auth.signOut()
                val intent = Intent(this.context!!,LoginActivity::class.java)
                startActivity(intent)
        }

    }

    @SuppressLint("SetTextI18n")
    private fun loadProfile() {
        val fileName = "profile.jpg"
        val nama = auth.currentUser?.uid

        val user = auth.currentUser
        val userreference = databaseReference?.child(user?.uid!!)
        val fotoUrl = storageRef?.child("userImages/$nama/$fileName")!!.downloadUrl.toString()

        tv_email_profile.text = user?.email.toString()

        downloadImageDatabaseStorage()

//        downloadPhoto(fotoUrl)

        userreference?.addValueEventListener(object : ValueEventListener {
            @SuppressLint("SetTextI18n")
            override fun onDataChange(snapshot: DataSnapshot) {

                tv_username_profile.text = snapshot.child("username").value.toString()
               val fotoProfileUrl = snapshot.child("imageUrl").value.toString()
                downloadPhoto(fotoProfileUrl)
//                lastnameText.text = "Last name - -> "+snapshot.child("lastname").value.toString()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

    }
//    private fun selectImageFromGallery() {
//
//        val intent = Intent()
//        intent.type = "image/*"
//        intent.action = Intent.ACTION_GET_CONTENT
//        startActivityForResult(
//            Intent.createChooser(
//                intent,
//                "Please select..."
//            ),
//            GALLERY_REQUEST_CODE
//        )
//    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val fileUri = data?.data
        if (requestCode == 111 && data != null) {
            Log.d("DatabaseFragment", "Sabi")
            uploadImageDatabaseStorage(fileUri!!)
        } else {


        }
    }

    private fun downloadImageDatabaseStorage() {
        val fileName = "profile.jpg"
        val nama = auth.currentUser?.uid
        val refStorage =
            FirebaseStorage.getInstance().reference.child("userImages/$nama/$fileName")



//        refStorage.getFile(fileUri!!)
//            .addOnSuccessListener(
//                { taskSnapshot ->
//                    taskSnapshot.storage.downloadUrl.addOnSuccessListener {
//                        val imageUrl = it.toString()
//                        downloadPhoto(imageUrl)
//                    }
//                })
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



//    private fun uploadImageToFirebaseStorage() {
//        val filename = UUID.randomUUID().toString()+".jpg"
////        val ref = FirebaseStorage.getInstance().getReference("/images/$filename")
//        val ref = storageRef!!.child(filename)
//        ref.putFile(imageUri!!)
//            .addOnSuccessListener {
//                Log.d("Image", "Success : ${it.metadata?.path}")
//            }
//    }

//
//    fun uploadFile() {
//        var pd = ProgressDialog(this.context!!)
//        pd.setTitle("uPLOADING")
//        pd.show()
//
//        val fileRef = storageRef!!.child(System.currentTimeMillis().toString() + ".jpg")
//        fileRef.putFile(imageUri!!)
//            .addOnSuccessListener { p0 ->
//                pd.dismiss()
//                Toast.makeText(context, "sucess", Toast.LENGTH_LONG).show()
//            }
//            .addOnFailureListener { p0 ->
//                pd.dismiss()
//                Toast.makeText(context, "fails", Toast.LENGTH_LONG).show()
//            }
//            .addOnProgressListener { p0 ->
//
//                Toast.makeText(context, "Uasg", Toast.LENGTH_LONG).show()
//            }
//    }
//
//    private fun uploadImageToDatabase() {
//        val progressBar = ProgressDialog(context)
//        progressBar.setMessage("image is uploading")
//        progressBar.show()
//
//        if (imageUri != null) {
//            val fileRef = storageRef!!.child(System.currentTimeMillis().toString() + ".jpg")
//
//            var uploadTask: StorageTask<*>
//            uploadTask = fileRef.putFile(imageUri!!)
//            uploadTask.continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>> { task ->
//                if (!task.isSuccessful) {
//                    task.exception?.let {
//                        throw it
//                    }
//                }
//                return@Continuation fileRef.downloadUrl
//            }).addOnCompleteListener { task ->
//                if (task.isSuccessful) {
//                    val downloadUrl = task.result
//                    val url = downloadUrl.toString()
//                    Glide.with(context!!)
//                        .load(fileRef)
//                        .into(iv_photo_profile)
//
//                }
//            }
//        }


    private fun downloadPhoto(url: String) {
        Glide.with(context!!)
            .load(url)
            .into(iv_photo_profile)
    }

    private fun pickImage() {
        val intent = Intent()
        intent.type = "userImages/*"
        intent.action = Intent.ACTION_PICK
        startActivityForResult(intent, 111)
    }
//}
//
//    private fun imageChooser() {
//        val i = Intent()
//        i.setType("Image/*")
//        i.setAction(Intent.ACTION_GET_CONTENT)
//        startActivityForResult(Intent.createChooser(i, "Choose Picture"), 111)
//    }
}


