package com.example.banculator.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.banculator.LoginActivity

import com.example.banculator.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.fragment_result.*

class ResultFragment : Fragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_add_feeds.setOnClickListener{
            updateUI(null)
            val intent = Intent(context!!, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_result, container, false)

    }


    private fun updateUI(currentUser: FirebaseUser?) {
        if (currentUser != null) {
            Toast.makeText(context!!, "Login Berhasil", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(context!!, "Login Gagal", Toast.LENGTH_LONG).show()

        }
    }

}
