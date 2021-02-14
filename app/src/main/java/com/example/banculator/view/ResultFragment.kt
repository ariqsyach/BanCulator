package com.example.banculator.view

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.banculator.LoginActivity

import com.example.banculator.R
import com.example.banculator.adapter.AdapterRv
import com.example.banculator.model.FeedFirestoreData
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import kotlinx.android.synthetic.main.dialog_add_feed.*
import kotlinx.android.synthetic.main.dialog_add_feed.view.*
import kotlinx.android.synthetic.main.dialog_add_feed.view.btn_cancel
import kotlinx.android.synthetic.main.fragment_database.*
import kotlinx.android.synthetic.main.fragment_result.*
import java.util.*
import kotlin.collections.ArrayList

class ResultFragment : Fragment() {
    private lateinit var adapter: AdapterRv
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference
    private lateinit var storage: FirebaseStorage
    private lateinit var storageReference: StorageReference
    

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        storage = FirebaseStorage.getInstance()
        databaseReference = database.reference.child("userFeeds")
        storageReference = storage.reference.child("userFeeds")


        return inflater.inflate(R.layout.fragment_result, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_add_feeds.setOnClickListener {
            showEditDialog()
        }
//        adapter = AdapterRv
//        rv_feeds.layoutManager = LinearLayoutManager(
//            this@ResultFragment.requireContext(),
//            LinearLayoutManager.VERTICAL,
//            false
//        )
//        rv_feeds.adapter = adapter
        getData()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val fileUri = data?.data
        if (requestCode == 111 && data != null) {
            Log.d("DatabaseFragment", "Sabi")
            uploadImageDatabaseStorage(fileUri!!)
        } else {
            Toast.makeText(this.context!!, "gagal", Toast.LENGTH_LONG)
                .show()
        }
    }

    private fun getData() {
//        val ref = databaseReference.child(auth.currentUser?.uid.toString())
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                var list = ArrayList<FeedFirestoreData>()
                for (data in snapshot.children) {
                    var model = data.getValue(FeedFirestoreData::class.java)
                    list.add(model as FeedFirestoreData)
                }
                if (list.size > 0) {
                    var adapter = AdapterRv(list)
                    rv_feeds.layoutManager = LinearLayoutManager(
                        this@ResultFragment.requireContext(),
                        LinearLayoutManager.VERTICAL,
                        false
                    )
                    rv_feeds.adapter = adapter
                }
            }
        })
    }

    private fun showEditDialog() {
        this.let {
            var requestCode = 0
            val builder = AlertDialog.Builder(this.context!!)
            val inflate =
                this.layoutInflater.inflate(R.layout.dialog_add_feed, cl_fragment_result, false)
            builder.setView(inflate)
            val dialog = builder.create()
            dialog.show()

            val currentUser = auth.currentUser?.uid!!.toString()
            val filename = UUID.randomUUID().toString()
//            val currentUSerDb = databaseReference.child(currentUser).child(filename)
            val currentUSerDb = databaseReference.child(filename)
            val sp = requireActivity().getSharedPreferences("coba", Context.MODE_PRIVATE)
            val imageUrl = sp.getString("feed_image", "")
            inflate.btn_update.setOnClickListener {
                val addMobil = inflate.et_add_tipeMobil
                val addLebar = inflate.et_add_lebar.text.toString()
                val addDiameter = inflate.et_add_diameter.text.toString()
                val addTinggi = inflate.et_add_tinggi.text.toString()
                val addKet = inflate.et_add_keterangan.text.toString()
                val mobil = addMobil.text.toString()
                currentUSerDb.child("mobil").setValue(mobil)
                currentUSerDb.child("lebar").setValue(addLebar)
                currentUSerDb.child("diameter").setValue("R$addDiameter")
                currentUSerDb.child("tinggi").setValue(addTinggi)
                currentUSerDb.child("keterangan").setValue(addKet)
                currentUSerDb.child("uid").setValue(currentUser)

                Toast.makeText(this.context!!, "Berhasil Menaruh ke Database", Toast.LENGTH_LONG)
                    .show()

//                        downloadPhoto(imageUrl)
                    currentUSerDb.child("imageUrl").setValue(imageUrl)
                getData()
                dialog.cancel()
            }
            inflate.iv_add_feed.setOnClickListener {
                val intent = Intent(Intent.ACTION_PICK)
                intent.type = "image/*"
                val gambar = intent.data.toString()
                startActivityForResult(intent, 111)
//                requestCode = 111
                if (requestCode == 111) {
                    Glide.with(context!!)
                        .load(imageUrl)
                        .into(inflate.iv_add_feed)
                }
            }
            inflate.btn_cancel.setOnClickListener {
                dialog.cancel()
            }

        }
    }

    private fun uploadImageDatabaseStorage(fileUri: Uri) {
        val filename = UUID.randomUUID().toString()
        val nama = auth.currentUser?.uid
        val currentUser = auth.currentUser?.uid!!
        val refStorage =
            FirebaseStorage.getInstance().reference.child("feedImages/$currentUser/$filename.jpg")

        refStorage.putFile(fileUri)
            .addOnSuccessListener(
                OnSuccessListener<UploadTask.TaskSnapshot> { taskSnapshot ->
                    taskSnapshot.storage.downloadUrl.addOnSuccessListener {
                        val imageUrl = it.toString()
                        val sp = requireActivity().getSharedPreferences("coba", Context.MODE_PRIVATE)
                        val esp = sp.edit()
                        esp.putString("feed_image", imageUrl)
                        esp.commit()
//                        downloadPhoto(imageUrl)
//                        val currentUSerDb = databaseReference.child(filename)
//                        currentUSerDb.child("imageUrl").setValue(imageUrl)
                        Toast.makeText(
                            this.context!!,
                            "berhasil mengupload gambar",
                            Toast.LENGTH_LONG
                        )
                            .show()
                    }
                })

            .addOnFailureListener(OnFailureListener { e ->
                print(e.message)
            })

    }

//    private fun downloadPhoto(url: String) {
//        Glide.with(context!!)
//            .load(url)
//            .into(inflate.iv_add_feed)
//    }

//    private fun updateUI(currentUser: FirebaseUser?) {
//        if (currentUser != null) {
//            Toast.makeText(context!!, "Login Berhasil", Toast.LENGTH_LONG).show()
//        } else {
//            Toast.makeText(context!!, "Login Gagal", Toast.LENGTH_LONG).show()
//
//        }
//    }

}
